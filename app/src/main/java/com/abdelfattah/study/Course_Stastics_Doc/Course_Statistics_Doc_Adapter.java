package com.abdelfattah.study.Course_Stastics_Doc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abdelfattah.study.R;

import java.util.ArrayList;

public class Course_Statistics_Doc_Adapter  extends ArrayAdapter<Course_Statistics_Doc_Data> {

    public Course_Statistics_Doc_Adapter(Context context, ArrayList<Course_Statistics_Doc_Data> DataList)
    {
        super(context,0,DataList);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Course_Statistics_Doc_Data Item = getItem(position);
        View ListItemView = convertView;
        if(ListItemView == null)
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.course_statistics_ticket_doc,parent,false);

        }
        //inserting Course Title to the corresponding course
        TextView CourseTitle = (TextView)(ListItemView.findViewById(R.id.Doc_Course_Ticket_Course_Title));
        CourseTitle.setText(String.valueOf(Item.getmCourseTitle()));

        //inserting lessons number to the corresponding course
        TextView LessonNum = (TextView)(ListItemView.findViewById(R.id.Doc_Course_Ticket_Lesson_Num));
        LessonNum.setText(String.valueOf(Item.getmLessonsNum()));

        //inserting announcements number to the corresponding course
        TextView AnnouncementNum = (TextView)(ListItemView.findViewById(R.id.Doc_Course_Ticket_Announcements_Num));
        AnnouncementNum.setText(String.valueOf(Item.getmAnnouncementsNum()));

        //inserting materials number to the corresponding course
        TextView MaterialNum = (TextView)(ListItemView.findViewById(R.id.Doc_Course_Ticket_Materials_Num));
        MaterialNum.setText(String.valueOf(Item.getmMaterialsNum()));

        //inserting questions number to the corresponding course
        TextView QuestionsNum = (TextView)(ListItemView.findViewById(R.id.Doc_Course_Ticket_Question_Num));
        QuestionsNum.setText(String.valueOf(Item.getmQuestionsNum()));

        //inserting answers number to the corresponding course
        TextView AnswersNum = (TextView)(ListItemView.findViewById(R.id.Doc_Course_Ticket_Answer_Num));
        AnswersNum.setText(String.valueOf(Item.getmAnswersNum()));
        return  ListItemView;
    }

}
