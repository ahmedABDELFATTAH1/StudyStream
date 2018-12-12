package com.abdelfattah.study.CourseMainMenu.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.abdelfattah.study.COURSE.PickedCourse
import com.abdelfattah.study.CourseMainMenu.Fragments.ADD.AddAnnouncement
import com.abdelfattah.study.CourseMainMenu.Fragments.ADD.AddLesson
import com.abdelfattah.study.LESSONS.Lessonsdata
import com.abdelfattah.study.LESSONS.Pickedlesson
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.Questions.QuestionObject
import com.abdelfattah.study.Questions.QuestionStudent
import com.abdelfattah.study.Questions.QuestionsDoctor

import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import com.abdelfattah.study.data.StudyStreamContract
import com.abdelfattah.study.data.StudyStreamContract.*
import kotlinx.android.synthetic.main.dcourseticket.view.*
import kotlinx.android.synthetic.main.fragment_lessons.*
import kotlinx.android.synthetic.main.fragment_lessons.view.*
import kotlinx.android.synthetic.main.lessonticket.view.*


class Lessonkotlin : Fragment() {
    var controller:Controller?=null

    private var listener: OnFragmentInteractionListener? = null
    var ListofLessons:ArrayList<Lessonsdata>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller= Controller(context!!)
        ListofLessons= ArrayList()
        GetAllLessons()


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val myview = inflater.inflate(R.layout.fragment_lessons, container, false)
        if (Studentinfo.Studentemail !== "Unknown") {
            myview.AddLesson.visibility = View.GONE
        }
        var adapter=LessonAdabterlist()
        myview.LessontList.adapter=adapter
        adapter.notifyDataSetChanged()
        myview.AddLesson.setOnClickListener {
            val intent = Intent(context,com.abdelfattah.study.CourseMainMenu.Fragments.ADD.AddLesson::class.java)
            startActivity(intent)
        }
        return myview
    }

    fun GetAllLessons()
    {
        var cursor =controller!!.SelectAllLessons(PickedCourse.Code)
        var Isnotempty:Boolean=cursor.moveToFirst()
        //   Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
        while (Isnotempty)
        {
            var Courscode=cursor.getInt(cursor.getColumnIndex(Lesson.Column_Course_Code))
            var lessonid=cursor.getInt(cursor.getColumnIndex(Lesson.Column_Lesson_Num))
            var title=cursor.getString(cursor.getColumnIndex(Lesson.Column_Title))
            ListofLessons!!.add(Lessonsdata(Courscode,lessonid,title))
            Isnotempty=cursor.moveToNext()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {

        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                Lessonkotlin().apply {

                }
    }
    inner class LessonAdabterlist(): BaseAdapter()
    {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var myview=layoutInflater.inflate(R.layout.lessonticket,null)
            var currentlesson= ListofLessons!![position]
            myview.LessonTitletxt.text=currentlesson.Title

            myview.setOnClickListener {
               Pickedlesson.Course_code=currentlesson.Course_code
                Pickedlesson.Lesson_num=currentlesson.Lesson_num
                Pickedlesson.Title=currentlesson.Title
                var intent=Intent(context,QuestionStudent::class.java)
                startActivity(intent)
            }
            return myview
        }

        override fun getItem(position: Int): Any {
            return  ListofLessons!![position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return  ListofLessons!!.size
        }


    }
}
