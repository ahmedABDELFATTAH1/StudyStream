package com.abdelfattah.study

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {
    var controller:Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        controller=Controller(this)

    }

    fun signupevent(view:View)
    {
        var Doctor=Doctor.isChecked
        var student=Student.isChecked
        var email:String=emailtxt.text.toString()
        var firstname:String=fnametxt.text.toString()
        var secondname:String=snametxt.text.toString()
        var pass:String=passtxt.text.toString()
        var passagain:String=repasstxt.text.toString()
        if(!Doctor && !student)
        {
            Toast.makeText(this,"Please chose student or doctor",Toast.LENGTH_SHORT).show()
           return
        }
        else if(email==""||firstname==""||secondname==""||pass!=passagain)
        {
            Toast.makeText(this,"enter all data and make sure password is match",Toast.LENGTH_SHORT).show()
            return
        }
         var success=controller!!.insertuser(Doctor,email,firstname,secondname,pass)
        if(success)
        {

            Toast.makeText(this,"el7amd llah",Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this,"this user already logged in",Toast.LENGTH_SHORT).show()
        }

    }


}
