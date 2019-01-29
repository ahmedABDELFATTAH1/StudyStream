package com.abdelfattah.study.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import org.jetbrains.annotations.Nullable;

import java.sql.Date;

import static com.abdelfattah.study.data.StudyStreamContract.*;

public class Controllerjava {

    private DBHelper db;
    private SQLiteDatabase readdata;
    private SQLiteDatabase writedata;
    public Controllerjava(Context contex)
    {
        db=new DBHelper(contex);



    }
    // region selecting all Announcements,materials,lessons from a particular course
    public Cursor SelectAllAnnouncements(int CourseCode)
    {
        //select * from Announcements where Course_Code = course_code Order by date desc
        String query="Select * from "+Announcements.Table_Name+ " where "+Announcements.Column_Course_Code+" = "
                + CourseCode ;

        Cursor cursor=db.getReadableDatabase().rawQuery(query,null);
        return cursor;

    }


    public Cursor SelectAllMaterials(int CourseCode)
    {
        //select * from Materials where Course_Code = course_code Order by date desc
        String query="Select * from "+Materials.Table_Name+ " where "+Materials.Column_Course_Code+" =?  Order by " +
                Materials.Column_Date + " desc";
        String[] Argument = {Integer.toString(CourseCode)};
        Cursor cursor=db.Select(query,Argument);
        return cursor;

    }

    public Cursor SelectAllLessons(int CourseCode)
    {
        //select * from Lesson where Course_Code = course_code
        String query="Select * from "+Lesson.Table_Name+ " where "+Lesson.Column_Course_Code+" =? ";
        String[] Argument = {Integer.toString(CourseCode)};
        Cursor cursor=db.Select(query,Argument);
        return cursor;

    }
    //endregion

    //region Selecting a specific material , announcement
    public Cursor SelectMaterial(int courseCode , String matTitle)
    {
        //select * from Announcements where Course_Code = course_code AND Material_title=matTitle
        String query="Select * from "+Materials.Table_Name+ " where "+Materials.Column_Course_Code+" = "
                + courseCode + " AND " + Materials.Column_Title + " = "+ matTitle ;

        Cursor cursor=db.getReadableDatabase().rawQuery(query,null);
        return cursor;

    }
    public Cursor SelectAnnouncement(int courseCode , String annTitle )
    {
        //select * from Announcements where Course_Code = course_code AND Ann_Title = annTitle
        String query="Select * from "+Announcements.Table_Name+ " where "+Announcements.Column_Course_Code+" = "
                + courseCode + " AND " + Announcements.Column_Title + " = "+ annTitle ;

        Cursor cursor=db.getReadableDatabase().rawQuery(query,null);
        return cursor;

    }
    //endregion

    //region inserting Lessons,Announcements,Materials

    //Inserting a new announcement
    public void InsertAnnouncement(int course_code, int announcement_number, String title, String Content, java.util.Date date , String doc_id)
    {
        ContentValues contentValues = new ContentValues();

        //mapping values to corresponding columns
        contentValues.put(Announcements.Column_Course_Code,course_code);
        contentValues.put(Announcements.Column_Announcement_Num,announcement_number);
        contentValues.put(Announcements.Column_Title,title);
        contentValues.put(Announcements.Column_Content,Content);
        contentValues.put(Announcements.Column_Date,date.toString());
        contentValues.put(Announcements.Column_Doc_ID,doc_id);

        db.insert(Announcements.Table_Name,contentValues);



    }

    public void InsertMaterial(int course_code, int material_number, String title, String Content, java.util.Date date , String doc_id)
    {


        ContentValues contentValues = new ContentValues();

        //mapping values to corresponding columns
        contentValues.put(Materials.Column_Course_Code,course_code);
        contentValues.put(Materials.Column_Material_Num,material_number);
        contentValues.put(Materials.Column_Title,title);
        contentValues.put(Materials.Column_Content,Content);
        contentValues.put(Materials.Column_Date,date.toString());
        contentValues.put(Materials.Column_Doc_ID,doc_id);

        db.insert(Materials.Table_Name,contentValues);
    }

    public void InsertLesson(int course_code,int lesson_number,String title)
    {
        ContentValues contentValues = new ContentValues();

        //mapping values to corresponding columns
        contentValues.put(Lesson.Column_Course_Code,course_code);
        contentValues.put(Lesson.Column_Lesson_Num,lesson_number);
        contentValues.put(Lesson.Column_Title,title);

        db.insert(Lesson.Table_Name,contentValues);
    }

