package com.abdelfattah.study.TopStudents;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controller;
import com.abdelfattah.study.data.Controllerjava;
import com.abdelfattah.study.data.StudyStreamContract;

import java.util.ArrayList;

public class topStudents extends AppCompatActivity {

    Controllerjava controller = new Controllerjava(this);
    Controller controller2 = new Controller(this);
    ArrayAdapter<TopStudents_Data> adapter;
    ArrayList<TopStudents_Data> TopStudentsList = new ArrayList<TopStudents_Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_students);
        FillList();
        adapter= new TopStudents_Adapter(this,TopStudentsList);
        ListView list = (ListView)findViewById(R.id.Top_Students_List);
        list.setAdapter(adapter);

    }
    //filling the list with the interacting students with answers and sorting them according to the rating
    //of their answers
    public void FillList()
    {
        Cursor cursor =controller.GetTopStudents(PickedCourse.Code);
        cursor.moveToFirst();
        for(int i =0;i<cursor.getCount();i++)
        {
            String studentID = cursor.getString(cursor.getColumnIndex(StudyStreamContract.Answers.Column_User_ID));
            int rate = cursor.getInt(cursor.getColumnIndex("RATING"));
            //checking if the user is a student or not
           if(!controller2.checkkind(studentID)) TopStudentsList.add(new TopStudents_Data(studentID,rate));

            cursor.moveToNext();
        }
        cursor.close();
    }
}
