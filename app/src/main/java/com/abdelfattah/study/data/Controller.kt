package com.abdelfattah.study.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.media.Rating
import com.abdelfattah.study.Answers.AnswerObject
import com.abdelfattah.study.Answers.AnswerObject.coursenum
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
    //check if the user email already used or not(must be uniqe)
    fun checkuserid(email:String):Boolean
    {
        var query="Select * from  "+ UserEntry.Table_Name +" where "+ UserEntry.Column_ID+" =?"
        var Argument= arrayOf(email)
        var Cursor=db!!.Select(query,Argument)
        return Cursor.count>0
    }

    //update password /nassar part
    fun UpdatePass(Useremail:String,Pass:Int):Boolean
    {
        var contentval = ContentValues()
        contentval.put(UserEntry.Column_Password,Pass.toInt())
        var whereclause=UserEntry.Column_ID+" =? "
        var argument= arrayOf(Useremail)
        var success= db!!.update(UserEntry.Table_Name,contentval,argument,whereclause)
        return success
    }


    //check if the user is a doctor or student
    fun checkkind(email: String):Boolean
    {
        var query="Select * from "+ Doctor.Table_Name+" where "+Doctor.Column_ID +"=? "
        var Argument= arrayOf(email)
        var Cursor=db!!.Select(query,Argument)
        return Cursor.count>0
    }

    //get all the user data from his email(uniqe id)
    fun UserData(email: String):Cursor
    {

        var query="Select * from "+UserEntry.Table_Name +" where "+UserEntry.Column_ID +" =? "
        var Argument= arrayOf(email)
        var Cursor=db!!.Select(query,Argument)
        Cursor.moveToFirst()
        return Cursor
    }

   /*
    1-check if the user already exist if yes return false
    2-insert the user data in the base table
    3-check if doctor insert in the doctor table the same for the student
    4-return true(success)
 */
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
    //get all courses that the student has joind
    fun JoindedCourses(studentemail:String):Cursor
    {

        var query="Select * From "+STUD_COURSE.Table_Name+" , "+Course.Table_Name+" Where "+STUD_COURSE.Column_Stud_ID+" =? and "+Course.Column_Code+" = "+STUD_COURSE.Column_Course_Code   //jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj
        var argument= arrayOf(studentemail)
        var cursor=db!!.Select(query,argument)
        return cursor
    }