    //endregion

    //region update materials , announcements

    public void UpdateMaterial(int courseCode,int matNum,String title , String content)
    {
        //update Materials Set Mat_Title=title , Content=content where Course_Code=courseCode AND Material_Num=matNum
        ContentValues contentValues = new ContentValues();
        contentValues.put(Materials.Column_Title,title);
        contentValues.put(Materials.Column_Content,content);

        String whereclause = Materials.Column_Course_Code + " =? AND "
                + Materials.Column_Material_Num + " =?";
        String[] arguments = {String.valueOf(courseCode),String.valueOf(matNum)};
        db.update(Materials.Table_Name,contentValues,arguments,whereclause);
    }
    public void UpdateAnnouncement(int courseCode,int annNum,String title , String content)
    {
        //update Announcement Set Ann_Title=title , Content=content where Course_Code=courseCode AND Ann=annNum
        ContentValues contentValues = new ContentValues();
        contentValues.put(Announcements.Column_Title,title);
        contentValues.put(Announcements.Column_Content,content);

        String whereclause = Announcements.Column_Course_Code + " =? AND "
                + Announcements.Column_Announcement_Num + " =?";
        String[] arguments = {String.valueOf(courseCode),String.valueOf(annNum)};
        db.update(Announcements.Table_Name,contentValues,arguments,whereclause);
    }

    //endregion

    //region delete materials , announcements

    public void DeleteMaterials(int courseCode , int matNum)
    {
        //Delete From Materials where CourseCode = courseCode AND Mat_Num=matNum
        String whereclause = Materials.Column_Course_Code + " =? AND "
                + Materials.Column_Material_Num + " =?";
        String[] arguments = {String.valueOf(courseCode),String.valueOf(matNum)};
        db.deleteWithoutCascade(Materials.Table_Name,arguments,whereclause);
    }
    public void DeleteAnnouncement(int courseCode , int matNum)
    {
        //Delete From Announcements where CourseCode = courseCode AND Ann_Num=annNum
        String whereclause = Announcements.Column_Course_Code + " =? AND "
                + Announcements.Column_Announcement_Num + " =?";
        String[] arguments = {String.valueOf(courseCode),String.valueOf(matNum)};
        db.deleteWithoutCascade(Announcements.Table_Name,arguments,whereclause);
    }

    //endregion

    //region checking Announcements,Materials,Lessons titles
    //checking if there is an announcement with the same name as title
    public boolean CheckAnnouncement(String title , int CourseCode)
    {
        //Select * from Anouncements where Title=title AND Course_Code = CourseCode
        String query = "select * from " + Announcements.Table_Name
                + " where " + Announcements.Column_Title + " = '" + title + "'  And " + Announcements.Column_Course_Code
                + " = " + CourseCode;

        Cursor mCount= db.Select(query,null);
        int  count = mCount.getCount();
        mCount.close();
        if (count >= 1)
            return true;
        else
            return false;

    }
    //checking if there is a material with the same name as title
    public boolean CheckMaterial(String title , int CourseCode)
    {
        //Select * from Materials where Title=title AND Course_Code = CourseCode
        String query = "select * from " + Materials.Table_Name
                + " where " + Materials.Column_Title + " = '" + title + "'  And " + Materials.Column_Course_Code
                + " = " + CourseCode;

        Cursor mCount= db.Select(query,null);
        int  count = mCount.getCount();
        mCount.close();

        if (count >= 1)
            return true;
        else
            return false;

    }
    //checking if there is a lesson with the same name as title
    public boolean CheckLesson(String title , int CourseCode)
    {
        //Select * from Lessons where Title=title AND Course_Code = CourseCode
        String query = "select * from " + Lesson.Table_Name
                + " where " + Lesson.Column_Title + " = '" + title + "'  And " + Lesson.Column_Course_Code
                + " = " + CourseCode;

        Cursor mCount= db.Select(query,null);
        int  count = mCount.getCount();
        mCount.close();
        if (count >= 1)
            return true;
        else
            return false;
    }
    //#endregion


