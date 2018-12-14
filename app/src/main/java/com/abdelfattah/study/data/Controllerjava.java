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

    //region checking Announcements,Materials,Lessons titles
    //checking if there is an announcement with the same name as title
    public boolean CheckAnnouncement(String title , int CourseCode)
    {
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




}
