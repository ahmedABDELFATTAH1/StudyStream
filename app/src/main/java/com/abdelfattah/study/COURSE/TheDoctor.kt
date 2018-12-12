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
import com.abdelfattah.study.data.CourseStudent
import com.abdelfattah.study.LoginSignUp.Doctorinfo
import com.abdelfattah.study.LoginSignUp.MainActivity
import com.abdelfattah.study.Questions.QuestionsDoctor
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract
import kotlinx.android.synthetic.main.activity_the_start.*
import kotlinx.android.synthetic.main.dcourseticket.view.*

class TheDoctor : AppCompatActivity() {

    var ListOfMadeCourses:ArrayList<CourseStudent>?=null
    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_start)
        setSupportActionBar(mytoolbarstart as Toolbar)
        ListOfMadeCourses=ArrayList()
        controller= Controller(this)
        GetMadeCourses()
       // GetBio()
        supportActionBar!!.title="Welcome Doctor "+ Doctorinfo.Fname
        var adapter=CourseAdabterlist()
        DCourseList.adapter=adapter
        adapter.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.mainmenu,menu)

        return true
    }
   fun GetBio()
{
    BioText.text= controller!!.getBio()

}


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item!!.itemId== R.id.logoutbtn)
        {
            Toast.makeText(this,"Hantala3k barra 7ader shoukrn ya mo7trm",Toast.LENGTH_LONG).show()
            var intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        return true
    }

fun GetMadeCourses()
{
    var cursor=controller!!.MadeCourses(Doctorinfo.email!!)
    var Isnotempty:Boolean=cursor.moveToFirst()
   //   Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
    while (Isnotempty)
    {
    //    Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
        var Courscode=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Course.Column_Code))
        var Coursetitle=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Course.Column_Title))
        var CourseDesc=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Course.Column_Description))
        var Coursepass=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Course.Column_Password))
        var DoctorID=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Course.Column_Doc_ID))
        ListOfMadeCourses!!.add(CourseStudent(Courscode,Coursetitle,CourseDesc,DoctorID,Coursepass))
        Isnotempty=cursor.moveToNext()
    }



}
    fun EditBioEvent(view:View)
    {

      var intent =Intent(this, AddBio::class.java)
        startActivity(intent)
    }

fun ADDCOURSEEVENT(view: View)
{
    val intent=Intent(this, AddCourse::class.java)
        startActivity(intent)


}
    inner class CourseAdabterlist(): BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.dcourseticket,null)
            var currentcourse= ListOfMadeCourses!![position]
            myview.ctitle.text=currentcourse.Title
            myview.cpass.text=currentcourse.password.toString()
            myview.cdisc.text=currentcourse.Desc
            myview.ccode.text=currentcourse.Course_Code.toString()

            myview.setOnClickListener {
                PickedCourse.Code=currentcourse.Course_Code
                PickedCourse.password=currentcourse.password
                PickedCourse.Description=currentcourse.Desc
                PickedCourse.Doc_id=currentcourse.Doctor_ID
                PickedCourse.Title=currentcourse.Title
                var intent=Intent(applicationContext,MainCourseDoctor::class.java)
                startActivity(intent)
            }
            return myview
        }

        override fun getItem(position: Int): Any {
            return  ListOfMadeCourses!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return  ListOfMadeCourses!!.size
        }


    }
}