    //region Getting a new Id for Lessons,Announcements,Materials
    public int NewAnnouncementID(int CourseCode)
    {
        // Select Max(Announcement_Number) from Announcements
        String query = "select max( " + Announcements.Column_Announcement_Num
                +" ) from " + Announcements.Table_Name + " Where " + Announcements.Column_Course_Code + " = ?";
        String[] arguments = {String.valueOf(CourseCode)};

        Cursor mCount = db.Select(query, arguments);
        mCount.moveToFirst();

        int maxID = mCount.getInt(0);

        mCount.close();

        return maxID + 1;
    }
    public int NewMaterialID(int CourseCode)
    {
        //select Max(Material_Number) from Materials
        Cursor mCount= db.getReadableDatabase().rawQuery("select MAX("+Materials.Column_Material_Num
                +") from " + Materials.Table_Name + " Where " + Materials.Column_Course_Code + " = " + CourseCode  , null);
        mCount.moveToFirst();
        int maxID= mCount.getInt(0);
        mCount.close();
        return maxID + 1;
    }
    public int NewLessonID(int CourseCode)
    {
        //select Max(Lesson_Number) from Lesson
        Cursor mCount= db.getReadableDatabase().rawQuery("select MAX("+Lesson.Column_Lesson_Num
                +") from " + Lesson.Table_Name + " Where " + Lesson.Column_Course_Code + " = " + CourseCode  , null);
        mCount.moveToFirst();
        int maxID= mCount.getInt(0);
        mCount.close();
        return maxID + 1;
    }

    //endregion

    //region Getting The Number of Students,Lessons,Announcements,Materials,Questions,Answer per course


    //Get number of  Lessons in a specific course
    public int GetNumberOfLessons(int CourseNum)
    {
        //Select Count(*) from Lessons where Course_Code = CourseNum
        String query="Select count(*) from "+Lesson.Table_Name + " where "+ Lesson.Column_Course_Code+" =? ";
        String[] Argument = {String.valueOf(CourseNum)};
        Cursor cursor=db.Select(query,Argument);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of Lessons
        return  count;
    }
    //Get number of  Announcements in a specific course
    public int GetNumberOfAnnouncements(int CourseNum)
    {
        //Select Count(*) from Announcement where Course_Code = CourseNum
        String query="Select count(*) from "+Announcements.Table_Name + " where "+ Announcements.Column_Course_Code + " =? ";
        String[] Argument = {String.valueOf(CourseNum)};
        Cursor cursor=db.Select(query,Argument);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of annoucements
        return  count;
    }

    //Get number of  Materials in a specific course
    public int GetNumberOfMaterials(int CourseNum)
    {
        //Select Count(*) from Materials where Course_Code = CourseNum
        String query="Select count(*) from "+Materials.Table_Name + " where "+ Materials.Column_Course_Code+" =? ";
        String[] Argument = {String.valueOf(CourseNum)};
        Cursor cursor=db.Select(query,Argument);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of Materials
        return  count;
    }

    //Get number of  Questions in a specific course
    public int GetNumberOfQuestions(int CourseNum)
    {
        //Select Count(*) from Questions where Course_Code = CourseNum
        String query="Select count(*) from "+Questions.Table_Name + " where "+ Questions.Column_Course_Code+" =? ";
        String[] Argument = {String.valueOf(CourseNum)};
        Cursor cursor=db.Select(query,Argument);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of Questions
        return  count;
    }


    //Get number of  Students in a specific course
    public int GetNumberOfStudents(int CourseNum)
    {
        //Select Count(*) from Students where Course_Code = CourseNum
        String query="Select count(*) from "+STUD_COURSE.Table_Name + " where "+ STUD_COURSE.Column_Course_Code+" =? ";
        String[] Argument = {String.valueOf(CourseNum)};
        Cursor cursor=db.Select(query,Argument);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of Students
        return  count;
    }
    //Get number of  Students in a specific course
    public int GetNumberOfAnswers(int CourseNum)
    {
        //Select Count(*) from Answers where Course_Code = CourseNum
        String query="Select count(*) from "+Answers.Table_Name + " where "+ Answers.Column_Course_Code+" =? ";
        String[] Argument = {String.valueOf(CourseNum)};
        Cursor cursor=db.Select(query,Argument);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of Answers
        return  count;
    }


    //endregion

    //region Student_Lesson Operations

