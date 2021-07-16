package com.example.actorkotlindemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.component.MainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.loadComponentLocal).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).also {
                it.putExtra("local", true)
            })
        }
        findViewById<View>(R.id.loadComponentApp).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java).also {
                it.putExtra("local", false)
            })
        }
    }
}