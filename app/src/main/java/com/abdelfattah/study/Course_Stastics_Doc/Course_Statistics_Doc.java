package com.abdelfattah.study.Course_Stastics_Doc;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.abdelfattah.study.LoginSignUp.PickedUser;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controller;
import com.abdelfattah.study.data.Controllerjava;
import com.abdelfattah.study.data.StudyStreamContract;

import java.util.ArrayList;

public class Course_Statistics_Doc extends AppCompatActivity {

    Controller controller1 = new Controller(this);
    Controllerjava controller2 = new Controllerjava(this);
    private ArrayList<Course_Statistics_Doc_Data> StatisticsList = new ArrayList<Course_Statistics_Doc_Data>();
    ArrayAdapter adapter =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__statistics__doc);
        FillList();
        adapter=new Course_Statistics_Doc_Adapter(this,StatisticsList);

        ListView list = (ListView)findViewById(R.id.Course_Statistics_Doc_ListView);
        list.setAdapter(adapter);


    }
    private void FillList() {
        Cursor Courses = controller1.MadeCourses(PickedUser.ID);
        Courses.moveToFirst();
        for (int i = 0; i <= Courses.getCount()-1; i++) {
            //title of course in position i of cursor
            String Title = Courses.getString(Courses.getColumnIndex(StudyStreamContract.Course.Column_Title));
            //code of course in position i in cursor
            int CourseCode = Courses.getInt(Courses.getColumnIndex(StudyStreamContract.Course.Column_Code));

            //number of lessons of course with code = CourseCode
            int LessonsNum = controller2.GetNumberOfLessons(CourseCode);
            //number of Announcements of course with code = CourseCode
            int AnnouncementsNum = controller2.GetNumberOfAnnouncements(CourseCode);
            //number of Materials of course with code = CourseCode
            int MaterialsNum = controller2.GetNumberOfMaterials(CourseCode);
            //number of Questions of course with code = CourseCode
            int QuestionsNum = controller2.GetNumberOfQuestions(CourseCode);
            //number of Answers of course with code = CourseCode
            int AnswerNum = controller2.GetNumberOfAnswers(CourseCode);
            //number of Students of course with code = CourseCode
            int StudentsNum = controller2.GetNumberOfStudents(CourseCode);

            //inserting the new statistics Data in the list
            Course_Statistics_Doc_Data Data = new Course_Statistics_Doc_Data(Title, LessonsNum, MaterialsNum,
                    AnnouncementsNum, QuestionsNum,
                    AnswerNum, StudentsNum);
            StatisticsList.add(Data);

            Courses.moveToNext();


        }

        Courses.close();
    }
}
