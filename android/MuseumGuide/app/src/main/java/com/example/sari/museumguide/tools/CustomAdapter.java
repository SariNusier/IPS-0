package com.example.sari.museumguide.tools;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sari.museumguide.R;

import java.util.ArrayList;

/**
 * Created by sari on 17/04/16.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    boolean[] checked;
    public CustomAdapter(Context context, ArrayList<String> rooms){
         super(context, R.layout.custom_listview_item, rooms);
         checked = new boolean[rooms.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.custom_listview_item,parent,false);
        }
        String room = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.room_name);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.customCheckBox);
        SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);
        textView.setText(room);
        checkBox.setTag(position);
        return convertView;
    }
    public boolean isChecked(int position) {
        return checked[position];
    }

    public void setChecked(boolean check, int position){
        checked[position] = check;
    }
}
