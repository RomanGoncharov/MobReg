package com.romanusynin.mobreg.mobreg.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.activities.WorkDaysActivity;
import com.romanusynin.mobreg.mobreg.adapters.DoctorAdapter;
import com.romanusynin.mobreg.mobreg.objects.Department;
import com.romanusynin.mobreg.mobreg.objects.Doctor;

import java.util.ArrayList;

public class DoctorsFragment extends Fragment{
    private ArrayList<Doctor> doctors;
    private Department department;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        doctors = (ArrayList<Doctor>) bundle.getSerializable("doctors");
        department = (Department) bundle.getSerializable("department");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.doctors_layout, parent, false);
        getActivity().setTitle(R.string.title_doctors_activity);
        TextView nameDepartment = (TextView)v.findViewById(R.id.nameDepartment);
        nameDepartment.setText(department.getName());
        DoctorAdapter adapter = new DoctorAdapter(getActivity(), doctors);
        ListView listView = (ListView) v.findViewById(R.id.lvDepartments);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){
                Doctor selectedDoctor = (Doctor) view.getItemAtPosition(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.WORKDAYS);
                b.putSerializable("doctor", selectedDoctor);
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        listView.setAdapter(adapter);
        return v;
    }
}
