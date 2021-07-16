package com.example.component

import com.example.component.entity.MyAPICallback
import com.example.component.entity.MyAPIRequestEntity
import com.example.component.entity.MyAPIResponseEntity
import com.example.component.entity.MyLogCallback
import com.example.resource_manager.EntityKeys
import com.example.resource_manager.EntityPool
import com.google.gson.Gson
import org.actor.ActorTrusteeship
import org.actor.MappingStrategy

interface APIKitTrusteeship {
    fun getActor(local: Boolean): ControlByComponent1
}

class APIKitTrusteeshipImpl : APIKitTrusteeship {

    private val apiKit: Any by lazy { EntityPool.pull(EntityKeys.INSTANCE_API_KIT) }
    private val apiRequestEntityClass: Class<*> by lazy { EntityPool.pull(EntityKeys.CLASS_API_REQUEST) }
    private val apiResponseEntityClass: Class<*> by lazy { EntityPool.pull(EntityKeys.CLASS_API_RESPONSE) }
    private val apiCallbackClass: Class<*> = EntityPool.pull(EntityKeys.CLASS_API_CALLBACK)
    private val logCallbackClass: Class<*> = EntityPool.pull(EntityKeys.CLASS_LOG_CALLBACK)


    open class SimpleMappingStrategy : MappingStrategy {
        private val gson by lazy { Gson() }
        override fun onMapping(from: Any, expectedType: Class<*>): Any? {
            return gson.fromJson(gson.toJson(from), expectedType)
        }
    }

    override fun getActor(local: Boolean): ControlByComponent1 {
        return if (local) {
            ActorTrusteeship().join(MyAPIKit(), ControlByComponent1::class.java).apply {
                it.setMappingStrategy(SimpleMappingStrategy())
            }.proxy()
        } else ActorTrusteeship().join(apiKit, ControlByComponent1::class.java).apply { actor ->
            actor.setMapping {
                mapOf<Class<*>, Class<*>>(
                    MyAPIRequestEntity::class.java to apiRequestEntityClass,
                    MyAPICallback::class.java to apiCallbackClass,
                    MyLogCallback::class.java to logCallbackClass
                )
            }
            actor.setMappingStrategy(object : SimpleMappingStrategy() {
                override fun onMapping(from: Any, expectedType: Class<*>): Any? {
                    return when (from) {
                        is MyLogCallback -> {
                            return ActorTrusteeship().join(from, logCallbackClass).proxy()
                        }
                        is MyAPICallback -> {
                            val responseClass: Class<*> = apiResponseEntityClass
                            return ActorTrusteeship().join(from, apiCallbackClass).apply { actor ->
                                actor.setMapping {
                                    mapOf<Class<*>, Class<*>>(
                                        responseClass to MyAPIResponseEntity::class.java
                                    )
                                }
                                actor.setMappingStrategy(SimpleMappingStrategy())
                            }.proxy()
                        }
                        else -> super.onMapping(from, expectedType)
                    }
                }
            })
        }.proxy()
    }
}