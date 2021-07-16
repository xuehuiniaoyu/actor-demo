package com.example.component.entity

interface MyAPICallback {
    fun onSuccess(entity: MyAPIResponseEntity)
    fun onFail()
}