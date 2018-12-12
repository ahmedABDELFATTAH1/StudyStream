package com.abdelfattah.study.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.abdelfattah.study.data.StudyStreamContract.*;



public class DBHelper extends SQLiteOpenHelper {

    private static final String Database_Name = "StudyStream.db";
    private static final int Database_Version = 1;

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        this.db = db;
        final String SQL_CREATE_USER_ENTRY = "CREATE TABLE " + StudyStreamContract.UserEntry.Table_Name
                + "( " + UserEntry.Column_ID + " TEXT PRIMARY KEY , "
                + UserEntry.Column_Password + " INTEGER NOT NULL, "
                + UserEntry.Column_FirstName + " TEXT NOT NULL, "
                + UserEntry.Column_SecondName + " TEXT NOT NULL "
                + ")";
        db.execSQL(SQL_CREATE_USER_ENTRY);


        final String SQL_CREATE_DOCTOR = "CREATE TABLE " + StudyStreamContract.Doctor.Table_Name
                + "( " + Doctor.Column_ID + " TEXT PRIMARY KEY, "
                + Doctor.Column_BIO + " TEXT ,"
                + "FOREIGN KEY (" + Doctor.Column_ID + ") REFERENCES " + UserEntry.Table_Name
                + ")";
        db.execSQL(SQL_CREATE_DOCTOR);

        final String SQL_CREATE_STUDENT = "CREATE TABLE " + StudyStreamContract.Student.Table_Name
                + "( " + Student.Column_ID + " TEXT PRIMARY KEY, "
                + " FOREIGN KEY (" + Student.Column_ID + ") REFERENCES " + UserEntry.Table_Name
                + ")";
        db.execSQL(SQL_CREATE_STUDENT);

        final String SQL_CREATE_Course = "CREATE TABLE " + StudyStreamContract.Course.Table_Name
                + "( " + Course.Column_Code + " INTEGER PRIMARY KEY, "
                + Course.Column_Password + " INTEGER NOT NULL ,"
                + Course.Column_Title + " TEXT NOT NULL ,"
                + Course.Column_Description + " TEXT NOT NULL ,"
                + Course.Column_Doc_ID + " TEXT NOT NULL ,"
                + " FOREIGN KEY (" + Course.Column_Doc_ID + ") REFERENCES " + Doctor.Table_Name
                + " ) ";

        db.execSQL(SQL_CREATE_Course);

        final String SQL_CREATE_Materials = "CREATE TABLE " + StudyStreamContract.Materials.Table_Name
                + "( " + Materials.Column_Course_Code + " INTEGER NOT NULL, "
                + Materials.Column_Material_Num + " INTEGER NOT NULL, "
                + Materials.Column_Title + " TEXT NOT NULL ,"
                + Materials.Column_Content + " TEXT NOT NULL ,"
                + Materials.Column_Date + " DATETIME,"
                + Materials.Column_Doc_ID + " TEXT ,"
                + "PRIMARY KEY ("+Materials.Column_Course_Code + "," + Materials.Column_Material_Num + ")"
                + ", FOREIGN KEY (" + Materials.Column_Course_Code + ") REFERENCES " + Course.Table_Name + ","
                + "FOREIGN KEY (" + Materials.Column_Doc_ID + ") REFERENCES " + Doctor.Column_ID
                +  ")";
        db.execSQL(SQL_CREATE_Materials);


        final String SQL_CREATE_Announcements = "CREATE TABLE " + StudyStreamContract.Announcements.Table_Name + "( "
                + Announcements.Column_Course_Code + " INTEGER NOT NULL, "
                + Announcements.Column_Announcement_Num + " INTEGER NOT NULL ,"
                + Announcements.Column_Title + " TEXT NOT NULL ,"
                + Announcements.Column_Content + " TEXT NOT NULL ,"
                + Announcements.Column_Date + " DATETIME ,"
                + Announcements.Column_Doc_ID + " TEXT ,"
                + "PRIMARY KEY (" + Announcements.Column_Course_Code + "," + Announcements.Column_Announcement_Num + ")"
                + ",FOREIGN KEY (" + Announcements.Column_Course_Code + ") REFERENCES " + Course.Table_Name + ","
                + "FOREIGN KEY (" + Announcements.Column_Doc_ID + ") REFERENCES " + Doctor.Column_ID
                +  ")";
        db.execSQL(SQL_CREATE_Announcements);

        final String SQL_CREATE_Lesson = "CREATE TABLE " + Lesson.Table_Name + "( " + Lesson.Column_Course_Code
                + " INTEGER , "
                + Lesson.Column_Lesson_Num + " INTEGER ,"
                + Lesson.Column_Title + " TEXT NOT NULL ,"
                + "PRIMARY KEY (" + Lesson.Column_Course_Code + ", " + Lesson.Column_Lesson_Num + " ), "
                + "FOREIGN KEY (" + Lesson.Column_Course_Code + ") REFERENCES " + Course.Table_Name
                +  ")";
        db.execSQL(SQL_CREATE_Lesson);

