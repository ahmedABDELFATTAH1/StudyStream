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

    //region checking titles
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




}
