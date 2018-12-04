package com.abdelfattah.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.abdelfattah.study.data.CourseStudent
import com.abdelfattah.study.data.StudyStreamContract.*
import kotlinx.android.synthetic.main.activity_the_student.*
class TheStudent : AppCompatActivity() {
    var ListOfCourses:ArrayList<CourseStudent>?=null
    var controller:Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_student)
        ListOfCourses=ArrayList()
        controller= Controller(this)
        setSupportActionBar(mytoolbarstudent as Toolbar)

        supportActionBar!!.title="Welcome Student "+Studentinfo.StudentFname
    }

    fun getCourse()
    {
        var cursor=controller!!.JoindedCourses(Studentinfo.Studentemail!!)
        var Isnotempty:Boolean=cursor.moveToFirst()
        while (Isnotempty)
        {
            var Courscode=cursor.getInt(cursor.getColumnIndex(Course.Column_Code))
            var Coursetitle=cursor.getString(cursor.getColumnIndex(Course.Column_Title))
            var CourseDesc=cursor.getString(cursor.getColumnIndex(Course.Column_Description))
            var Coursepass=cursor.getInt(cursor.getColumnIndex(Course.Column_Password))
            var DoctorID=cursor.getString(cursor.getColumnIndex(Course.Column_Doc_ID))
            ListOfCourses!!.add(CourseStudent(Courscode,Coursetitle,CourseDesc,DoctorID,Coursepass))
            Isnotempty=cursor.moveToNext()
        }




    }







    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.mainmenu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item!!.itemId==R.id.logoutbtn)
        {
            Toast.makeText(this,"Hantala3k barra 7ader shoukrn ya mo7trm", Toast.LENGTH_LONG).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        return true
    }
}