        final String SQL_CREATE_Questions = "CREATE TABLE " + Questions.Table_Name + "( "
                + Questions.Column_Course_Code + " INTEGER  NOT NULL , "
                + Questions.Column_Question_Num + " INTEGER NOT NULL ,"
                + Questions.Column_Lesson_Num + " INTEGER NOT NULL ,"
                + Questions.Column_Stud_ID + " TEXT NOT NULL,"
                + Questions.Column_Title + " TEXT NOT NULL ,"
                + Questions.Column_Content + " TEXT NOT NULL ,"
                + Questions.Column_Date + " DATETIME,"
                + "PRIMARY KEY (" + Questions.Column_Course_Code + "," + Questions.Column_Question_Num + "," + Questions.Column_Lesson_Num +  ")"
                + ",FOREIGN KEY (" + Questions.Column_Course_Code + "," + Questions.Column_Lesson_Num +" ) REFERENCES "
                + Lesson.Table_Name + "(" + Lesson.Column_Course_Code + "," + Lesson.Column_Lesson_Num + ")" + ","
                + "FOREIGN KEY (" + Questions.Column_Stud_ID + ") REFERENCES " + Student.Table_Name
                +  ")";
        db.execSQL(SQL_CREATE_Questions);

        final String SQL_CREATE_Answers = "CREATE TABLE " + Answers.Table_Name + "( "
                + Answers.Column_Course_Code + " INTEGER NOT NULL, "
                + Answers.Column_Question_Num + " INTEGER NOT NULL ,"
                + Answers.Column_Answer_Num + " INTEGER NOT NULL ,"
                + Answers.Column_Lesson_Num + " INTEGER NOT NULL ,"
                + Answers.Column_User_ID + " TEXT NOT NULL,"
                + Answers.Column_Title + "  TEXT NOT NULL ,"
                + Answers.Column_Content + " TEXT NOT NULL ,"
                + Answers.Column_Date + " DATETIME,"
                + "PRIMARY KEY (" +Answers.Column_Course_Code +","+ Answers.Column_Question_Num + "," + Answers.Column_Answer_Num + "," + Answers.Column_Lesson_Num +  ")"
                + ",FOREIGN KEY (" + Answers.Column_Course_Code + " , " + Answers.Column_Lesson_Num +" ) REFERENCES "
                + Lesson.Table_Name + "(" + Lesson.Column_Course_Code + "," + Lesson.Column_Lesson_Num + ")" + ","
                + "FOREIGN KEY (" + Answers.Column_User_ID + ") REFERENCES " + UserEntry.Table_Name
                + ",FOREIGN KEY (" + Answers.Column_Question_Num + ") REFERENCES " + Answers.Table_Name
                +  ")";
        db.execSQL(SQL_CREATE_Answers);

        final String SQL_CREATE_STUD_COURSE = "CREATE TABLE " + STUD_COURSE.Table_Name + "( "
                + STUD_COURSE.Column_Course_Code + " INTEGER NOT NULL, "
                + STUD_COURSE.Column_Stud_ID + " TEXT NOT NULL ,"
                + "PRIMARY KEY (" + STUD_COURSE.Column_Course_Code + "," + STUD_COURSE.Column_Stud_ID + ")"
                + ",FOREIGN KEY (" + STUD_COURSE.Column_Course_Code+") REFERENCES "
                + Course.Table_Name
                + ", FOREIGN KEY (" + STUD_COURSE.Column_Stud_ID + ") REFERENCES " + Student.Table_Name
                +  ")";
        db.execSQL(SQL_CREATE_STUD_COURSE);

        final String SQL_CREATE_LESSON_STUD = "CREATE TABLE " + LESSON_STUD.Table_Name
                + "( " + LESSON_STUD.Column_Course_Code + " INTEGER NOT NULL, "
                + LESSON_STUD.Column_Lesson_Num + " INTEGER NOT NULL, "
                + LESSON_STUD.Column_Stud_ID + " TEXT NOT NULL ,"
                + "PRIMARY KEY (" + LESSON_STUD.Column_Course_Code + "," + LESSON_STUD.Column_Stud_ID+ ")"
                + ",FOREIGN KEY (" + LESSON_STUD.Column_Course_Code + "," + LESSON_STUD.Column_Lesson_Num +") REFERENCES "
                + Lesson.Table_Name + "(" + Lesson.Column_Course_Code + "," + Lesson.Column_Lesson_Num + ")"
                + ", FOREIGN KEY (" + LESSON_STUD.Column_Stud_ID +") REFERENCES " + Student.Table_Name
                +  ")";
        db.execSQL(SQL_CREATE_LESSON_STUD);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ UserEntry.Table_Name);
        onCreate(db);
    }

    public Cursor Select(String query,String Argument[])
    {
       SQLiteDatabase db=this.getReadableDatabase();
       Cursor cu= db.rawQuery(query, Argument);
       return cu;

    }
    public Boolean insert(String tablename,ContentValues content)
    {
        SQLiteDatabase db=this.getWritableDatabase();
       Long success= db.insert(tablename,null,content);
       return success!=-1;
    }
    public Boolean update(String tablename,ContentValues contentValues,String Argument[],String whreclause)
    {
        SQLiteDatabase db=this.getWritableDatabase();
       int NumberOfRowAffected= db.update(tablename, contentValues, whreclause, Argument);
       return NumberOfRowAffected!=0;
    }

}

