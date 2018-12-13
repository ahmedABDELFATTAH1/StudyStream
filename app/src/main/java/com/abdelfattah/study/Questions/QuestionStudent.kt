package com.abdelfattah.study.Questions

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.abdelfattah.study.Answers.Answers
import com.abdelfattah.study.LESSONS.Pickedlesson
import com.abdelfattah.study.LoginSignUp.Doctorinfo
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract
import kotlinx.android.synthetic.main.activity_add_lesson.view.*
import kotlinx.android.synthetic.main.activity_question_student.*
import kotlinx.android.synthetic.main.activity_the_start.*
import kotlinx.android.synthetic.main.questiondoctorticket.view.*

class QuestionStudent : AppCompatActivity() {
    var ListofQuestions:ArrayList<Questiondata>?=null
    var controller: Controller?=null
    var rating:Int?=0
    var useremail:String?=null
    var adapter:QuestionsAdabterlist?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_student)
        controller= Controller(this)
        ListofQuestions= ArrayList()
        getQuestions()
        adapter=QuestionsAdabterlist()
        SQList.adapter=adapter
        adapter!!.notifyDataSetChanged()
        if(Doctorinfo.email!="Unknown")
        {
            useremail=Doctorinfo.email
            AddQuestionbtn.visibility=View.GONE
            rating=5
        }
        else{
            useremail=Studentinfo.Studentemail
            rating=1
        }
    }

    fun AddQuestionEvent(view: View)
    {
        var intent= Intent(this,AddQuestion::class.java)
        startActivity(intent)
    }


    fun getQuestions()
    {
        ListofQuestions!!.clear()
        var cursor=controller!!.getQuestion(Pickedlesson.Lesson_num!!, Pickedlesson.Course_code!!)
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
            var rating=controller!!.Rating(coursenumm.toString(),lessonnumm.toString(),questionnum.toString())
            ListofQuestions!!.add(Questiondata(questionnum,lessonnumm,coursenumm,studentid,TheDate,contentt,title,rating))
        }
    }

    inner class QuestionsAdabterlist(): BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.questiondoctorticket,null)
            var currentquestion=ListofQuestions!![position]
            myview.QDatetext.text=currentquestion.TheDate
            myview.Qcontenttext.text=currentquestion.content
            myview.Qtitletext.text=currentquestion.title
            myview.rateText.text=currentquestion.Rating.toString()
            myview.setOnClickListener {
                QuestionObject.TheDate= currentquestion.TheDate
                QuestionObject.contente= currentquestion.content
                QuestionObject.titlee=  currentquestion.title
                QuestionObject.coursenume= currentquestion.coursenum
                QuestionObject.lessonnume=currentquestion.lessonnum
                QuestionObject.questionnum=currentquestion.questionnum
                QuestionObject.studentid=currentquestion.studentid
                var intent=Intent(baseContext, Answers::class.java)
                startActivity(intent)
            }
            myview.downvotebtn2.setOnClickListener {
                if(controller!!.Qisalreadyrate(currentquestion.coursenum.toString(),currentquestion.lessonnum.toString(),currentquestion.questionnum.toString(),useremail.toString()))
                {

               var success=  controller!!.Qupdaterate(currentquestion.coursenum.toString(),currentquestion.lessonnum.toString(),currentquestion.questionnum.toString(),useremail!!,-rating!!)
                    if(success)
                    {
                        myview.downvotebtn2.setTextColor(Color.BLUE)
                        myview.upvotebtn.setTextColor(Color.BLACK)

                    }

                }
                else{

                   var success= controller!!.Qinsertnewrate(currentquestion.coursenum,currentquestion.lessonnum,currentquestion.questionnum,useremail!!,-rating!!)
                    if(success)
                    {
                        myview.downvotebtn2.setTextColor(Color.BLUE)
                        myview.upvotebtn.setTextColor(Color.BLACK)
                    }

                }
                notifyDataSetChanged()

            }
            myview.upvotebtn.setOnClickListener {

                if(controller!!.Qisalreadyrate(currentquestion.coursenum.toString(),currentquestion.lessonnum.toString(),currentquestion.questionnum.toString(),useremail.toString()))
                {
                    notifyDataSetChanged()
                    var success=  controller!!.Qupdaterate(currentquestion.coursenum.toString(),currentquestion.lessonnum.toString(),currentquestion.questionnum.toString(),useremail!!,rating!!)
                    if(success)
                    {
                        myview.upvotebtn.setTextColor(Color.RED)
                        myview.downvotebtn2.setTextColor(Color.BLACK)

                    }

                }
                else{

                    var success= controller!!.Qinsertnewrate(currentquestion.coursenum,currentquestion.lessonnum,currentquestion.questionnum,useremail!!,rating!!)
                    if(success)
                    {
                        myview.upvotebtn.setTextColor(Color.RED)
                        myview.downvotebtn2.setTextColor(Color.BLACK)
                    }

                }
                notifyDataSetChanged()
            }
            myview.norating.setOnClickListener {

                if(controller!!.Qisalreadyrate(currentquestion.coursenum.toString(),currentquestion.lessonnum.toString(),currentquestion.questionnum.toString(),useremail.toString()))
                {

                    var success=  controller!!.Qupdaterate(currentquestion.coursenum.toString(),currentquestion.lessonnum.toString(),currentquestion.questionnum.toString(),useremail!!,0!!)
                    if(success)
                    {
                        notifyDataSetChanged()
                        myview.upvotebtn.setTextColor(Color.BLACK)
                        myview.downvotebtn2.setTextColor(Color.BLACK)

                    }

                }
                else{

                    var success= controller!!.Qinsertnewrate(currentquestion.coursenum,currentquestion.lessonnum,currentquestion.questionnum,useremail!!,0!!)
                    if(success)
                    {
                        myview.upvotebtn.setTextColor(Color.BLACK)
                        myview.downvotebtn2.setTextColor(Color.BLACK)
                    }

                }

                notifyDataSetChanged()

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
