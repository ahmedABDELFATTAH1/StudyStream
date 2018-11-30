package com.abdelfattah.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var controller:Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        controller=Controller(this)
    }

    fun loginevent(view:View) {
        val email: String = logintext.text.toString()
        val pass: String = passwordtext.text.toString()
        if(email==""||pass=="")
        {
            Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
        }
        var IsThere = controller!!.checkuser(email, pass)
        if (IsThere) {
            Toast.makeText(this, "A7la Mesa 3la El so7ab", Toast.LENGTH_LONG).show()
            var intent = Intent(this, TheStart::class.java)
            startActivity(intent)
        } else
        {
            Toast.makeText(this,"Wrong Email or Password Please Reenter",Toast.LENGTH_SHORT).show()
        }

    }

}
