package com.romanusynin.mobreg.mobreg.fragments;

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
import com.romanusynin.mobreg.mobreg.adapters.WorkDayAdapter;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;

import java.util.ArrayList;

public class WorkDaysListFragment extends Fragment {
    private ArrayList<WorkDay> workDays;
    private String week;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workDays = (ArrayList<WorkDay>) bundle.getSerializable("workdays");
        week = (String) bundle.getSerializable("week");
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_workdays_layout, container, false);
        createWorkDaysList(v);
        setWeekText();
        return v;
    }

    private void createWorkDaysList(View v){
        WorkDayAdapter adapter = new WorkDayAdapter(getActivity(), workDays);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){
                WorkDay selectedWorkDay = (WorkDay) view.getItemAtPosition(position);
                loadSelectedDay(selectedWorkDay);
            }

        });
        listView.setAdapter(adapter);
        TextView emptyText = (TextView)v.findViewById(R.id.textCenter);
        emptyText.setText("На данной неделе талонов нет. Выберите другую неделю.");
        listView.setEmptyView(emptyText);
    }

    private void loadSelectedDay(WorkDay selectedWorkDay){
        Bundle b = new Bundle();
        b.putSerializable("id", LoadingFragment.TICKETS);
        b.putSerializable("workday", selectedWorkDay);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment f = new WorkTimesFragment();
        f.setArguments(b);
        ft.replace(R.id.fragmentContainer, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void setWeekText(){
        TextView tVweek = (TextView) getActivity().findViewById(R.id.weekDates);
        if (tVweek!=null)
            tVweek.setText(week);
    }
}