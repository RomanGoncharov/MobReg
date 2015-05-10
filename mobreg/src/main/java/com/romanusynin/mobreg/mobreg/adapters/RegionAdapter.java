package com.romanusynin.mobreg.mobreg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Region;

import java.util.ArrayList;

public class RegionAdapter extends ArrayAdapter<Region> {

    public RegionAdapter(Context context, ArrayList<Region> regions) {
        super(context, 0, regions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Region region = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listview, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.text1);
        TextView tvCountHospital = (TextView) convertView.findViewById(R.id.text2);
        tvName.setText(region.getName());
        tvCountHospital.setText(region.getCountHospital());
        return convertView;
    }
}