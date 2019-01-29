package com.abdelfattah.study.COURSE

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.abdelfattah.study.CourseMainMenu.Fragments.MainCourseDoctor
import com.abdelfattah.study.Course_Stastics_Doc.Course_Statistics_Doc
import com.abdelfattah.study.data.CourseStudent
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.LoginSignUp.MainActivity
import com.abdelfattah.study.LoginSignUp.editPassword
import com.abdelfattah.study.Questions.QuestionStudent
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract.*
import kotlinx.android.synthetic.main.activity_the_student.*
import kotlinx.android.synthetic.main.scourseticket.view.*

class TheStudent : AppCompatActivity() {
    var ListOfCourses:ArrayList<CourseStudent>?=null
    var controller: Controller?=null
    var adapter:CourseAdabterlist?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_student)
        ListOfCourses=ArrayList()
        controller= Controller(this)
        setSupportActionBar(mytoolbarstudent as Toolbar)
        supportActionBar!!.title="Welcome Student "+ Studentinfo.StudentFname
        getCourse()
        adapter=CourseAdabterlist()
        SCourseList.adapter= adapter
        adapter!!.notifyDataSetChanged()
    }
    fun JoinEvent(view: View)
    {
        var intent=Intent(this, JoinCourse::class.java)
        startActivity(intent)

    }
    fun getCourse()//get all course from the data base and assign it to Listofcourses
    {
        ListOfCourses!!.clear()
        var cursor=controller!!.JoindedCourses(Studentinfo.Studentemail!!)
        var Isnotempty:Boolean=cursor.moveToFirst()
      //  Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
        while (Isnotempty)
        {
           // Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
            var Courscode=cursor.getInt(cursor.getColumnIndex(Course.Column_Code))
            var Coursetitle=cursor.getString(cursor.getColumnIndex(Course.Column_Title))
            var CourseDesc=cursor.getString(cursor.getColumnIndex(Course.Column_Description))
            var Coursepass=cursor.getInt(cursor.getColumnIndex(Course.Column_Password))
            var DoctorID=cursor.getString(cursor.getColumnIndex(Course.Column_Doc_ID))
            ListOfCourses!!.add(CourseStudent(Courscode,Coursetitle,CourseDesc,DoctorID,Coursepass))
            Isnotempty=cursor.moveToNext()
        }
    cursor.close()
    }

    override fun onRestart() {
        super.onRestart()
        getCourse()
        adapter!!.notifyDataSetChanged()

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.mainmenu,menu)
        menu!!.findItem(R.id.statisticsbtn).setVisible(false);
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item!!.itemId== R.id.logoutbtn)
        {
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        if(item!!.itemId== R.id.changePassbtn)
        {
            var intent=Intent(this, editPassword::class.java)
            startActivity(intent)
        }


        return true
    }


    inner class CourseAdabterlist():BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                var myview=layoutInflater.inflate(R.layout.scourseticket,null)
                var currentcourse=ListOfCourses!![position]
                myview.coursetitle.text=currentcourse.Title
                myview.coursedisc.text= currentcourse.Desc
                myview.doctorid.text=currentcourse.Doctor_ID
            myview.setOnClickListener {//if they clicked  on any course got to (lessons/materials/announcements)
                PickedCourse.Code=currentcourse.Course_Code
                PickedCourse.password=currentcourse.password
                PickedCourse.Description=currentcourse.Desc
                PickedCourse.Doc_id=currentcourse.Doctor_ID
                PickedCourse.Title=currentcourse.Title
                var intent=Intent(applicationContext, MainCourseDoctor::class.java)
                startActivity(intent)
            }
            myview.UnSubscribeCoursebtn.setOnClickListener {
                controller!!.UNSubscribeCourse(currentcourse.Course_Code,Studentinfo.Studentemail!!)
                getCourse()
                notifyDataSetChanged()

            }


                return myview
        }

        override fun getItem(position: Int): Any {
           return ListOfCourses!![position]
        }

        override fun getItemId(position: Int): Long {
           return position.toLong()
        }

        override fun getCount(): Int {
           return ListOfCourses!!.size
        }


    }
}
