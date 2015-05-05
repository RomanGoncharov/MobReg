package com.romanusynin.mobreg.mobreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkDayAdapter extends ArrayAdapter<WorkDay> {

    WorkDayAdapter(Context context, ArrayList<WorkDay> workDays) {
            super(context, 0, workDays);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkDay workDay = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
        }
        TextView tvDateTime = (TextView) convertView.findViewById(R.id.text1);
        TextView tvFreeTalons = (TextView) convertView.findViewById(R.id.text2);
        tvDateTime.setText(workDay.getDate()+" "+workDay.getWorkTimeInterval());
        tvFreeTalons.setText(workDay.getFreeTalons());
        return convertView;
    }

}
