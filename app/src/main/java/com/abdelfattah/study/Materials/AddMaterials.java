package com.abdelfattah.study.Materials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;

public class AddMaterials extends AppCompatActivity {
    Controllerjava controller = new Controllerjava(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_materials);

    }
    public void AddMaterial(View view)
    {
        EditText Title = (EditText) findViewById(R.id.Material_Title_EditText) ;
        String title = Title.getText().toString();
        EditText Content = (EditText) findViewById(R.id.Material_Content_EditText) ;
        String content = Content.getText().toString();
        //checking if all fields are filled or not
        if (CheckFields() == false )
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return ;
        }

        //checking if announcement title already exists or not
        if(controller.CheckMaterial(title,PickedCourse.Code))
        {
            Toast.makeText(this,"Title already exists please pick another one",Toast.LENGTH_SHORT).show();
            return ;
        }
        int MaterialNum = controller.NewMaterialID(PickedCourse.Code);
        controller.InsertMaterial(PickedCourse.Code,MaterialNum,title,content,new java.util.Date(),PickedCourse.Doc_id);
        finish();

    }

    //checking if fields are empty or not
    public boolean CheckFields ()

    {
        //checking if content is empty
        EditText Content = (EditText) findViewById(R.id.Material_Content_EditText);
        String content = Content.getText().toString();
        content.trim();          //removing extra spaces
        if(content.length()==0)
        {
            return false;
        }
        //checking if title is empty
        EditText Title = (EditText) findViewById(R.id.Material_Title_EditText);
        String title = Title.getText().toString();
        title.trim();             //removing extra spaces
        if(title.length()==0)
        {
            return false;
        }
        return true;
    }
}
