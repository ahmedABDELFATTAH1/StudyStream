package com.abdelfattah.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.abdelfattah.study.data.DBHelper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DBHelper(this)

    }
    fun logmain(view:View)
    {

        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)

    }
    fun signmain(view:View)
    {

        val intent=Intent(this,signup::class.java)
        startActivity(intent)
    }

}
