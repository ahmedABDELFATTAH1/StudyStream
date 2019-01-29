package com.abdelfattah.study.Announcements;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;

public class editAnnouncement extends AppCompatActivity {

    Controllerjava controller = new Controllerjava(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_announcement);
        FillFields();

    }

    public void UpdateAnnouncement(View view)
    {
        TextView Title = (TextView) findViewById(R.id.Edit_Announcement_Title);
        String title = Title.getText().toString();

        TextView Content = (TextView) findViewById(R.id.Edit_Announcement_Content);
        String content = Content.getText().toString();

        //Checking if all fields are filled
        if (CheckFields() == false )
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return ;
        }


        //checking if announcement title already exists or not
        if( controller.CheckAnnouncement(title,PickedAnnouncement.CourseCode) && !(title.equals(PickedAnnouncement.AnnTitle)))
        {

            Toast.makeText(this, "Title already exists please pick another one", Toast.LENGTH_SHORT).show();
            return;

        }

        controller.UpdateAnnouncement(PickedAnnouncement.CourseCode,PickedAnnouncement.AnnNum,title,content);
        Toast.makeText(this, "Announcement Updated Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void DeleteAnnouncement(View view)
    {
        controller.DeleteAnnouncement(PickedAnnouncement.CourseCode,PickedAnnouncement.AnnNum);
        Toast.makeText(this, "Announcement Deleted Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    //checking if fields are empty or not
    public boolean CheckFields ()
    {
        //checking if content is empty
        TextView Content = (TextView) findViewById(R.id.Edit_Announcement_Content);
        String content = Content.getText().toString();
        content.trim();          //removing extra spaces
        if(content.length()==0)
        {
            return false;
        }
        //checking if title is empty
        TextView Title = (TextView) findViewById(R.id.Edit_Announcement_Title);
        String title = Title.getText().toString();
        title.trim();             //removing extra spaces
        if(title.length()==0)
        {
            return false;
        }
        return true;
    }
    public void FillFields()
    {
        TextView Content = (TextView) findViewById(R.id.Edit_Announcement_Content);
        Content.setText(PickedAnnouncement.AnnContent);
        TextView Title = (TextView) findViewById(R.id.Edit_Announcement_Title);
        Title.setText(PickedAnnouncement.AnnTitle);

    }
}
