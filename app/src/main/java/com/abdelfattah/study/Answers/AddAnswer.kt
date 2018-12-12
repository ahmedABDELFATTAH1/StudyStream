package com.abdelfattah.study.Answers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import kotlinx.android.synthetic.main.activity_add_question.*

class AddAnswer : AppCompatActivity() {
    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_answer)
        controller= Controller(this)
    }


    fun AddAnswerEvent(view: View)
    {
        var title=Qaddtitle.text.toString()
        var cont=Qaddcontent.text.toString()
      //  var success=controller!!.
//        if(success)
//        {
//
//            Toast.makeText(this,"inset question succesffuly", Toast.LENGTH_SHORT).show()
//            finish()
//
//        }
//        else{
//
//            Toast.makeText(this,"Something went wrong", Toast.LENGTH_SHORT).show()
//        }

    }
}
