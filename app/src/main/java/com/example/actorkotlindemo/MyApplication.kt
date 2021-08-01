package com.example.actorkotlindemo

import android.app.Application
import com.example.actorkotlindemo.api.APIKit

class MyApplication: Application() {
    val apiKit by lazy {
        APIKit()
    }
}