package com.abdelfattah.study.Announcements;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.LESSONS.PickedLesson2;
import com.abdelfattah.study.LESSONS.Pickedlesson;
import com.abdelfattah.study.LoginSignUp.PickedUser;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;

public class AddAnnouncement extends AppCompatActivity {
    Controllerjava controller = new Controllerjava(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_announcement);
    }
    public void AddAnnouncement(View view)
    {
        EditText Title = (EditText) findViewById(R.id.new_Announcement_Title_EditText) ;
        String title = Title.getText().toString();
        EditText Content = (EditText) findViewById(R.id.new_Announcement_Content_EditText) ;
        String content = Content.getText().toString();
        //checking if all fields are filled or not
        if (CheckFields() == false )
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return ;
        }

        //checking if announcement title already exists or not
        if(controller.CheckAnnouncement(title,PickedCourse.Code))
        {
            Toast.makeText(this,"Title already exists please pick another one",Toast.LENGTH_SHORT).show();
            return ;
        }

        int AnnouncementNum = controller.NewAnnouncementID(PickedCourse.Code);
        controller.InsertAnnouncement(PickedCourse.Code,AnnouncementNum,title,content,new java.util.Date(),PickedCourse.Doc_id);
    }




    //checking if fields are empty or not
    public boolean CheckFields ()
    {
        //checking if content is empty
        EditText Content = (EditText) findViewById(R.id.new_Announcement_Content_EditText);
        String content = Content.getText().toString();
        content.trim();          //removing extra spaces
        if(content.length()==0)
        {
            return false;
        }
        //checking if title is empty
        EditText Title = (EditText) findViewById(R.id.new_Announcement_Title_EditText);
        String title = Title.getText().toString();
        title.trim();             //removing extra spaces
        if(title.length()==0)
        {
            return false;
        }
        return true;
    }





}
