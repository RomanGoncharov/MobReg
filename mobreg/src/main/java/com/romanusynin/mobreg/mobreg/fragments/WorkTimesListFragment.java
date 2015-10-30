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


public class WorkTimesListFragment extends Fragment {
    private ArrayList<WorkTime> workTimes;
    private String cookie;
    private WorkDay workDay;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workTimes = (ArrayList<WorkTime>)bundle.getSerializable("worktimes");
        cookie = bundle.getString("cookie");
        workDay = (WorkDay)bundle.getSerializable("workday");
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_workdays_layout, container, false);
        createWorkTimesList(v);
        setPrevBtnEnabled();
        setNextBtnEnabled();
        setDateText();
        return v;
    }

    private void loadSelectedWorktime(WorkTime selectedWorkTime){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        Fragment f = new AuthFragment();
        Bundle b = new Bundle();
        b.putSerializable("worktime", selectedWorkTime);
        b.putSerializable("cookie", cookie);
        f.setArguments(b);
        ft.replace(R.id.fragmentContainer, f);
        ft.addToBackStack(null);
        ft.commit();
    }

    private void createWorkTimesList(View v){
        WorkTimeAdapter adapter = new WorkTimeAdapter(getActivity(), workTimes);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position, long id) {
                WorkTime selectedWorkTime = (WorkTime) view.getItemAtPosition(position);
                loadSelectedWorktime(selectedWorkTime);
            }

        });
        listView.setAdapter(adapter);
        TextView emptyText = (TextView)v.findViewById(R.id.textCenter);
        emptyText.setText("На данный день талонов нет. Выберите другой день.");
        listView.setEmptyView(emptyText);
    }

    private void setPrevBtnEnabled(){
        String prevWorkDayUrl = workDay.getPrevWorkDayUrl();
        Button prevWorkDay = (Button) getActivity().findViewById(R.id.prevWorkDay);
        if (prevWorkDay!=null)
            prevWorkDay.setEnabled(prevWorkDayUrl != null);
    }

    private void setNextBtnEnabled(){
        String nextWorkDayUrl = workDay.getNextWorkDayUrl();
        Button nextWorkDay = (Button) getActivity().findViewById(R.id.nextWorkDay);
        if (nextWorkDay!=null)
            nextWorkDay.setEnabled(nextWorkDayUrl!=null);
    }

    private void setDateText(){
        String currentDate = workDay.getDate();
        TextView tVweek = (TextView)  getActivity().findViewById(R.id.currentDate);
        if (tVweek!=null)
            tVweek.setText(currentDate);
    }
}