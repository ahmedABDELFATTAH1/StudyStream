package com.abdelfattah.study.Answers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abdelfattah.study.LoginSignUp.Doctorinfo
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.Questions.QuestionObject
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import kotlinx.android.synthetic.main.activity_add_answer.*
import kotlinx.android.synthetic.main.activity_add_question.*
import java.util.*

class AddAnswer : AppCompatActivity() {
    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_answer)
        controller= Controller(this)
    }


    fun AddAnswerEvent(view: View)
    {
        var title=Aaddtitle.text.toString()
        var cont=Aaddcontent.text.toString()
        var useremai:String=""
        var ansid=controller!!.NewAnswerID(QuestionObject.coursenume,QuestionObject.lessonnume,QuestionObject.questionnum)
        if(Studentinfo.Studentemail!="Unknown")
        {
            useremai=Studentinfo.Studentemail!!

        }
        else{
            useremai=Doctorinfo.email!!

        }
        var success=controller!!.insertAnswer(title,cont,QuestionObject.coursenume,QuestionObject.lessonnume,QuestionObject.questionnum,useremai,Date(),ansid)
       if(success)
        {

           finish()

       }
       else{

           Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
       }

    }
}
