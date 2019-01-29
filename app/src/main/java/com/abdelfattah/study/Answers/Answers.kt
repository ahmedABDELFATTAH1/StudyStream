package com.abdelfattah.study.Answers

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.abdelfattah.study.LESSONS.Pickedlesson
import com.abdelfattah.study.LoginSignUp.Doctorinfo
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.Questions.QuestionObject
import com.abdelfattah.study.Questions.Questiondata
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract
import kotlinx.android.synthetic.main.activity_answers.*
import kotlinx.android.synthetic.main.activity_question_student.*
import kotlinx.android.synthetic.main.answerticket.view.*
import kotlinx.android.synthetic.main.questiondoctorticket.view.*

class Answers : AppCompatActivity() {
var controller:Controller?=null
var listOfAnswers:ArrayList<Answerdata>?=null
    var rating:Int?=0
    var useremail:String?=null
    var adapter:AnswerAdabterlist?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answers)
        listOfAnswers= ArrayList()
        controller= Controller(this)
        getAnswers()
        adapter= AnswerAdabterlist()
        AnswersList.adapter=adapter
        if(Doctorinfo.email!="Unknown")
        {
            useremail= Doctorinfo.email
            rating=5
        }
        else{
            useremail= Studentinfo.Studentemail
            rating=1
        }
    }

    override fun onRestart() {
        super.onRestart()
        getAnswers()
        adapter!!.notifyDataSetChanged()

    }

    fun AddAnswerEvent(view:View)
    {
        var intent=Intent(this,com.abdelfattah.study.Answers.AddAnswer::class.java)
        startActivity(intent)

    }
    fun getAnswers()
    {
        listOfAnswers!!.clear()
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
            var rating=controller!!.ARating(coursenumm.toString(),lessonnumm.toString(),questionnum.toString(),Answernaumber.toString())
            listOfAnswers!!.add(Answerdata(questionnum,Answernaumber,lessonnumm,coursenumm,studentid,TheDate,contentt,title,rating))
            Isnotempty=cursor.moveToNext()
        }
        sortlist()



    }
