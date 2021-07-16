package com.example.component

import com.example.component.entity.MyAPIRequestEntity
import com.example.component.entity.MyAPICallback
import com.example.component.entity.MyLogCallback

interface ControlByComponent1 {
    fun log(msg: String, callback: MyLogCallback)
    fun callApi(apiRequestEntity: MyAPIRequestEntity, callback: MyAPICallback)
}