package com.romanusynin.mobreg.mobreg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.adapters.HospitalAdapter;
import com.romanusynin.mobreg.mobreg.objects.Hospital;

import java.util.ArrayList;

public class HospitalsFragment extends Fragment {
    private ArrayList<Hospital> hospitals;
    private HospitalAdapter adapter;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        hospitals = (ArrayList<Hospital>) bundle.getSerializable("hospitals");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.hospitals_layout, parent, false);
        getActivity().setTitle(R.string.title_hospitals_activity);
        adapter = new HospitalAdapter(getActivity(), hospitals);
        ListView listView = (ListView) v.findViewById(R.id.lvHospitals);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> view, View v, int position, long id) {
                Hospital selectedHospital = (Hospital) view.getItemAtPosition(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.DEPARTMENTS);
                b.putSerializable("hospital", selectedHospital);
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        TextView emptyText = (TextView)v.findViewById(R.id.textCenter);
        emptyText.setText("По данному запросу медицинских учреждений не найдено.");
        listView.setEmptyView(emptyText);

        EditText editsearch = (EditText) v.findViewById(R.id.search);
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (adapter != null) {
                    adapter.getFilter().filter(s);
                }
            }
        });
        return v;
    }
}
