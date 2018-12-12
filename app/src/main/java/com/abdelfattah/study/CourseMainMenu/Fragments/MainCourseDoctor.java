package com.abdelfattah.study.CourseMainMenu.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.abdelfattah.study.R;

public class MainCourseDoctor extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction1 = fragmentManager.beginTransaction();
                    transaction1.replace(R.id.container1,new Lessonkotlin()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    FragmentTransaction transaction2 = fragmentManager.beginTransaction();
                    transaction2.replace(R.id.container1,new Materials()).commit();
                    return true;
                case R.id.navigation_notifications:
                    FragmentTransaction transaction3 = fragmentManager.beginTransaction();
                    transaction3.replace(R.id.container1,new Announcement()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_course_doctor);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container1,new Lessonkotlin()).commit();
    }

}
