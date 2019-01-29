package com.abdelfattah.study.CourseMainMenu.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.abdelfattah.study.Announcements.AnnData;
import com.abdelfattah.study.Announcements.AnnouncementAdapter;
import com.abdelfattah.study.Announcements.AnnouncementData;
import com.abdelfattah.study.Announcements.PickedAnnouncement;
import com.abdelfattah.study.Announcements.editAnnouncement;
import com.abdelfattah.study.COURSE.PickedCourse;
import com.abdelfattah.study.Announcements.AddAnnouncement;
import com.abdelfattah.study.LoginSignUp.PickedUser;
import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controllerjava;
import com.abdelfattah.study.data.StudyStreamContract;

import java.util.ArrayList;


public class Announcement extends Fragment {
    private ArrayList<AnnData> Announcementslist = new ArrayList<AnnData>();
    Controllerjava controller = new Controllerjava(getContext());
    ArrayAdapter adapter = null;

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
        controller = new Controllerjava(getContext());
        Announcementslist = new ArrayList<AnnData>();
        GetAllAnnouncements();
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myview= inflater.inflate(R.layout.fragment_announcement,container, false);
        adapter = new AnnouncementAdapter(getContext(),Announcementslist);
        ListView list = (ListView) myview.findViewById(R.id.AnnouncementList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(PickedUser.Doctor == true) {
                    //record the selected announcement
                    PickedAnnouncement.CourseCode = Announcementslist.get(position).GetCourseCode();
                    PickedAnnouncement.AnnNum = Announcementslist.get(position).GetAnnouncementNum();
                    PickedAnnouncement.AnnTitle = Announcementslist.get(position).GetTitle();
                    PickedAnnouncement.AnnContent = Announcementslist.get(position).GetContent();

                    Intent intent = new Intent(getContext(),editAnnouncement.class);
                    startActivity(intent);
                }
            }
        });
        Button b = (Button)myview.findViewById(R.id.AddAnnouncment);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddAnnouncement.class);
                startActivity(intent);
            }
        });
        if(PickedUser.Doctor == false) //student
            //only the doctor can see the add announcement button
            b.setVisibility(View.GONE);

        return  myview;
    }

    @Override
    public void onStart() {
        super.onStart();
        GetAllAnnouncements();
        adapter.notifyDataSetChanged();
    }

    public void GetAllAnnouncements()
    {
        Announcementslist.clear();

        Cursor cursor = controller.SelectAllAnnouncements(PickedCourse.Code);
        cursor.moveToFirst();

        //inserting all announcements received in cursor into the list to view the list
        for(int i=0;i<cursor.getCount();i++)
        {
            int Coursecode = cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Announcements.Column_Course_Code));
            int AnnouncementId = cursor.getInt(cursor.getColumnIndex(StudyStreamContract.Announcements.Column_Announcement_Num));
            String title = cursor.getString(cursor.getColumnIndex(StudyStreamContract.Announcements.Column_Title));
            String content = cursor.getString(cursor.getColumnIndex(StudyStreamContract.Announcements.Column_Content));
            Announcementslist.add(new AnnData(title,content,Coursecode,AnnouncementId));
            cursor.moveToNext();
        }
        cursor.close();




    }



}




