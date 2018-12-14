package com.abdelfattah.study.Announcements;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abdelfattah.study.R;

import java.util.ArrayList;
import java.util.List;

public class AnnouncementAdapter extends ArrayAdapter<AnnouncementData> {

    public AnnouncementAdapter(Context context, ArrayList<AnnouncementData> announcementList)
    {
        super(context,0,announcementList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AnnouncementData Item = getItem(position);
        View ListItemView = convertView;
        if(ListItemView == null)
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.announcementticket,parent,false);

        }
        TextView title = (TextView)ListItemView.findViewById(R.id.Announcement_Ticket_Title);
        title.setText(Item.GetTitle());
        return  ListItemView;
    }
}
