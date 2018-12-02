package com.abdelfattah.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.abdelfattah.study.data.DBHelper
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DBHelper(this)
        setSupportActionBar(mytoolbarmain as Toolbar)
        supportActionBar!!.title="Welcome To STUDY STREAM"
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
