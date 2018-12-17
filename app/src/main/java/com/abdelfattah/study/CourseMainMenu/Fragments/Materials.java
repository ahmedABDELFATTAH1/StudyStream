package com.abdelfattah.study.CourseMainMenu.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.LoginSignUp.PickedUser;
import com.abdelfattah.study.Materials.AddMaterials;
import com.abdelfattah.study.Materials.MaterialAdapter;
import com.abdelfattah.study.Materials.MaterialData;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;
import com.abdelfattah.study.data.StudyStreamContract;

import java.util.ArrayList;

public class Materials extends Fragment {
    public  Context context=null;
    private MaterialAdapter adapter = null;
    private ArrayList<MaterialData> MaterialList = null;
    private Controllerjava controller;

    public Materials newInstance(Context context) {
        Materials fragment = new Materials();
        this.context=context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MaterialList = new ArrayList<MaterialData>();
        controller = new Controllerjava(getContext());
        GetAllMaterials();
        if (getArguments() != null) {

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        GetAllMaterials();
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myview= inflater.inflate(R.layout.fragment_materials, container, false);
        adapter = new MaterialAdapter(getContext(),MaterialList);
        ListView list = (ListView) myview.findViewById(R.id.MaterialstList);
        list.setAdapter(adapter);
        Button b = (Button)myview.findViewById(R.id.AddMaterials);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddMaterials.class);
                startActivity(intent);
            }
        });
        if(PickedUser.Doctor == false) //student
            b.setVisibility(View.GONE);

        return myview;
    }

    public void GetAllMaterials()
    {
        MaterialList.clear();
        // Cursor cursor =controller!!.SelectAllLessons(PickedCourse.Code)
        Cursor cursor = controller.SelectAllMaterials(PickedCourse.Code);
        cursor.moveToFirst();

        //   Toast.makeText(this,"Please enter your email and password first",Toast.LENGTH_SHORT).show()
        for(int i=0;i<cursor.getCount();i++)
        {
            int Coursecode = cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Materials.Column_Course_Code));
            int MaterialId = cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Materials.Column_Material_Num));
            String title = cursor.getString(cursor.getColumnIndex(StudyStreamContract.Materials.Column_Title));
            MaterialList.add(new MaterialData(title,Coursecode,MaterialId));
            cursor.moveToNext();
        }
        cursor.close();



    }






}
