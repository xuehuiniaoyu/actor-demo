package com.example.component

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.component.entity.MyAPIRequestEntity
import com.example.component.entity.MyAPIResponseEntity
import com.example.component.entity.MyAPICallback
import com.example.component.entity.MyLogCallback

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "component-main"
    }
    private val fromLocal by lazy { intent.getBooleanExtra("local", false) }
    private val apiKitTrusteeship by lazy { APIKitTrusteeshipImpl().getActor(fromLocal) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.component_main)
        this.findViewById<View>(R.id.btnCallApi).setOnClickListener {
            callSuccessAPI()
        }
        this.findViewById<View>(R.id.btnCallApiFail).setOnClickListener {
            callFailAPI()
        }
        this.findViewById<View>(R.id.btnShowLog).setOnClickListener {
            showLog()
        }
    }

    fun showLog() {
        apiKitTrusteeship.log("hello world!", object : MyLogCallback {
            override fun log(log: String) {
                findViewById<TextView>(R.id.txtShowLog).text = log
            }
        })
    }

    fun callSuccessAPI() {
        apiKitTrusteeship.callApi(MyAPIRequestEntity("POST", "http://api.login"), object: MyAPICallback {
            override fun onSuccess(entity: MyAPIResponseEntity) {
                findViewById<TextView>(R.id.txtCallApi).text = "${entity.data}"
            }

            override fun onFail() {
                findViewById<TextView>(R.id.txtCallApi).text = "call api is fail"
            }
        })
    }

    fun callFailAPI() {
        apiKitTrusteeship.callApi(MyAPIRequestEntity("POST", "onFail"), object: MyAPICallback {
            override fun onSuccess(entity: MyAPIResponseEntity) {
                findViewById<TextView>(R.id.txtCallApi).text = "${entity.data}"
            }

            override fun onFail() {
                findViewById<TextView>(R.id.txtCallApi).text = "call api is fail"
            }
        })
    }
}