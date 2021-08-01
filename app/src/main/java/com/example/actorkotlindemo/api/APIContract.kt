package com.example.actorkotlindemo.api

import com.example.actorkotlindemo.entity.APIResponse

interface APIContract {
    fun getApiKey(): String
    fun onSuccess(response: APIResponse)
    fun onFail()
}