fun sortlist()
    {
        var max=0
        for (i in 0..listOfAnswers!!.count()-1)
        {
            for(j in i+1..listOfAnswers!!.count()-1)
            {
                if (listOfAnswers!![j].Rating>listOfAnswers!![max].Rating)
                {
                    max =j
                }

            }
            swap(i,max)
            max =i+1
        }




    }

    fun swap(j:Int,max:Int)
    {
         var maxtitle=listOfAnswers!![max].titlee
         var maxcon=listOfAnswers!![max].conten
        var maxuser=listOfAnswers!![max].Userid
        var maxdate=listOfAnswers!![max].TheDate
        var maxrate=listOfAnswers!![max].Rating
        var maxln=listOfAnswers!![max].lessonnum
        var maxcn=listOfAnswers!![max].coursenum
        var qn=listOfAnswers!![max].questionnum
        var maxan=listOfAnswers!![max].Answernum
        ///////////////////////////////////////////////
        listOfAnswers!![max].titlee=listOfAnswers!![j].titlee
        listOfAnswers!![max].conten=listOfAnswers!![j].conten
        listOfAnswers!![max].Userid=listOfAnswers!![j].Userid
        listOfAnswers!![max].TheDate=listOfAnswers!![j].TheDate
        listOfAnswers!![max].Answernum=listOfAnswers!![j].Answernum
        listOfAnswers!![max].lessonnum=listOfAnswers!![j].lessonnum
        listOfAnswers!![max].coursenum=listOfAnswers!![j].coursenum
        listOfAnswers!![max].questionnum=listOfAnswers!![j].questionnum
        listOfAnswers!![max].Rating=listOfAnswers!![j].Rating
        //////////////////
        listOfAnswers!![j].titlee=maxtitle
        listOfAnswers!![j].conten=maxcon
        listOfAnswers!![j].Userid=maxuser
        listOfAnswers!![j].TheDate=maxdate
        listOfAnswers!![j].Rating=maxrate
        listOfAnswers!![j].lessonnum=maxln
        listOfAnswers!![j].coursenum=maxcn
        listOfAnswers!![j].questionnum=qn
        listOfAnswers!![j].Answernum=maxan

    }


    inner class AnswerAdabterlist(): BaseAdapter()
    {



        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var myview=layoutInflater.inflate(R.layout.answerticket,null)

            var currentAnswer= listOfAnswers!![position]
            myview.titletext.text=currentAnswer.titlee
            myview.contenttext.text=currentAnswer.conten
            myview.datetext.text=currentAnswer.TheDate
            myview.useridtext.text=currentAnswer.Userid
            myview.RateText.text=currentAnswer.Rating.toString()
            if(currentAnswer.Rating>0)
            {
                myview.RateText.setTextColor(Color.BLUE)
            }
            if(currentAnswer.Rating<0)
            {
                myview.RateText.setTextColor(Color.RED)

            }

            var thereA=controller!!.getAnswerbyuser(currentAnswer.coursenum,currentAnswer.lessonnum,currentAnswer.questionnum,currentAnswer.Answernum, useremail!!)
            if(Doctorinfo.email=="Unknown"&&!thereA)
            {
                myview.ADeletebtn.visibility=View.GONE
            }
            myview.ADeletebtn.setOnClickListener {

                controller!!.DelteAnswer(currentAnswer.coursenum,currentAnswer.lessonnum,currentAnswer.questionnum,currentAnswer.Answernum)
                getAnswers()
                notifyDataSetChanged()
            }
            myview.Upvotebtn.setOnClickListener {
              if(controller!!.Aisalreadyrate(currentAnswer.coursenum.toString(),currentAnswer.lessonnum.toString(),currentAnswer.questionnum.toString(),useremail.toString(),currentAnswer.Answernum.toString()))
              {

                  var success=  controller!!.Aupdaterate(currentAnswer.coursenum.toString(),currentAnswer.lessonnum.toString(),currentAnswer.questionnum.toString(),useremail!!,rating!!,currentAnswer.Answernum.toString())
                  if(success)
                  {
                      getAnswers()
                      notifyDataSetChanged()
                  }

              }
              else{

                  var success= controller!!.Ainsertnewrate(currentAnswer.coursenum,currentAnswer.lessonnum,currentAnswer.questionnum,useremail!!,rating!!,currentAnswer.Answernum)
                  if(success)
                  {
                      getAnswers()
                      notifyDataSetChanged()

                  }

              }


          }
            myview.NORATING.setOnClickListener {

                if(controller!!.Aisalreadyrate(currentAnswer.coursenum.toString(),currentAnswer.lessonnum.toString(),currentAnswer.questionnum.toString(),useremail.toString(),currentAnswer.Answernum.toString()))
                {

                    var success=  controller!!.Aupdaterate(currentAnswer.coursenum.toString(),currentAnswer.lessonnum.toString(),currentAnswer.questionnum.toString(),useremail!!,0!!,currentAnswer.Answernum.toString())
                    if(success)
                    {
                        getAnswers()
                        notifyDataSetChanged()


                    }

                }
                else{

                    var success= controller!!.Ainsertnewrate(currentAnswer.coursenum,currentAnswer.lessonnum,currentAnswer.questionnum,useremail!!,0!!,currentAnswer.Answernum)
                    if(success)
                    {
                        getAnswers()
                        notifyDataSetChanged()


                    }

                }


            }
            myview.downvotebtn.setOnClickListener {
                if(controller!!.Aisalreadyrate(currentAnswer.coursenum.toString(),currentAnswer.lessonnum.toString(),currentAnswer.questionnum.toString(),useremail.toString(),currentAnswer.Answernum.toString()))
                {

                    var success=  controller!!.Aupdaterate(currentAnswer.coursenum.toString(),currentAnswer.lessonnum.toString(),currentAnswer.questionnum.toString(),useremail!!,-rating!!,currentAnswer.Answernum.toString())
                    if(success)
                    {

                        getAnswers()
                        notifyDataSetChanged()


                    }

                }
                else{

                    var success= controller!!.Ainsertnewrate(currentAnswer.coursenum,currentAnswer.lessonnum,currentAnswer.questionnum,useremail!!,-rating!!,currentAnswer.Answernum)
                    if(success)
                    {
                        getAnswers()
                        notifyDataSetChanged()


                    }

                }

            }
            myview.setOnClickListener {

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
