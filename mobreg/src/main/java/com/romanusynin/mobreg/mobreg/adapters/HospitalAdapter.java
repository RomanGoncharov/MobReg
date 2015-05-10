package com.romanusynin.mobreg.mobreg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.objects.Hospital;
import com.romanusynin.mobreg.mobreg.R;

import java.util.ArrayList;

public class HospitalAdapter extends ArrayAdapter<Hospital> {

    public HospitalAdapter(Context context, ArrayList<Hospital> hospitals) {
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
