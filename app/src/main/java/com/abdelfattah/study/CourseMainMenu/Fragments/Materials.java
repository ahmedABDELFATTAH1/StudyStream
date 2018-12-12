package com.abdelfattah.study.CourseMainMenu.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.abdelfattah.study.CourseMainMenu.Fragments.ADD.AddAnnouncement;
import com.abdelfattah.study.CourseMainMenu.Fragments.ADD.AddMaterials;
import com.abdelfattah.study.R;

public class Materials extends Fragment {
    public  Context context=null;
    public Materials newInstance(Context context) {
        Materials fragment = new Materials();
        this.context=context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View myview= inflater.inflate(R.layout.fragment_materials, container, false);
        Button b = (Button)myview.findViewById(R.id.AddMaterials);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddMaterials.class);
               startActivity(intent);
            }
        });

       return myview;
    }







}
