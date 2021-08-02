package com.example.component

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import xuehuiniaoyu.github.io.actor.Actor
import xuehuiniaoyu.github.io.actor.ActorBean
import xuehuiniaoyu.github.io.actor.di.DynamicImplementation

class MainActivity : AppCompatActivity() {
    interface APIKitProxy {
        fun callApi(key: String, @DynamicImplementation contract: Any)
    }

    interface APIContractProxy {
        fun getApiKey(): String
        fun onSuccess(response: Any)
        fun onFail()
    }

    private val apiKit by lazy {
        Actor(
            ActorBean(applicationContext).get("apiKit") ?: error("")
        ).imitator(APIKitProxy::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_main)
        findViewById<Button>(R.id.btnCallApi).setOnClickListener {
            apiKit.callApi("data", object: APIContractProxy {
                override fun getApiKey(): String = "api1"

                override fun onSuccess(response: Any) {
                    val data = ActorBean(response).get<String>("data")
                    findViewById<TextView>(R.id.txtData).text = data
                }

                override fun onFail() {
                    findViewById<TextView>(R.id.txtData).text = "api call fail"
                }
            })
        }
    }
}