//get all courses that the doctor has created
    fun MadeCourses(Doctorid:String):Cursor
    {
        var query="Select * from "+Course.Table_Name+" where "+Course.Column_Doc_ID+" =? "
        var Argument= arrayOf(Doctorid)
        var cursor=db!!.Select(query,Argument)
        return cursor

    }
    //return true if this course code already exists
    fun IsUsedCourseCode(CourseCode:String):Boolean
    {
        var query="Select * from "+Course.Table_Name+" where "+Course.Column_Code+" =? "
        var Argument= arrayOf(CourseCode)
        var cursor=db!!.Select(query,Argument)
        return cursor.count>0
    }
    //insert a new course to the data base
    fun InsertNewCourse(tablename:String,contentval:ContentValues):Boolean
    {
      return  db!!.insert(tablename,contentval)
    }
    //return true if the student entered an existing course information ----> join course
    fun FindCourse(coursecode:String,coursevalues:String):Boolean
    {
      var query="Select * from "+Course.Table_Name+" where "+Course.Column_Code+" =? and "+ Course.Column_Password+" =? "
        var Argument= arrayOf(coursecode,coursevalues)
       var cursor= db!!.Select(query,Argument)
        return cursor.count>0
    }

    //join course
    fun joincourse(coursecode:String):Boolean
      {
          var contentvalue=ContentValues()
          contentvalue.put(STUD_COURSE.Column_Course_Code,coursecode)
          contentvalue.put(STUD_COURSE.Column_Stud_ID, Studentinfo.Studentemail)
          return  db!!.insert(STUD_COURSE.Table_Name,contentvalue)

      }

    //insert new Bio for doctor
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
       try {
        return   cursor.getString(0)
       }catch (e:Exception){}

           return "Oops! No Bio Added Yet."
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
    fun getQuestionbystudent(lesson_id:Int,Coursid:Int,studentnumber:String,Qnumber:Int):Boolean
    {
        var query="Select * From "+Questions.Table_Name+" Where "+Questions.Column_Lesson_Num+" =? and "+Questions.Column_Course_Code+" =? and "+Questions.Column_Stud_ID+" =? and "+Questions.Column_Question_Num+" =? "
        var Argument= arrayOf(lesson_id.toString(),Coursid.toString(),studentnumber,Qnumber.toString())
        var cursor= db!!.Select(query,Argument)
        return cursor.count>0
    }
    fun DelteQuestion(CN:Int,LN:Int,QN:Int):Boolean
    {
        var whereclause=Questions.Column_Course_Code+" =? and "+Questions.Column_Lesson_Num+" =? and "+Questions.Column_Question_Num+" =? "
        var Argument= arrayOf(CN.toString(),LN.toString(),QN.toString())
        return db!!.delete(Questions.Table_Name,Argument,whereclause)

    }
    fun getAnswer(Coursid:Int,lesson_id:Int,questionid:Int):Cursor
    {
        var query="Select * From "+Answers.Table_Name+" Where "+Answers.Column_Lesson_Num+" =? and "+Answers.Column_Course_Code+" =? and "+Answers.Column_Question_Num+" =? "
        var Argument= arrayOf(lesson_id.toString(),Coursid.toString(),questionid.toString())
        return db!!.Select(query,Argument)

    }
    fun getAnswerbyuser(Coursid:Int,lesson_id:Int,questionid:Int,Answernum:Int,UserID:String):Boolean
    {
        var query="Select * From "+Answers.Table_Name+" Where "+Answers.Column_Lesson_Num+" =? and "+Answers.Column_Course_Code+" =? and "+Answers.Column_Question_Num+" =? and "+Answers.Column_Answer_Num+" =? and "+Answers.Column_User_ID+" =? "
        var Argument= arrayOf(lesson_id.toString(),Coursid.toString(),questionid.toString(),Answernum.toString(),UserID)
        var cursor= db!!.Select(query,Argument)
        return cursor.count>0
    }
    fun DelteAnswer(CN:Int,LN:Int,QN:Int,AN:Int):Boolean
    {
        var whereclause=Answers.Column_Course_Code+" =? and "+Answers.Column_Lesson_Num+" =? and "+Answers.Column_Question_Num+" =? and "+Answers.Column_Answer_Num+" =? "
        var Argument= arrayOf(CN.toString(),LN.toString(),QN.toString(),AN.toString())
        return db!!.delete(Answers.Table_Name,Argument,whereclause)

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

    fun NewAnswerID(CourseCode: Int,Lessonnumber:Int,questionnumber:Int): Int {

        val query = ("select max( " + Answers.Column_Answer_Num
                + " ) from " + Answers.Table_Name + " Where " + Answers.Column_Course_Code + " = ? and "+Answers.Column_Lesson_Num+" =? and "+Answers.Column_Question_Num+" =? ")
        val arguments = arrayOf(CourseCode.toString(),Lessonnumber.toString(),questionnumber.toString())

        val mCount = db!!.Select(query, arguments)
        mCount.moveToFirst()

        val maxID = mCount.getInt(0)

        mCount.close()

        return maxID + 1
    }
    fun QRating(coursenum:String,Lessonnum:String,Questionnum:String):Int
    {
        var query="Select Sum( "+USER_VOTE_QUESTION.Column_Rating+" ) from "+USER_VOTE_QUESTION.Table_Name+" where "+USER_VOTE_QUESTION.Column_Course_Code+" =? and "+USER_VOTE_QUESTION.Column_Lesson_Num+" =?  and "+USER_VOTE_QUESTION.Column_Question_Num+" =? "
        var argument= arrayOf(coursenum,Lessonnum,Questionnum)
      var cursor=  db!!.Select(query,argument)
        cursor.moveToFirst()
        return cursor.getInt(0)

    }
    fun ARating(coursenum:String,Lessonnum:String,Questionnum:String,Answernum:String):Int
    {
        var query="Select Sum( "+USER_VOTE_ANSWERS.Column_Rating+" ) from "+USER_VOTE_ANSWERS.Table_Name+" where "+USER_VOTE_ANSWERS.Column_Course_Code+" =? and "+USER_VOTE_ANSWERS.Column_Lesson_Num+" =?  and "+USER_VOTE_ANSWERS.Column_Question_Num+" =? and "+USER_VOTE_ANSWERS.Column_Answer_Num+" =? "
        var argument= arrayOf(coursenum,Lessonnum,Questionnum,Answernum)
        var cursor=  db!!.Select(query,argument)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }


    fun SelectAllLessons(CourseCode: Int): Cursor {
        //select * from Lesson where Course_Code = course_code
        val query = "Select * from " + Lesson.Table_Name + " where " + Lesson.Column_Course_Code + " =? "
        val Argument = arrayOf(Integer.toString(CourseCode))
        return db!!.Select(query, Argument)

    }

    fun  insertAnswer(title:String,cont:String,Cnum:Int,Lnum:Int,Qnum:Int,useremail:String,datee:Date,Anum:Int):Boolean
    {
        var contentvalues=ContentValues()
        contentvalues.put(Answers.Column_Answer_Num,Anum)
        contentvalues.put(Answers.Column_Title,title)
        contentvalues.put(Answers.Column_Content,cont)
        contentvalues.put(Answers.Column_Course_Code,Cnum)
        contentvalues.put(Answers.Column_Lesson_Num,Lnum)
        contentvalues.put(Answers.Column_Question_Num,Qnum)
        contentvalues.put(Answers.Column_User_ID,useremail)
        contentvalues.put(Answers.Column_Date,datee.toString())
        return db!!.insert(Answers.Table_Name,contentvalues)
    }
    fun Qisalreadyrate(coursenum:String,Lessonnum:String,Questionnum:String,useremai:String):Boolean
    {
        var query="Select * from "+USER_VOTE_QUESTION.Table_Name+" where "+USER_VOTE_QUESTION.Column_Course_Code+" =? and "+USER_VOTE_QUESTION.Column_Lesson_Num+" =? and "+USER_VOTE_QUESTION.Column_Question_Num+" =? and "+USER_VOTE_QUESTION.Column_USER_ID+" =? "
        var Argument= arrayOf(coursenum,Lessonnum,Questionnum,useremai)
      var cursor =db!!.Select(query,Argument)
        return cursor.count>0
    }
    fun Qupdaterate(coursenum:String,Lessonnum:String,Questionnum:String,useremail:String,rating:Int):Boolean
    {
        var contentvalues=ContentValues()
        contentvalues.put(USER_VOTE_QUESTION.Column_Rating,rating)
        var whereclause=USER_VOTE_QUESTION.Column_Course_Code+" =? and "+USER_VOTE_QUESTION.Column_Lesson_Num+" =? and "+USER_VOTE_QUESTION.Column_Question_Num+" =? and "+USER_VOTE_QUESTION.Column_USER_ID+" =?"
        var Argument= arrayOf(coursenum,Lessonnum,Questionnum,useremail)
        return db!!.update(USER_VOTE_QUESTION.Table_Name,contentvalues,Argument,whereclause)

    }
    fun Qinsertnewrate(coursenum:Int,Lessonnum:Int,Questionnum:Int,useremail:String,rating:Int):Boolean
    {
        var contentvalues=ContentValues()
        contentvalues.put(USER_VOTE_QUESTION.Column_Course_Code,coursenum)
        contentvalues.put(USER_VOTE_QUESTION.Column_Lesson_Num,Lessonnum)
        contentvalues.put(USER_VOTE_QUESTION.Column_Question_Num,Questionnum)
        contentvalues.put(USER_VOTE_QUESTION.Column_USER_ID,useremail)
        contentvalues.put(USER_VOTE_QUESTION.Column_Rating,rating)
      return  db!!.insert(USER_VOTE_QUESTION.Table_Name,contentvalues)

    }
    fun Aisalreadyrate(coursenum:String,Lessonnum:String,Questionnum:String,useremai:String,answernumber:String):Boolean
    {
        var query="Select * from "+USER_VOTE_ANSWERS.Table_Name+" where "+USER_VOTE_ANSWERS.Column_Course_Code+" =? and "+USER_VOTE_ANSWERS.Column_Lesson_Num+" =? and "+USER_VOTE_ANSWERS.Column_Question_Num+" =? and "+USER_VOTE_ANSWERS.Column_USER_ID+" =? and "+USER_VOTE_ANSWERS.Column_Answer_Num+" =? "
        var Argument= arrayOf(coursenum,Lessonnum,Questionnum,useremai,answernumber)
        var cursor =db!!.Select(query,Argument)
        return cursor.count>0
    }
    fun Aupdaterate(coursenum:String,Lessonnum:String,Questionnum:String,useremail:String,rating:Int,answernumber:String):Boolean
    {
        var contentvalues=ContentValues()
        contentvalues.put(USER_VOTE_QUESTION.Column_Rating,rating)
        var whereclause=USER_VOTE_ANSWERS.Column_Course_Code+" =? and "+USER_VOTE_ANSWERS.Column_Lesson_Num+" =? and "+USER_VOTE_ANSWERS.Column_Question_Num+" =? and "+USER_VOTE_ANSWERS.Column_USER_ID+" =? and "+USER_VOTE_ANSWERS.Column_Answer_Num+" =? "
        var Argument= arrayOf(coursenum,Lessonnum,Questionnum,useremail,answernumber)
        return db!!.update(USER_VOTE_ANSWERS.Table_Name,contentvalues,Argument,whereclause)

    }
    fun Ainsertnewrate(coursenum:Int,Lessonnum:Int,Questionnum:Int,useremail:String,rating:Int,Answernum:Int):Boolean
    {
        var contentvalues=ContentValues()
        contentvalues.put(USER_VOTE_ANSWERS.Column_Course_Code,coursenum)
        contentvalues.put(USER_VOTE_ANSWERS.Column_Lesson_Num,Lessonnum)
        contentvalues.put(USER_VOTE_ANSWERS.Column_Question_Num,Questionnum)
        contentvalues.put(USER_VOTE_ANSWERS.Column_USER_ID,useremail)
        contentvalues.put(USER_VOTE_ANSWERS.Column_Rating,rating)
        contentvalues.put(USER_VOTE_ANSWERS.Column_Answer_Num,Answernum)
        return  db!!.insert(USER_VOTE_ANSWERS.Table_Name,contentvalues)
    }
    fun DeleteLesson(CC:Int,LN: Int):Boolean
    {
        var whereclause=Lesson.Column_Course_Code+" =? and "+Lesson.Column_Lesson_Num+" =? "
        var Argument= arrayOf(CC.toString(),LN.toString())
        return db!!.delete(Lesson.Table_Name,Argument,whereclause)
    }
   fun DeleteCourse(CC:Int):Boolean
   {
       var whereclause=Course.Column_Code+" =?"
       var Argument= arrayOf(CC.toString())
       return db!!.delete(Course.Table_Name,Argument,whereclause)

   }
    fun UNSubscribeCourse(CC:Int,SE:String):Boolean
    {
        var whereclause=STUD_COURSE.Column_Course_Code+" =? and "+STUD_COURSE.Column_Stud_ID+" =? "
        var Argument= arrayOf(CC.toString(),SE)
        return db!!.delete(STUD_COURSE.Table_Name,Argument,whereclause)

    }

}