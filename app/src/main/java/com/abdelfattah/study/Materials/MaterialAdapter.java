package com.abdelfattah.study.Materials;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abdelfattah.study.R;

import java.util.ArrayList;

public class MaterialAdapter extends ArrayAdapter {
    public MaterialAdapter(Context context , ArrayList<MaterialData> materialList)
    {
        super(context,0,materialList);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // MaterialData Item = getItem(position);
        MaterialData Item = (MaterialData) getItem(position);
        View ListItemView = convertView;
        if(ListItemView == null)
        {
            ListItemView = LayoutInflater.from(getContext()).inflate(R.layout.materialticket,parent,false);

        }
        TextView title = (TextView)ListItemView.findViewById(R.id.Material_Ticket_Title);
        title.setText(Item.GetTitle());

        TextView content = (TextView)ListItemView.findViewById(R.id.Material_Ticket_Content);
        content.setText(Item.GetContent());
        return  ListItemView;
    }
}
