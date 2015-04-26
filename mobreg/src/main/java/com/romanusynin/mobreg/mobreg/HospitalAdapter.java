package com.romanusynin.mobreg.mobreg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class HospitalAdapter extends ArrayAdapter<Hospital> {

    HospitalAdapter(Context context, ArrayList<Hospital> hospitals) {
        super(context, 0, hospitals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hospital hospital = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.text1);
        TextView tvCountHospital = (TextView) convertView.findViewById(R.id.text2);
        tvName.setText(hospital.getName());
        tvCountHospital.setText(hospital.getAddress());
        return convertView;
    }
}
