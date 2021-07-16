package com.example.actorkotlindemo.entity

interface APICallback {
    fun onSuccess(data: APIResponseEntity)
    fun onFail()
}