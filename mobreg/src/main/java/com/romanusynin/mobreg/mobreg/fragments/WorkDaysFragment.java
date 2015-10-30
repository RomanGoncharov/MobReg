package com.romanusynin.mobreg.mobreg.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Constants;
import com.romanusynin.mobreg.mobreg.objects.Doctor;

public class WorkDaysFragment extends Fragment {
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
        createText(v);
        initListLoad();
        createPrevWeekBtn(v);
        createNextWeekBtn(v);
        return v;
    }

    private void createText(View v){
        TextView nameDoctor = (TextView)v.findViewById(R.id.nameDoctor);
        nameDoctor.setText(doctor.getName());
        TextView specDoctor = (TextView)v.findViewById(R.id.specializationDoctor);
        specDoctor.setText(doctor.getSpecialization());
    }

    private void initListLoad(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.listContainer);
        if (fragment==null){
            startLoading(false);
        }else{
            startLoading(true);
        }
    }

    private void createPrevWeekBtn(View v){
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
                startLoading(true);
            }
        });
    }

    private void createNextWeekBtn(View v){
        nextWeek = (Button) v.findViewById(R.id.nextWeek);
        nextWeek.setText("След.");
        if (weekNumber== Constants.MAX_WEEK_NUMBER){
            nextWeek.setEnabled(false);
        }
        nextWeek.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                weekNumber++;
                prevWeek.setEnabled(true);
                if (weekNumber== Constants.MAX_WEEK_NUMBER){
                    nextWeek.setEnabled(false);
                }
                startLoading(true);
            }
        });
    }

    private void startLoading(boolean replace){
        Bundle b = new Bundle();
        b.putSerializable("id", LoadingFragment.WORKDAYS);
        b.putSerializable("doctor", doctor);
        b.putInt("weeknum", weekNumber);
        LoadingFragment fragment = new LoadingFragment();
        fragment.setArguments(b);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (replace)
            ft.replace(R.id.listContainer, fragment);
        else
            ft.add(R.id.listContainer,fragment);
        ft.commit();
    }
}
