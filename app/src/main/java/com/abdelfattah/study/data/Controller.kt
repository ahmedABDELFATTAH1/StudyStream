package com.abdelfattah.study.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.abdelfattah.study.LoginSignUp.Doctorinfo
import com.abdelfattah.study.LoginSignUp.Studentinfo
import com.abdelfattah.study.data.StudyStreamContract.*
import java.util.*


class Controller {
    var db:DBHelper?=null

    constructor(context:Context)
    {
        db= DBHelper(context)


    }
    fun checkuser(email:String,pass:String):Boolean
    {
        var query="Select * from  "+ UserEntry.Table_Name +" where "+ UserEntry.Column_ID+" =? And "+UserEntry.Column_Password+" =?"
        var Argument= arrayOf(email,pass)
        var Cursor=db!!.Select(query,Argument)
        return Cursor.count>0
    }
    fun checkuserid(email:String):Boolean
    {
        var query="Select * from  "+ UserEntry.Table_Name +" where "+ UserEntry.Column_ID+" =?"
        var Argument= arrayOf(email)
        var Cursor=db!!.Select(query,Argument)
        return Cursor.count>0
    }
    fun checkkind(email: String):Boolean
    {
        var query="Select * from "+ Doctor.Table_Name+" where "+Doctor.Column_ID +"=? "
        var Argument= arrayOf(email)
        var Cursor=db!!.Select(query,Argument)
        return Cursor.count>0
    }
    fun UserData(email: String):Cursor
    {

        var query="Select * from "+UserEntry.Table_Name +" where "+UserEntry.Column_ID +" =? "
        var Argument= arrayOf(email)
        var Cursor=db!!.Select(query,Argument)
        Cursor.moveToFirst()
        return Cursor
    }
    fun insertuser(isDoctor:Boolean,Email:String,Firstname:String,Secondname:String,pass:String):Boolean
    {
        var content=ContentValues()
        content.put(UserEntry.Column_ID,Email)
        content.put(UserEntry.Column_Password,pass)
        content.put(UserEntry.Column_FirstName,Firstname)
        content.put(UserEntry.Column_SecondName,Secondname)
        if(checkuserid(Email))
        {
            return false
        }
        var sucess=db!!.insert(UserEntry.Table_Name,content)
        if(!sucess)
            return false
        content.clear()
        if(isDoctor)
        {
            content.put(Doctor.Column_ID,Email)
            sucess=db!!.insert(Doctor.Table_Name,content)
            if(!sucess)
                return false

        }
        else
        {
            content.put(Student.Column_ID,Email)
            sucess=db!!.insert(Student.Table_Name,content)
            if(!sucess)
                return false

        }
        return  true


    }
    fun JoindedCourses(studentemail:String):Cursor
    {

        var query="Select * From "+STUD_COURSE.Table_Name+" , "+Course.Table_Name+" Where "+STUD_COURSE.Column_Stud_ID+" =? and "+Course.Column_Code+" = "+STUD_COURSE.Column_Course_Code   //jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
        var argument= arrayOf(studentemail)
        var cursor=db!!.Select(query,argument)
        return cursor
    }

    fun MadeCourses(Doctorid:String):Cursor
    {
        var query="Select * from "+Course.Table_Name+" where "+Course.Column_Doc_ID+" =? "
        var Argument= arrayOf(Doctorid)
        var cursor=db!!.Select(query,Argument)
        return cursor

    }

    fun IsUsedCourseCode(CourseCode:String):Boolean
    {
        var query="Select * from "+Course.Table_Name+" where "+Course.Column_Code+" =? "
        var Argument= arrayOf(CourseCode)
        var cursor=db!!.Select(query,Argument)
        return cursor.count>0



    }
    fun InsertNewCourse(tablename:String,contentval:ContentValues):Boolean
    {
      return  db!!.insert(tablename,contentval)
    }

    fun FindCourse(coursecode:String,coursevalues:String):Boolean
    {
      var query="Select * from "+Course.Table_Name+" where "+Course.Column_Code+" =? and "+ Course.Column_Password+" =? "
        var Argument= arrayOf(coursecode,coursevalues)
       var cursor= db!!.Select(query,Argument)
        return cursor.count>0
    }
    fun joincourse(coursecode:String):Boolean
      {
          var contentvalue=ContentValues()
          contentvalue.put(STUD_COURSE.Column_Course_Code,coursecode)
          contentvalue.put(STUD_COURSE.Column_Stud_ID, Studentinfo.Studentemail)
          return  db!!.insert(STUD_COURSE.Table_Name,contentvalue)

      }

    fun updateBio(Bio:String):Boolean
    {
       var  contentval=ContentValues()
       contentval.put(Doctor.Column_BIO,Bio)
       return db!!.update(Doctor.Table_Name,contentval, arrayOf(Doctorinfo.email),Doctor.Column_ID+" =? ")

    }
   fun  getBio():String
   {
       var query ="Select "+Doctor.Column_BIO+" From "+Doctor.Table_Name+" Where "+Doctor.Column_ID+" =? "
       var Argument= arrayOf(Doctorinfo.email)
       var cursor=db!!.Select(query,Argument)
     var tru=  cursor.moveToFirst()
       if(tru)
       return cursor.getString(0)
       else
           return "You can add Bio here"
   }
    fun insertQuestion(lessonnum:Int,Coursenum:Int,StudentId:String,datee:Date,title:String,content:String,Questionnym:Int):Boolean
    {
        var contentvalue=ContentValues()
        contentvalue.put(Questions.Column_Lesson_Num,lessonnum)
        contentvalue.put(Questions.Column_Course_Code,Coursenum)
        contentvalue.put(Questions.Column_Stud_ID,StudentId)
        contentvalue.put(Questions.Column_Date,datee.toString())
        contentvalue.put(Questions.Column_Title,title)
        contentvalue.put(Questions.Column_Content,content)
        contentvalue.put(Questions.Column_Question_Num,Questionnym)
        return db!!.insert(Questions.Table_Name,contentvalue)
    }

    fun getQuestion(lesson_id:Int,Coursid:Int):Cursor
    {
        var query="Select * From "+Questions.Table_Name+" Where "+Questions.Column_Lesson_Num+" =? and "+Questions.Column_Course_Code+" =? "
        var Argument= arrayOf(lesson_id.toString(),Coursid.toString())
       return db!!.Select(query,Argument)
    }

    fun NewQuestionID(CourseCode: Int,Lessonnumber:Int): Int {
        // Select Max(Announcement_Number) from Announcements
        val query = ("select max( " + Questions.Column_Question_Num
                + " ) from " + Questions.Table_Name + " Where " + Questions.Column_Course_Code + " = ? and "+Questions.Column_Lesson_Num+" =?")
        val arguments = arrayOf(CourseCode.toString(),Lessonnumber.toString())

        val mCount = db!!.Select(query, arguments)
        mCount.moveToFirst()

        val maxID = mCount.getInt(0)

        mCount.close()

        return maxID + 1
    }

    fun SelectAllLessons(CourseCode: Int): Cursor {
        //select * from Lesson where Course_Code = course_code
        val query = "Select * from " + Lesson.Table_Name + " where " + Lesson.Column_Course_Code + " =? "
        val Argument = arrayOf(Integer.toString(CourseCode))
        return db!!.Select(query, Argument)

    }

}