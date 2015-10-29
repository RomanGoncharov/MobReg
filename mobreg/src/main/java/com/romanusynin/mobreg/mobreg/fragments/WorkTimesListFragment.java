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

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_workdays_layout, container, false);
        WorkTimeAdapter adapter = new WorkTimeAdapter(getActivity(), workTimes);
        ListView listView = (ListView) v.findViewById(android.R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position, long id) {
                WorkTime selectedWorkTime = (WorkTime) view.getItemAtPosition(position);
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

        });
        listView.setAdapter(adapter);
        TextView emptyText = (TextView)v.findViewById(R.id.textCenter);
        emptyText.setText("На данный день талонов нет. Выберите другой день.");
        listView.setEmptyView(emptyText);

        String prevWorkDayUrl = workDay.getPrevWorkDayUrl();
        String nextWorkDayUrl = workDay.getNextWorkDayUrl();
        String currentDate = workDay.getDate();
        Button prevWorkDay = (Button) getActivity().findViewById(R.id.prevWorkDay);
        prevWorkDay.setEnabled(prevWorkDayUrl!=null);

        Button nextWorkDay = (Button) getActivity().findViewById(R.id.nextWorkDay);
        nextWorkDay.setEnabled(nextWorkDayUrl!=null);

        TextView tVweek = (TextView)  getActivity().findViewById(R.id.currentDate);
        tVweek.setText(currentDate);

        return v;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workTimes = (ArrayList<WorkTime>)bundle.getSerializable("worktimes");
        cookie = bundle.getString("cookie");
        workDay = (WorkDay)bundle.getSerializable("workday");
    }
}