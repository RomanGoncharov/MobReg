package com.romanusynin.mobreg.mobreg.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.objects.Doctor;
import com.romanusynin.mobreg.mobreg.R;

import java.util.ArrayList;

public class DoctorAdapter extends ArrayAdapter <Doctor> {

        public DoctorAdapter(Context context, ArrayList<Doctor> doctors) {super(context, 0, doctors);}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Doctor doctor = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listelement_layout, parent, false);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.text1);
            TextView tvOffice = (TextView) convertView.findViewById(R.id.text2);
            tvName.setText(doctor.getName());
            tvOffice.setText(doctor.getOffice());
            return convertView;
        }
    }

