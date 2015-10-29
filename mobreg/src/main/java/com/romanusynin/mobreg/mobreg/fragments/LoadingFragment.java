package com.romanusynin.mobreg.mobreg.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Constants;
import com.romanusynin.mobreg.mobreg.objects.Department;
import com.romanusynin.mobreg.mobreg.objects.Doctor;
import com.romanusynin.mobreg.mobreg.objects.Hospital;
import com.romanusynin.mobreg.mobreg.objects.Parser;
import com.romanusynin.mobreg.mobreg.objects.Region;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;
import com.romanusynin.mobreg.mobreg.objects.WorkTime;

import java.util.ArrayList;


public class LoadingFragment extends Fragment {
    private Bundle b;
    private int weeknum;
    private int flag;
    private LoadingFragment frag;
    private boolean remove;

    public static final int HOSPITALS =0;
    public static final int DOCTORS =1;
    public static final int DEPARTMENTS =2;
    public static final int REGIONS =3;
    public static final int WORKDAYS =4;
    public static final int TICKETS =5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.loading_layout, parent, false);
        getActivity().setTitle(R.string.loading_title);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frag = this;
        remove=false;
        Bundle bundle = getArguments();
        b = new Bundle();
        int id = bundle.getInt("id", 6);
        if (id == HOSPITALS){
            Region region;
            region = (Region) bundle.getSerializable("region");
            if (region == null){
                String nameRegion = "город Омск";
                region = new Region(nameRegion, Constants.OMSK_URL, null);
            }
            GetHospitalsListTask task = new GetHospitalsListTask();
            task.execute(region);
        }else if (id == DOCTORS){
            Department department = (Department) bundle.getSerializable("department");
            b.putSerializable("department", department);
            GetDoctorsList task = new GetDoctorsList();
            task.execute(department);
        }else if (id == DEPARTMENTS){
            Hospital hospital = (Hospital) bundle.getSerializable("hospital");
            b.putSerializable("hospital", hospital);
            GetListDepartment task = new GetListDepartment();
            task.execute(hospital);
        }else if (id == REGIONS){
            GetRegionsListTask task = new GetRegionsListTask();
            task.execute();
        }else if (id == WORKDAYS){
            Doctor doctor = (Doctor) bundle.getSerializable("doctor");
            weeknum = bundle.getInt("weeknum", 1);
            remove = bundle.getBoolean("needremove", false);
            b.putSerializable("doctor", doctor);
            GetListWorkDaysTask task = new GetListWorkDaysTask();
            task.execute(doctor);
        }else if (id == TICKETS){
            WorkDay workDay = (WorkDay) bundle.getSerializable("workday");
            b.putSerializable("workday", workDay);
            flag = bundle.getInt("flag", 0);
            remove = bundle.getBoolean("needremove", false);
            GetListTicketsTask task = new GetListTicketsTask();
            task.execute(workDay);
        }
    }

    class GetHospitalsListTask extends AsyncTask<Region, ArrayList<Hospital>, ArrayList<Hospital>> {

        @Override
        protected ArrayList<Hospital> doInBackground(Region... params) {
            return Parser.getHospitals(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Hospital> hospitals) {
            super.onPostExecute(hospitals);
            if (hospitals == null) {
                Fragment f = new NetErrorFragment();
                Bundle bundle = getArguments();
                f.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, f);
                ft.commit();
            }else{
                Fragment f = new HospitalsFragment();
                b.putSerializable("hospitals", hospitals);
                f.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();
            }
        }
    }

    class GetRegionsListTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Region> regions;

        @Override
        protected Void doInBackground(Void... params) {
            regions = Parser.getRegions();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (regions == null) {
                Fragment f = new NetErrorFragment();
                Bundle bundle = getArguments();
                f.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, f);
                ft.commit();
            }else {
                Fragment f = new RegionsFragment();
                b.putSerializable("regions", regions);
                f.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();
            }
        }
    }

    class GetDoctorsList extends AsyncTask<Department, ArrayList<Doctor>, ArrayList<Doctor>> {

        @Override
        protected ArrayList<Doctor> doInBackground(Department... params) {
            return Parser.getDoctors(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Doctor> results) {
            super.onPostExecute(results);
            if (results == null) {
                Fragment f = new NetErrorFragment();
                Bundle bundle = getArguments();
                f.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, f);
                ft.commit();
            } else {
                Fragment f = new DoctorsFragment();
                b.putSerializable("doctors", results);
                f.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();
            }
        }
    }

    class GetListDepartment extends AsyncTask<Hospital,  ArrayList<Department>,  ArrayList<Department>> {

        @Override
        protected ArrayList<Department> doInBackground(Hospital... params) {
            return Parser.getDepartmentsList(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Department> departments) {
            super.onPostExecute(departments);
            if (departments == null) {
                Fragment f = new NetErrorFragment();
                Bundle bundle = getArguments();
                f.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragmentContainer, f);
                ft.commit();
            } else {
                Fragment f = new DepartmentsFragment();
                b.putSerializable("departments", departments);
                f.setArguments(b);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, f).commit();
            }
        }
    }

    class GetListWorkDaysTask extends AsyncTask<Doctor, Void, Parser.WorkDaysAndWeek> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Parser.WorkDaysAndWeek doInBackground(Doctor... params) {
            return Parser.getWorkDaysAndWeek(params[0], weeknum);//1 -- номер недели по умолчанию
        }

        @Override
        protected void onPostExecute(Parser.WorkDaysAndWeek workDaysAndWeek) {
            super.onPostExecute(workDaysAndWeek);
            if (workDaysAndWeek == null) {
                Fragment f = new NetErrorFragment();
                Bundle bundle = getArguments();
                f.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (remove) {
                    ft.remove(frag);
                }
                ft.replace(R.id.listContainer, f);
                ft.commit();
            } else {
                Fragment f = new WorkDaysListFragment();
                ArrayList<WorkDay> workDays = workDaysAndWeek.getWorkDays();
                b.putSerializable("workdays", workDays);
                b.putSerializable("week", workDaysAndWeek.getWeek());
                b.putInt("weeknum", weeknum);
                f.setArguments(b);
                try {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    if (remove) {
                        ft.remove(frag);
                    }
                    ft.replace(R.id.listContainer, f);
                    ft.commit();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class GetListTicketsTask extends AsyncTask<WorkDay, Void, Parser.WorkTimesListAndCookieObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Parser.WorkTimesListAndCookieObject doInBackground(WorkDay... params) {
            return Parser.getWorkDateTimes(params[0], flag);
        }

        @Override
        protected void onPostExecute(final Parser.WorkTimesListAndCookieObject workTimesAndWorkdaysUrls) {
            super.onPostExecute(workTimesAndWorkdaysUrls);
            if (workTimesAndWorkdaysUrls == null) {
                Fragment f = new NetErrorFragment();
                Bundle bundle = getArguments();
                f.setArguments(bundle);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                if (remove) {
                    ft.remove(frag);
                }
                ft.replace(R.id.listContainer, f);
                ft.commit();
            } else {
                Fragment f = new WorkTimesListFragment();
                ArrayList<WorkTime> workTimes = workTimesAndWorkdaysUrls.getWorkTimes();
                b.putSerializable("worktimes", workTimes);
                b.putSerializable("cookie", workTimesAndWorkdaysUrls.getCookie());
                b.putInt("weeknum", weeknum);
                f.setArguments(b);
                try {
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    if (remove) {
                        ft.remove(frag);
                    }
                    ft.replace(R.id.listContainer, f);
                    ft.commit();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
