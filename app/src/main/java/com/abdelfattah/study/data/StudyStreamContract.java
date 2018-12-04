package com.abdelfattah.study.data;

import android.provider.BaseColumns;


public final class StudyStreamContract {

    private StudyStreamContract(){}

    public static class UserEntry implements BaseColumns {
        public static final String Table_Name = "User";
        public static final String Column_ID = "ID";
        public static final String Column_Password = "Password";
        public static final String Column_FirstName = "FirstName";
        public static final String Column_SecondName = "SecondName";

    }

    public static class Doctor implements BaseColumns {
        public static final String Table_Name = "Doctor";
        public static final String Column_ID = "ID";
        public static final String Column_BIO = "BIO";

    }

    public static class Student implements BaseColumns {
        public static final String Table_Name = "Student";
        public static final String Column_ID = "ID";

    }

    public static class Course implements BaseColumns {
        public static final String Table_Name = "Course";
        public static final String Column_Code = "Code";
        public static final String Column_Password = "Password";
        public static final String Column_Title = "Title";
        public static final String Column_Description = "Description";
        public static final String Column_Doc_ID = "Doc_ID";
    }

    public static class Materials implements BaseColumns {
        public static final String Table_Name = "Materials";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Material_Num = "Material_Number";
        public static final String Column_Doc_ID = "Doc_ID";
        public static final String Column_Date = "Date";
        public static final String Column_Content = "Content";
        public static final String Column_Title = "Title";

    }

    public static class Announcements implements BaseColumns {
        public static final String Table_Name = "Announcements";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Announcement_Num = "Announcement_Number";
        public static final String Column_Doc_ID = "Doc_ID";
        public static final String Column_Date = "Date";
        public static final String Column_Content = "Content";
        public static final String Column_Title = "Title";

    }

    public static class Lesson implements BaseColumns {
        public static final String Table_Name = "Lesson";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Lesson_Num = "Lesson_Number";
        public static final String Column_Title = "Title";

    }

    public static class Questions implements BaseColumns {
        public static final String Table_Name = "Questions";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Lesson_Num = "Lesson_Number";
        public static final String Column_Question_Num = "Question_Number";
        public static final String Column_Date = "Date";
        public static final String Column_Content = "Content";
        public static final String Column_Title = "Title";
        public static final String Column_Up_Vote = "Up_Vote";
        public static final String Column_Down_Vote = "Down_Vote";
        public static final String Column_Stud_ID = "Stud_ID";

    }

    public static class Answers implements BaseColumns {
        public static final String Table_Name = "Answers";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Lesson_Num = "Lesson_Number";
        public static final String Column_Question_Num = "Question_Number";
        public static final String Column_Answer_Num = "Answer_Number";
        public static final String Column_Date = "Date";
        public static final String Column_Content = "Content";
        public static final String Column_Title = "Title";
        public static final String Column_Up_Vote = "Up_Vote";
        public static final String Column_Down_Vote = "Down_Vote";
        public static final String Column_User_ID = "User_ID";

    }

    public static class STUD_COURSE implements BaseColumns {
        public static final String Table_Name = "STUD_COURSE";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Stud_ID = "Stud_ID";
    }

        public static class LESSON_STUD implements BaseColumns {
        public static final String Table_Name = "LESSON_STUD";
        public static final String Column_Course_Code = "Course_Code";
        public static final String Column_Stud_ID = "Stud_ID";
        public static final String Column_Lesson_Num = "Lesson_Num";
    }

}

