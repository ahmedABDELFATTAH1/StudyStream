package com.abdelfattah.study.TopStudents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abdelfattah.study.Materials.MaterialData;
import com.abdelfattah.study.R;

import java.util.ArrayList;

public class TopStudents_Adapter extends ArrayAdapter {
    public TopStudents_Adapter(Context context , ArrayList<TopStudents_Data> StudentsList)
    {
        super(context,0,StudentsList);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TopStudents_Data Item = (TopStudents_Data) getItem(position);
        View ListItemView = convertView;
        if(ListItemView == null)
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.top_students_ticket,parent,false);

        }
        TextView studentID = (TextView)ListItemView.findViewById(R.id.Top_Students_Name_Ticket);
        studentID.setText(Item.getmStudentID());

        TextView rate = (TextView)ListItemView.findViewById(R.id.Top_Students_Rating_Ticket);
        rate.setText(String.valueOf(Item.getmRate()));

        return  ListItemView;
    }

}
