package com.abdelfattah.study.CourseMainMenu.Fragments.ADD;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;

public class AddLesson extends AppCompatActivity {
    Controllerjava controller = new Controllerjava(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lesson);
    }
    public void NewLesson(View view)
    {
        EditText Title = (EditText) findViewById(R.id.lesson_Title_EditText) ;
        String title = Title.getText().toString();

        //checking if all fields are filled or not
        if (CheckFields() == false )
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();

            return ;
        }

        //checking if lesson title already exists or not
        if(controller.CheckLesson(title,PickedCourse.Code))
        {
            Toast.makeText(this,"Title already exists please pick another one",Toast.LENGTH_SHORT).show();
            return ;
        }
        int LessonNum = controller.NewLessonID(PickedCourse.Code);
        controller.InsertLesson(PickedCourse.Code,LessonNum,title);
        finish();
    }
    public boolean CheckFields ()
    {
        //checking if content is empty
        EditText Title = (EditText) findViewById(R.id.lesson_Title_EditText);
        String title = Title.getText().toString();
        title.trim();          //removing extra spaces
        if(title.length()==0)
        {
            return false;
        }

        return true;
    }


}
