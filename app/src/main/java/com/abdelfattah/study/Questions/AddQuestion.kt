package com.abdelfattah.study.Questions

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abdelfattah.study.LESSONS.Pickedlesson
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import kotlinx.android.synthetic.main.activity_add_question.*
import java.util.*

class AddQuestion : AppCompatActivity() {
var controller:Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_question)
        controller= Controller(this)
    }
    fun AddQuestionEvent(view: View)
    {
        var title=Qaddtitle.text.toString()
        var cont=Qaddcontent.text.toString()
     var questionnumber= controller!!.NewQuestionID(Pickedlesson.Course_code!!, Pickedlesson.Lesson_num!!)
        var success=controller!!.insertQuestion(Pickedlesson.Lesson_num!!,Pickedlesson.Course_code!!,Studentinfo.Studentemail!!, Date(),title,cont,questionnumber)
        if(success)
        {

            Toast.makeText(this,"inset question succesffuly",Toast.LENGTH_SHORT).show()
            finish()

        }
        else{

            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
        }

    }
}
