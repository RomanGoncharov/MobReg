package com.romanusynin.mobreg.mobreg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.objects.Department;
import com.romanusynin.mobreg.mobreg.R;

import java.util.ArrayList;

public class DepartmentAdapter extends ArrayAdapter<Department> {

    public DepartmentAdapter(Context context, ArrayList<Department> departments) {
        super(context, 0, departments);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Department department = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.text1);
        TextView tvCountTikets = (TextView) convertView.findViewById(R.id.text2);
        tvName.setText(department.getName());
        tvCountTikets.setText(department.getCountTickets());
        return convertView;
    }
}