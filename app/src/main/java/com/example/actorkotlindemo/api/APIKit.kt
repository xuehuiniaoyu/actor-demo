package com.example.actorkotlindemo.api

import com.example.actorkotlindemo.entity.APIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception

class APIKit {
    fun callApi(apiKey: String, contract: APIContract) {
        println("apiKey: $apiKey")
        val key = contract.getApiKey()
        val url = APIDeclaration[key]
        GlobalScope.launch(Dispatchers.IO) {
            val response = call(url ?: error("$key isNot defined！"))
            launch(Dispatchers.Main) {
                if(response.code == 200)
                    contract.onSuccess(response)
                else
                    contract.onFail()
            }
        }
    }

    private fun call(url: String): APIResponse {
        return try {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body?.string()
            APIResponse(response.code, data)
        } catch (e: Exception) {
            APIResponse(-1, null)
        }
    }
}