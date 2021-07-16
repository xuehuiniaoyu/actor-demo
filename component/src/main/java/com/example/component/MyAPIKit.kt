package com.example.component

import android.util.Log
import com.example.component.entity.MyAPICallback
import com.example.component.entity.MyAPIRequestEntity
import com.example.component.entity.MyAPIResponseEntity
import com.example.component.entity.MyLogCallback

class MyAPIKit {
    companion object {
        const val TAG = "My-APIKit"
    }

    fun log(msg: String, callback: MyLogCallback) {
        callback.log("from:local -- $msg ...... ${System.currentTimeMillis()}")
    }

    fun callApi(request: MyAPIRequestEntity, callback: MyAPICallback) {
        callback.onSuccess(MyAPIResponseEntity("success from:local -- ${System.currentTimeMillis()}"))
    }
}