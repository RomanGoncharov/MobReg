package com.romanusynin.mobreg.mobreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DepartmentAdapter extends ArrayAdapter<Department> {

    DepartmentAdapter(Context context, ArrayList<Department> hospitals) {
        super(context, 0, hospitals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Department department = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.text1);
        TextView tvCountHospital = (TextView) convertView.findViewById(R.id.text2);
        tvName.setText(department.getName());
        tvCountHospital.setText(department.getCountTickets());
        return convertView;
    }
}