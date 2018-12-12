package com.abdelfattah.study.Answers

import android.content.ContentResolver
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.abdelfattah.study.LESSONS.Pickedlesson
import com.abdelfattah.study.Questions.QuestionObject
import com.abdelfattah.study.Questions.Questiondata
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract
import kotlinx.android.synthetic.main.activity_answers.*
import kotlinx.android.synthetic.main.answerticket.view.*
import kotlinx.android.synthetic.main.questiondoctorticket.view.*

class Answers : AppCompatActivity() {
var controller:Controller?=null
var listOfAnswers:ArrayList<Answerdata>?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
        listOfAnswers= ArrayList()
        controller= Controller(this)
        getAnswers()
        var adapter= AnswerAdabterlist()
        AnswersList.adapter=adapter
        adapter.notifyDataSetChanged()
    }

    fun AddAnswerEvent(view:View)
    {
        var intent=Intent(this,com.abdelfattah.study.Answers.AddAnswer::class.java)
        startActivity(intent)

    }
    fun getAnswers()
    {
        var cursor=controller!!.getAnswer(QuestionObject.coursenume!!, QuestionObject.lessonnume!!,QuestionObject.questionnum)
        var Isnotempty:Boolean=cursor.moveToFirst()
        while (Isnotempty)
        {
            var questionnum=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Question_Num))
            var lessonnumm=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Lesson_Num))
            var coursenumm=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Course_Code))
            var studentid=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Answers.Column_User_ID))
            var  TheDate=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Date))
            var  contentt=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Content))
            var  title=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Title))
            var Answernaumber=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Answers.Column_Answer_Num))
            Isnotempty=cursor.moveToNext()
          listOfAnswers!!.add(Answerdata(questionnum,Answernaumber,lessonnumm,coursenumm,studentid,TheDate,contentt,title))
        }

    }



    inner class AnswerAdabterlist(): BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.answerticket,null)
            var current= listOfAnswers!![position]
            myview.titletext.text=current.titlee
            myview.contenttext.text=current.conten
            myview.datetext.text=current.TheDate
            myview.useridtext.text=current.Userid
          myview.Upvotebtn.setOnClickListener {
              Toast.makeText(baseContext,"Upvote",Toast.LENGTH_SHORT).show()

          }
            myview.downvotebtn.setOnClickListener {
                Toast.makeText(baseContext,"downvote",Toast.LENGTH_SHORT).show()
            }
            myview.setOnClickListener {
                Toast.makeText(baseContext,"wladawlada",Toast.LENGTH_SHORT).show()
            }
            return myview
        }

        override fun getItem(position: Int): Any {
            return  listOfAnswers!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return  listOfAnswers!!.size
        }


    }
}
