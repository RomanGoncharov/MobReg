package com.romanusynin.mobreg.mobreg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;

import java.util.ArrayList;

public class WorkDayAdapter extends ArrayAdapter<WorkDay> {

    public WorkDayAdapter(Context context, ArrayList<WorkDay> workDays) {
            super(context, 0, workDays);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WorkDay workDay = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listelement_layout, parent, false);
        }
        TextView tvDateTime = (TextView) convertView.findViewById(R.id.text1);
        TextView tvFreeTalons = (TextView) convertView.findViewById(R.id.text2);
        tvDateTime.setText(workDay.getDate()+" "+workDay.getWorkTimeInterval());
        tvFreeTalons.setText(workDay.getFreeTalons());
        return convertView;
    }

}
