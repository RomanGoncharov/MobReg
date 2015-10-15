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
    private ArrayList<WorkTime> workTimes;
    private WorkDay workDay;
    private int flag;
    private String cookie;

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workTimes = (ArrayList<WorkTime>)bundle.getSerializable("worktimes");
        workDay = (WorkDay) bundle.getSerializable("workday");
        cookie = bundle.getString("cookie");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tickets_layout, parent, false);
        getActivity().setTitle(R.string.title_tickets_activity);
        String prevWorkDayUrl = workDay.getPrevWorkDayUrl();
        String nextWorkDayUrl = workDay.getNextWorkDayUrl();
        String currentDate = workDay.getDate();
        TextView nameDoctor = (TextView)v.findViewById(R.id.nameDoctor);
        nameDoctor.setText(workDay.getDoctorName());
        TextView specDoctor = (TextView)v.findViewById(R.id.specializationDoctor);
        specDoctor.setText(workDay.getDoctorSpec());
        WorkTimeAdapter adapter = new WorkTimeAdapter(getActivity(), workTimes);
        ListView listView = (ListView) v.findViewById(R.id.lvTickets);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> view, View v, int position,long id){
                WorkTime selectedWorkTime = (WorkTime) view.getItemAtPosition(position);
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new AuthFragment();
                Bundle b = new Bundle();
                b.putSerializable("worktime", selectedWorkTime);
                b.putSerializable("cookie",cookie);
                f.setArguments(b);
                ft.replace(R.id.fragmentContainer, f);
                ft.addToBackStack(null);
                ft.commit();
            }

        });
        listView.setAdapter(adapter);
        TextView emptyText = (TextView)v.findViewById(R.id.textEmpty);
        emptyText.setText("На данный день талонов нет. Выберите другой день.");
        listView.setEmptyView(emptyText);

        Button prevWorkDay = (Button) v.findViewById(R.id.prevWorkDay);
        prevWorkDay.setText("Пред.");
        if (prevWorkDayUrl==null){
            prevWorkDay.setEnabled(false);
        }
        prevWorkDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                flag = 1;
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.TICKETS);
                b.putSerializable("workday", workDay);
                b.putSerializable("flag", flag);
                b.putBoolean("needremove", true);
                f.setArguments(b);
                ft.add(R.id.fragmentContainer, f);
                ft.commit();
            }
        });

        Button nextWorkDay = (Button) v.findViewById(R.id.nextWorkDay);
        nextWorkDay.setText("След.");
        if (nextWorkDayUrl == null){
            nextWorkDay.setEnabled(false);
        }
        nextWorkDay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                flag = 2;
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                Fragment f = new LoadingFragment();
                Bundle b = new Bundle();
                b.putSerializable("id", LoadingFragment.TICKETS);
                b.putSerializable("workday", workDay);
                b.putSerializable("flag",flag);
                b.putBoolean("needremove", true);
                f.setArguments(b);
                ft.add(R.id.fragmentContainer, f);
                ft.commit();
            }
        });

        TextView tVweek = (TextView) v.findViewById(R.id.currentDate);
        tVweek.setText(currentDate);


        return v;
    }
}
