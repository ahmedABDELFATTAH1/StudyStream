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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        controller= Controller(this)
        setSupportActionBar(mytoolbarlogin as Toolbar)
        supportActionBar!!.title="LOGIN"
        Doctorinfo.email="Unknown"
        Studentinfo.Studentemail="Unknown"
       //controller!!.enable()
    }

//take email and pass word
//check if they put it
// make sure it's exists
// see if it is a doctor or student and then go to doctor/studen activities
    fun loginevent(view:View) {
        Doctorinfo.email="Unknown"
        Studentinfo.Studentemail="Unknown"
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
               PickedUser.Doctor = true;
               PickedUser.ID = Doctorinfo.email;
                var intent = Intent(this, TheDoctor::class.java)
                startActivity(intent)

           }
           else{
                Studentinfo.Studentemail=UserCursor.getString(0)
                Studentinfo.password=UserCursor.getString(1)
                Studentinfo.StudentFname=UserCursor.getString(2)
                Studentinfo.Secondname=UserCursor.getString(3)
                PickedUser.Doctor = false;
                PickedUser.ID = Studentinfo.Studentemail;
                var intent = Intent(this, TheStudent::class.java)
               startActivity(intent)
          }



        } else
        {
            Toast.makeText(this,"Wrong Email or Password Please Reenter",Toast.LENGTH_SHORT).show()
        }

    }

}
