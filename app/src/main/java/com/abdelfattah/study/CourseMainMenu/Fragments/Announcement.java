package com.abdelfattah.study.CourseMainMenu.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.CourseMainMenu.Fragments.ADD.AddAnnouncement;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;
import com.abdelfattah.study.data.StudyStreamContract;

import java.util.ArrayList;


public class Announcement extends Fragment {
    private ArrayList<String> Announcementslist = new ArrayList<String>();
    Controllerjava controller = new Controllerjava(getContext());

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Announcement() {
        // Required empty public constructor
    }

    public static Announcement newInstance(String param1, String param2) {
        Announcement fragment = new Announcement();

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
        // Inflate the layout for this fragment
       View myview= inflater.inflate(R.layout.fragment_announcement,container, false);
        Button b = (Button)myview.findViewById(R.id.AddAnnouncment);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddAnnouncement.class);
                startActivity(intent);
            }
        });
        return  myview;
    }


    }




