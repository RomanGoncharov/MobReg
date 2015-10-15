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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.adapters.WorkDayAdapter;
import com.romanusynin.mobreg.mobreg.objects.Constants;
import com.romanusynin.mobreg.mobreg.objects.Doctor;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;

import java.util.ArrayList;

public class WorkDaysFragment extends Fragment {
    private ArrayList<WorkDay> workDays;
    private String week;
    private Doctor doctor;
    private int weekNumber;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workDays = (ArrayList<WorkDay>) bundle.getSerializable("workdays");
        week = (String) bundle.getSerializable("week");
        doctor = (Doctor) bundle.getSerializable("doctor");
        weekNumber = bundle.getInt("weeknum");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.work_days_layout, parent, false);
        getActivity().setTitle(R.string.title_work_days_activity);
        TextView nameDoctor = (TextView)v.findViewById(R.id.nameDoctor);
        nameDoctor.setText(doctor.getName());
        TextView specDoctor = (TextView)v.findViewById(R.id.specializationDoctor);
        specDoctor.setText(doctor.getSpecialization());
        WorkDayAdapter adapter = new WorkDayAdapter(getActivity(), workDays);
        ListView listView = (ListView) v.findViewById(R.id.lvWorkDays);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){

                WorkDay selectedWorkDay = (WorkDay) view.getItemAtPosition(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.TICKETS); //tickets
                b.putSerializable("workday", selectedWorkDay);
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        listView.setAdapter(adapter);
        TextView emptyText = (TextView)v.findViewById(R.id.textCenter);
        emptyText.setText("На данной неделе талонов нет. Выберите другую неделю.");
        listView.setEmptyView(emptyText);

        Button prevWeek = (Button) v.findViewById(R.id.prevWeek);
        prevWeek.setText("Пред.");
        if (weekNumber==1){
            prevWeek.setEnabled(false);
        }
        prevWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                weekNumber--;
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.WORKDAYS); //workdays
                b.putSerializable("doctor", doctor);
                b.putBoolean("needremove", true);
                b.putInt("weeknum", weekNumber);
                f.setArguments(b);
                ft.add(R.id.fragmentContainer, f);
                ft.commit();
            }
        });

        Button nextWeek = (Button) v.findViewById(R.id.nextWeek);
        nextWeek.setText("След.");
        if (weekNumber== Constants.MAX_WEEK_NUMBER){
            nextWeek.setEnabled(false);
        }
        nextWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                weekNumber++;
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.WORKDAYS); //workdays
                b.putSerializable("doctor", doctor);
                b.putBoolean("needremove",true);
                b.putInt("weeknum", weekNumber);
                f.setArguments(b);
                ft.add(R.id.fragmentContainer, f);
                ft.commit();
            }
        });

        TextView tVweek = (TextView) v.findViewById(R.id.weekDates);
        tVweek.setText(week);

        return v;
    }
}