    //Checking if Student voted for the lesson or not
    //returns true if student has voted and false if student didnt vote
    public boolean CheckStudentVote(int courseCode , int lessonNum,String studentId)
    {
        //select count(*)
        // from LESSON_STUD
        // where Course_Code = courseCode AND Lesson_Num = lessonNum AND Stud_id = studentId
        String query = "Select Count(*) From " + LESSON_STUD.Table_Name
                + " Where " + LESSON_STUD.Column_Course_Code + " = " + courseCode
                +  " AND " + LESSON_STUD.Column_Lesson_Num + " = " + lessonNum
                + " AND " + LESSON_STUD.Column_Stud_ID  + " = '" + studentId + "'" ;
        Cursor cursor = db.Select(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of students who voted with Id = studentId
        if (count>=1)
            return  true;
        else return false;


    }
    // INSERTING a student vote
    public void InsertVote (int courseCode , int lessonNum,String studentId , int vote)
    {
        ContentValues contentValues = new ContentValues();

        //mapping values to corresponding columns
        contentValues.put(LESSON_STUD.Column_Course_Code,courseCode);
        contentValues.put(LESSON_STUD.Column_Lesson_Num,lessonNum);
        contentValues.put(LESSON_STUD.Column_Stud_ID,studentId);
        contentValues.put(LESSON_STUD.Column_Status,vote);

        db.insert(LESSON_STUD.Table_Name,contentValues);
    }

    //selecting all yes votes from a particular lesson
    public Cursor SelectAllVotes(int courseCode , int lessonNum)
    {
        //Select * From LESSON_STUD
        // where Course_Code = courseCode AND Lesson_Num = lessonNum AND Status = 1
        String query = "Select * from " + LESSON_STUD.Table_Name + " Where "
                + LESSON_STUD.Column_Course_Code + " = " + courseCode
                +  " AND " + LESSON_STUD.Column_Lesson_Num + " = " + lessonNum
                +   "AND " + LESSON_STUD.Column_Status + " = 1";
        Cursor cursor = db.Select(query , null);
        return cursor;
    }
    //this fn returns the num of students who voted yes
    public int PositiveFeedBackNum(int courseCode , int lessonNum)
    {
        //select count(*)
        // from LESSON_STUD
        // where Course_Code = courseCode AND Lesson_Num = lessonNum AND Status = 1
        String query = "Select Count(*) From " + LESSON_STUD.Table_Name
                + " Where " + LESSON_STUD.Column_Course_Code + " = " + courseCode
                +  " AND " + LESSON_STUD.Column_Lesson_Num + " = " + lessonNum
                + " AND " + LESSON_STUD.Column_Status  + " = 1";
        Cursor cursor = db.Select(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of students who voted yes
        return  count;


    }
    //this fn returns the num of students who voted no
    public int NegativeFeedBackNum(int courseCode , int lessonNum)
    {
        //select count(*)
        // from LESSON_STUD
        // where Course_Code = courseCode AND Lesson_Num = lessonNum AND Status = 0
        String query = "Select Count(*) From " + LESSON_STUD.Table_Name
                + " Where " + LESSON_STUD.Column_Course_Code + " = " + courseCode
                +  " AND " + LESSON_STUD.Column_Lesson_Num + " = " + lessonNum
                + " AND " + LESSON_STUD.Column_Status  + " = 0";
        Cursor cursor = db.Select(query,null);
        cursor.moveToFirst();
        int count = cursor.getInt(0); //getting count of students who voted with no
        return  count;


    }




    //endregion

    //region Top students operations
    public Cursor GetTopStudents(int CourseCode)
    {
        //Select A.User_ID , SUM(RATING) AS RATING
        //from USER_VOTE_ANSWER AS UA ,ANSWER AS A
        //where A.Course_Code = CourseCode
        //AND   A.Course_Code = UA.Course_Code
        //AND   A.Lesson_Number=UA.Lesson_Number
        //AND   A.Question_Number=UA.Question_Number
        //AND   A.Answer_Number = UA.Answer_Number
        //group by User_ID
        //order by Rating desc
        String query = "Select A." + Answers.Column_User_ID
                + " ,SUM("+USER_VOTE_ANSWERS.Column_Rating+") AS RATING" +" from "+USER_VOTE_ANSWERS.Table_Name
                +" AS UA , " + Answers.Table_Name + " AS A Where A."
                + Answers.Column_Course_Code +" = " +CourseCode
                +" AND UA."+ USER_VOTE_ANSWERS.Column_Course_Code + " = "+CourseCode
                +" AND A." +Answers.Column_Lesson_Num +" = UA."+ USER_VOTE_ANSWERS.Column_Lesson_Num
                +" AND A." +Answers.Column_Question_Num + " = UA."+USER_VOTE_ANSWERS.Column_Question_Num
                +" AND A." +Answers.Column_Answer_Num + " = UA."+USER_VOTE_ANSWERS.Column_Answer_Num
                + " group by A."+Answers.Column_User_ID + " ORDER BY RATING desc";

        return db.Select(query,null);
    }
    //endregion




}
