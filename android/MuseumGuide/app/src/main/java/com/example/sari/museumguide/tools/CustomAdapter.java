package com.example.sari.museumguide.tools;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.sari.museumguide.R;
import com.example.sari.museumguide.activities.BuildingActivity;

import java.util.ArrayList;


public class CustomAdapter extends ArrayAdapter<String> {
    boolean[] checked;
    int[] progresses;
    public CustomAdapter(Context context, ArrayList<String> rooms){
         super(context, R.layout.custom_listview_item, rooms);
         checked = new boolean[rooms.size()];
         progresses = new int[rooms.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.custom_listview_item,parent,false);
        }
        String room = getItem(position);
        TextView textView = (TextView) convertView.findViewById(R.id.room_name);
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.chkBox);
        SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.seekBar);
        textView.setText(room);
        checkBox.setTag(position);
        seekBar.setTag(position);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked on: ", "" + v.getTag() + " " + checked[(int) v.getTag()]);
                checked[(int) v.getTag()] = !checked[(int) v.getTag()];
                Log.d("Changed: ", "" + v.getTag() + " " + checked[(int) v.getTag()]);
                ((BuildingActivity) v.getContext()).updateEstTime();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progresses[(int) seekBar.getTag()] = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return convertView;
    }
    public boolean isChecked(int position) {
        return checked[position];
    }

    public int getProgress(int position){
        return progresses[position]+1;
    }

    public void check(int position){
        checked[position] = !checked[position];
    }
}
