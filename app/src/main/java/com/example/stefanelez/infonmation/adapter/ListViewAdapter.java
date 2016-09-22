package com.example.stefanelez.infonmation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.stefanelez.infonmation.R;
import com.example.stefanelez.infonmation.model.Item;

import java.util.ArrayList;

import static android.R.attr.id;

public class ListViewAdapter extends ArrayAdapter<Item> {
    public ListViewAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item item1 = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.title_id);
        TextView tvHome = (TextView) convertView.findViewById(R.id.desc_id);
        // Populate the data into the template view using the data object
        tvName.setText(item1.getTitle());
        tvHome.setText(item1.getDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}