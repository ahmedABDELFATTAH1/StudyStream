package com.abdelfattah.study.Materials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;

public class editMaterial extends AppCompatActivity {


    Controllerjava controller = new Controllerjava(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_material);
        FillFields();

    }
    public void UpdateMaterial(View view)
    {
        TextView Title = (TextView) findViewById(R.id.Edit_Material_Title);
        String title = Title.getText().toString();

        TextView Content = (TextView) findViewById(R.id.Edit_Material_Content);
        String content = Content.getText().toString();

        if (CheckFields() == false )
        {
            Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
            return ;
        }


        //checking if announcement title already exists or not
        if( controller.CheckMaterial(title,PickedMaterial.CourseCode) && !(title.equals(PickedMaterial.MatTitle)))
        {

            Toast.makeText(this, "Title already exists please pick another one", Toast.LENGTH_SHORT).show();
            return;

        }

        controller.UpdateMaterial(PickedMaterial.CourseCode,PickedMaterial.MatNum,title,content);
        Toast.makeText(this, "Material Updated Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void DeleteMaterial(View view)
    {
        controller.DeleteMaterials(PickedMaterial.CourseCode,PickedMaterial.MatNum);
        Toast.makeText(this, "Material Deleted Successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }
    public boolean CheckFields ()

    {
        //checking if content is empty
        TextView Content = (TextView) findViewById(R.id.Edit_Material_Content);
        String content = Content.getText().toString();
        content.trim();          //removing extra spaces
        if(content.length()==0)
        {
            return false;
        }
        //checking if title is empty
        TextView Title = (TextView) findViewById(R.id.Edit_Material_Title);
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
        TextView Content = (TextView) findViewById(R.id.Edit_Material_Content);
        Content.setText(PickedMaterial.MatContent);
        TextView Title = (TextView) findViewById(R.id.Edit_Material_Title);
        Title.setText(PickedMaterial.MatTitle);

    }
}
