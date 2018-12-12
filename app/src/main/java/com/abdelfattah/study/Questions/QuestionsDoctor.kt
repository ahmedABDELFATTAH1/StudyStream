package com.abdelfattah.study.Questions

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.abdelfattah.study.LESSONS.Pickedlesson
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.DBHelper
import com.abdelfattah.study.data.StudyStreamContract
import kotlinx.android.synthetic.main.activity_questions_doctor.*
import kotlinx.android.synthetic.main.questiondoctorticket.view.*

class QuestionsDoctor : AppCompatActivity() {
   var ListofQuestions:ArrayList<Questiondata>?=null
    var controller:Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions_doctor)
        DBHelper(this)
        controller= Controller(this)
        ListofQuestions= ArrayList()
        getQuestions()
        var adapter=QuestionsAdabterlist()
        DQList.adapter=adapter
        adapter.notifyDataSetChanged()
    }

 fun getQuestions()
 {
     var cursor=controller!!.getQuestion(Pickedlesson.Lesson_num!!,Pickedlesson.Course_code!!)
     var Isnotempty:Boolean=cursor.moveToFirst()
     while (Isnotempty)
     {
       var questionnum=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Question_Num))
         var lessonnumm=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Lesson_Num))
         var coursenumm=cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Course_Code))
         var studentid=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Stud_ID))
         var  TheDate=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Date))
         var  contentt=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Content))
         var  title=cursor.getString(cursor.getColumnIndex(StudyStreamContract.Questions.Column_Title))
         Isnotempty=cursor.moveToNext()
         ListofQuestions!!.add(Questiondata(questionnum,lessonnumm,coursenumm,studentid,TheDate,contentt,title))
     }

 }



    inner class QuestionsAdabterlist(): BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.questiondoctorticket,null)
            myview.QDatetext.text=ListofQuestions!![position].TheDate
            myview.Qcontenttext.text=ListofQuestions!![position].content
            myview.Qtitletext.text=ListofQuestions!![position].title
            myview.setOnClickListener {
                QuestionObject.TheDate= ListofQuestions!![position].TheDate
              QuestionObject.contente= ListofQuestions!![position].content
              QuestionObject.titlee=  ListofQuestions!![position].title
                QuestionObject.coursenume= ListofQuestions!![position].coursenum
                QuestionObject.lessonnume=ListofQuestions!![position].lessonnum
                QuestionObject.questionnum=ListofQuestions!![position].questionnum
                QuestionObject.studentid=ListofQuestions!![position].studentid

            }
            return myview
        }

        override fun getItem(position: Int): Any {
            return  ListofQuestions!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return  ListofQuestions!!.size
        }


    }


}