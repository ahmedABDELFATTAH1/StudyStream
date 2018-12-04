package com.abdelfattah.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_the_start.*

class LoginActivity : AppCompatActivity() {
    var controller:Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        controller=Controller(this)
        setSupportActionBar(mytoolbarlogin as Toolbar)
        supportActionBar!!.title="LOGIN"
    }

    fun loginevent(view:View) {
        val email: String = logintext.text.toString()
        val pass: String = passwordtext.text.toString()
        if(email==""||pass=="")
        {
            Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
            return
        }

        var IsThere = controller!!.checkuser(email, pass)
        if (IsThere) {

           var isdoctor=controller!!.checkkind(email)
          var UserCursor=controller!!. UserData(email)
            if(isdoctor)
           {
               Doctorinfo.email=UserCursor.getString(0)
               Doctorinfo.password=UserCursor.getString(1)
               Doctorinfo.Fname=UserCursor.getString(2)
               Doctorinfo.Secondname=UserCursor.getString(3)
                Toast.makeText(this, "A7la Mesa 3la El so7ab", Toast.LENGTH_LONG).show()
                var intent = Intent(this, TheStart::class.java)
                startActivity(intent)

           }
           else{
                Studentinfo.Studentemail=UserCursor.getString(0)
                Studentinfo.password=UserCursor.getString(1)
                Studentinfo.StudentFname=UserCursor.getString(2)
                Studentinfo.Secondname=UserCursor.getString(3)
                var intent = Intent(this, TheStudent::class.java)
               startActivity(intent)
          }



        } else
        {
            Toast.makeText(this,"Wrong Email or Password Please Reenter",Toast.LENGTH_SHORT).show()
        }

    }

}
