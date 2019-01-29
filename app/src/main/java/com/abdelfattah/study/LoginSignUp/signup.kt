package com.abdelfattah.study.LoginSignUp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.R
import com.abdelfattah.study.COURSE.TheDoctor
import com.abdelfattah.study.COURSE.TheStudent
import kotlinx.android.synthetic.main.activity_signup.*

class signup : AppCompatActivity() {
    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        controller= Controller(this)
        setSupportActionBar(mytoolbarsignup as Toolbar)
        supportActionBar!!.title="SIGN UP"
        Doctorinfo.email="Unknown"
        Studentinfo.Studentemail="Unknown"
      //  controller!!.enable()
    }

    override fun onRestart() {
        super.onRestart()
        Doctorinfo.email="Unknown"
        Studentinfo.Studentemail="Unknown"
    }

    fun signupevent(view:View)
    {
        var Doctor=Doctor.isChecked
        var student=Student.isChecked
        var email:String=emailtxt.text.toString()
        var firstname:String=fnametxt.text.toString()
        var secondname:String=snametxt.text.toString()
        var pass:String=passtxt.text.toString()
        var passagain:String=repasstxt.text.toString()//take all data
        if(!Doctor && !student)//check if they chose a student or doctor
        {
            Toast.makeText(this,"Please chose student or doctor",Toast.LENGTH_SHORT).show()
           return
        }
        else if(email==""||firstname==""||secondname==""||pass!=passagain)//check if they insert all fields
        {
            Toast.makeText(this,"enter all data and make sure password is match",Toast.LENGTH_SHORT).show()
            return
        }
        if(email.length>15||pass.length>15||firstname.length>15||secondname.length>15)
        {
            Toast.makeText(this,"Any info cannot exceed 15 characters",Toast.LENGTH_SHORT).show()
            return
        }
        var success=controller!!.insertuser(Doctor,email,firstname,secondname,pass)//insert user
        if(success)
        {

            if(Doctor)
            {
                Doctorinfo.email= email
                Doctorinfo.Fname=firstname
                Doctorinfo.password=pass
                Doctorinfo.Secondname=secondname
                PickedUser.Doctor=true;
                PickedUser.ID=Doctorinfo.email
                var intent = Intent(this, TheDoctor::class.java)
                startActivity(intent)//if doctor go to doctor activity
            }
            else{
                Studentinfo.Studentemail= email
                Studentinfo.StudentFname=firstname
                Studentinfo.password=pass
                Studentinfo.Secondname=secondname
                PickedUser.Doctor=false;
                PickedUser.ID=Studentinfo.Studentemail

                var intent = Intent(this, TheStudent::class.java)
                startActivity(intent)//if student go to student activity

            }

        }
        else
        {
            Toast.makeText(this,"this user already logged in",Toast.LENGTH_SHORT).show()
        }

    }


}
