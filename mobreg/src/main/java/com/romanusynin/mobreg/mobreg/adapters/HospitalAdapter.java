package com.romanusynin.mobreg.mobreg.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Filter;
import com.romanusynin.mobreg.mobreg.objects.Hospital;
import com.romanusynin.mobreg.mobreg.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HospitalAdapter extends ArrayAdapter<Hospital> {
    ArrayList<Hospital> hospitals;
    private Context context;
    private Filter filter;

    public HospitalAdapter(Context context, ArrayList<Hospital> hospitals) {
        super(context, 0, hospitals);
        this.context = context;
        this.hospitals = hospitals;
    }

    @Override
    public int getCount() {
        return hospitals.size();
    }

    @Override
    public Hospital getItem(int position) {
        return hospitals.get(position);
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new HospitalFilter<Hospital>(hospitals);
        return filter;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hospital hospital = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_listelement_layout, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.text1);
        TextView tvCountHospital = (TextView) convertView.findViewById(R.id.text2);
        tvName.setText(hospital.getName());
        tvCountHospital.setText(hospital.getAddress());
        return convertView;
    }

    private class HospitalFilter<T> extends Filter {

        private ArrayList<Hospital> sourceObjects;

        public HospitalFilter(ArrayList<Hospital> hospitals) {
            sourceObjects = new ArrayList<Hospital>();
            synchronized (this) {
                sourceObjects.addAll(hospitals);
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq.length() > 0) {
                ArrayList<Hospital> filter = new ArrayList<Hospital>();

                for (Hospital hospital : sourceObjects) {
                    if (hospital.getName().toLowerCase().contains(filterSeq))
                        filter.add(hospital);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((Hospital) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }
}
