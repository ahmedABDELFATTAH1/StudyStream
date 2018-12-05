package com.abdelfattah.study

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.view.View
import com.abdelfattah.study.data.CourseStudent
import com.abdelfattah.study.data.DBHelper
import com.abdelfattah.study.data.StudyStreamContract
import com.abdelfattah.study.data.StudyStreamContract.*

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




}