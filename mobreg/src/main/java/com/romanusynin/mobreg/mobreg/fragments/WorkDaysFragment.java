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
    private String week;
    private Doctor doctor;
    private int weekNumber;
    private Button nextWeek;
    private Button prevWeek;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        doctor = (Doctor) bundle.getSerializable("doctor");
        weekNumber = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.work_days_layout, parent, false);
        getActivity().setTitle(R.string.title_work_days_activity);
        TextView nameDoctor = (TextView)v.findViewById(R.id.nameDoctor);
        nameDoctor.setText(doctor.getName());
        TextView specDoctor = (TextView)v.findViewById(R.id.specializationDoctor);
        specDoctor.setText(doctor.getSpecialization());
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.listContainer);
        if (fragment==null){
            fragment = new LoadingFragment();
            Bundle b = new Bundle();
            b.putSerializable("id", LoadingFragment.WORKDAYS); //workdays
            b.putSerializable("doctor", doctor);
            b.putBoolean("needremove", true);
            b.putInt("weeknum", weekNumber);
            fragment.setArguments(b);
            fm.beginTransaction()
                    .add(R.id.listContainer, fragment)
                    .commit();
        }else{
            fragment = new LoadingFragment();
            Bundle b = new Bundle();
            b.putSerializable("id", LoadingFragment.WORKDAYS); //workdays
            b.putSerializable("doctor", doctor);
            b.putBoolean("needremove", true);
            b.putInt("weeknum", weekNumber);
            fragment.setArguments(b);
            fm.beginTransaction()
                    .replace(R.id.listContainer, fragment)
                    .commit();
        }

        prevWeek = (Button) v.findViewById(R.id.prevWeek);
        prevWeek.setText("Пред.");
        if (weekNumber==1){
            prevWeek.setEnabled(false);
        }
        prevWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                weekNumber--;
                nextWeek.setEnabled(true);
                if (weekNumber==1){
                    prevWeek.setEnabled(false);
                }
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.WORKDAYS); //workdays
                b.putSerializable("doctor", doctor);
                //b.putBoolean("needremove", true);
                b.putInt("weeknum", weekNumber);
                f.setArguments(b);
                ft.replace(R.id.listContainer, f);
                ft.commit();
            }
        });

        nextWeek = (Button) v.findViewById(R.id.nextWeek);
        nextWeek.setText("След.");
        if (weekNumber== Constants.MAX_WEEK_NUMBER){
            nextWeek.setEnabled(false);
        }
        nextWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                weekNumber++;
                //Button prevWeek = (Button) arg0.findViewById(R.id.prevWeek);
                prevWeek.setEnabled(true);
                if (weekNumber== Constants.MAX_WEEK_NUMBER){
                    //Button nextWeek = (Button) arg0.findViewById(R.id.nextWeek);
                    nextWeek.setEnabled(false);
                }
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.WORKDAYS); //workdays
                b.putSerializable("doctor", doctor);
                //b.putBoolean("needremove",true);
                b.putInt("weeknum", weekNumber);
                f.setArguments(b);
                ft.replace(R.id.listContainer, f);
                ft.commit();
            }
        });
        return v;
    }
}
