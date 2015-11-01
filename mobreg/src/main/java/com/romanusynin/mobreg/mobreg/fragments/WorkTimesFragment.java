package com.romanusynin.mobreg.mobreg.fragments;

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
import com.romanusynin.mobreg.mobreg.adapters.WorkTimeAdapter;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;
import com.romanusynin.mobreg.mobreg.objects.WorkTime;

import java.util.ArrayList;

public class WorkTimesFragment extends Fragment {
    private WorkDay workDay;
    private int flag;
    Button prevWorkDay;
    Button nextWorkDay;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workDay = (WorkDay) bundle.getSerializable("workday");
        flag = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tickets_layout, parent, false);
        getActivity().setTitle(R.string.title_tickets_activity);
        createText(v);
        initListLoad();
        createPrevDayBtn(v);
        createNextDayBtn(v);
        return v;
    }

    private void createPrevDayBtn(View v){
        prevWorkDay = (Button) v.findViewById(R.id.prevWorkDay);
        prevWorkDay.setText("Пред.");
        prevWorkDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                flag = 1;
                startLoading(true);
            }
        });
    }

    private void createNextDayBtn(View v){
        nextWorkDay = (Button) v.findViewById(R.id.nextWorkDay);
        nextWorkDay.setText("След.");
        nextWorkDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                flag = 2;
                startLoading(true);
            }
        });
    }

    private void initListLoad(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.listContainer);
        if (fragment==null){
            flag = 0;
            startLoading(false);
        }else {
            flag = 0;
            startLoading(true);
        }
    }

    private void createText(View v){
        String currentDate = workDay.getDate();
        TextView nameDoctor = (TextView)v.findViewById(R.id.nameDoctor);
        nameDoctor.setText(workDay.getDoctorName());
        TextView specDoctor = (TextView)v.findViewById(R.id.specializationDoctor);
        specDoctor.setText(workDay.getDoctorSpec());
        TextView tVweek = (TextView) v.findViewById(R.id.currentDate);
        tVweek.setText(currentDate);
    }

    private void startLoading(boolean replace){
        Bundle b = new Bundle();
        b.putSerializable("id", LoadingFragment.TICKETS);
        b.putSerializable("workday", workDay);
        b.putSerializable("flag", flag);
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
