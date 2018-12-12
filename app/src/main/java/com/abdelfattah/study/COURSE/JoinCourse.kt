package com.abdelfattah.study.COURSE

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import kotlinx.android.synthetic.main.activity_join_course.*


class JoinCourse : AppCompatActivity() {
    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_course)
        controller= Controller(this)
    }

    fun JoinCbtn(view:View)
    {
        var coursecode= jcoursecodetext.text.toString()
        var coursepass=jcoursepassword.text.toString()
        if(coursecode!=""||coursepass!="")
        {
            if(controller!!.FindCourse(coursecode,coursepass))
            {

                if(controller!!.joincourse(coursecode))
                {
                    Toast.makeText(this,"Welcome To The Course", Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this,"Some Thing Went Wrong", Toast.LENGTH_SHORT).show()
                }


            }else
            {

                Toast.makeText(this,"Course code or Course Pass not Corerct", Toast.LENGTH_SHORT).show()

            }




        }else{

            Toast.makeText(this,"Please write all Course Information", Toast.LENGTH_SHORT).show()

        }





    }
}
