package com.example.actorkotlindemo

import android.app.Application
import com.example.actorkotlindemo.entity.APICallback
import com.example.actorkotlindemo.entity.APIRequestEntity
import com.example.actorkotlindemo.entity.APIResponseEntity
import com.example.actorkotlindemo.entity.LogCallback
import com.example.resource_manager.EntityKeys
import com.example.resource_manager.EntityPool

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        EntityPool.push(EntityKeys.INSTANCE_API_KIT, lazy { APIKit() })
        EntityPool.push(EntityKeys.CLASS_API_REQUEST, lazy { APIRequestEntity::class.java })
        EntityPool.push(EntityKeys.CLASS_API_RESPONSE, lazy { APIResponseEntity::class.java })
        EntityPool.push(EntityKeys.CLASS_API_CALLBACK, lazy { APICallback::class.java })
        EntityPool.push(EntityKeys.CLASS_LOG_CALLBACK, lazy { LogCallback::class.java})
    }
}