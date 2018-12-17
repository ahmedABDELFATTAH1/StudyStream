package com.abdelfattah.study.COURSE

import android.content.ContentValues
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abdelfattah.study.LoginSignUp.Doctorinfo
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract.*
import kotlinx.android.synthetic.main.activity_add_course.*

class AddCourse : AppCompatActivity() {
      var  controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        controller= Controller(this)
    }
    fun ADDCOURSEEVENT(view: View)
    {

        var CourseCode=CourseCodeText.text.toString()
        var CoursePass= CoursePassText.text.toString()
        var CourseTitle=CourseTilteText.text.toString()
        var CourseDisc=CourseDiscText.text.toString()
        if(CourseCode!=""||CoursePass!=""||CourseTitle!=""||CourseDisc!="")
        {
            var UsedBefore=controller!!.IsUsedCourseCode(CourseCode)
            if(!UsedBefore)
            {
                var content=ContentValues()
                content.put(Course.Column_Code,CourseCode.toInt())
                content.put(Course.Column_Doc_ID, Doctorinfo.email)
                content.put(Course.Column_Password,CoursePass.toInt())
                content.put(Course.Column_Title,CourseTitle)
                content.put(Course.Column_Description,CourseDisc)
                var success=controller!!.InsertNewCourse(Course.Table_Name,content)
                if(success)
                {
                    finish()

                }
                else{
                    Toast.makeText(this,"Some thing went wrong",Toast.LENGTH_SHORT).show()
                }


            }else
            {

                Toast.makeText(this,"This Course Code Is Already Used",Toast.LENGTH_SHORT).show()

            }
        }
        else{

            Toast.makeText(this,"Please write all Course Information",Toast.LENGTH_SHORT).show()
            return

        }


    }
}
