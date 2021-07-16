package com.example.actorkotlindemo

import android.util.Log
import com.example.actorkotlindemo.entity.APICallback
import com.example.actorkotlindemo.entity.APIRequestEntity
import com.example.actorkotlindemo.entity.APIResponseEntity
import com.example.actorkotlindemo.entity.LogCallback
import com.example.component.entity.MyAPIResponseEntity

class APIKit {
    companion object {
        const val TAG = "APIKit"
    }

    fun log(msg: String, callback: LogCallback) {
        callback.log("from:app -- $msg ...... ${System.currentTimeMillis()}")
    }

    fun callApi(request: APIRequestEntity, callback: APICallback) {
        if(request.url.contains("http://") || request.url.contains("https://")) {
            callback.onSuccess(APIResponseEntity("success from:app -- ${System.currentTimeMillis()}"))
        }
        else {
            callback.onFail()
        }
    }
}