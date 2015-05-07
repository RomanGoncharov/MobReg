package com.romanusynin.mobreg.mobreg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class WorkDaysActivity extends Activity {

    private Department department;
    private Doctor doctor;
    private int weekNumber=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        department = (Department) intent.getExtras().getSerializable("department");
        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
        MyTaskParams paramsTask = new MyTaskParams(department.getUrl(), doctor, weekNumber);
        new GetListWorkDaysTask().execute(paramsTask);
    }

    private static class MyTaskParams {
        String url;
        Doctor doctor;
        int weekNumber;

        MyTaskParams(String url, Doctor doctor, int weekNumber) {
            this.doctor = doctor;
            this.url = url;
            this.weekNumber = weekNumber;
        }
    }

    class GetListWorkDaysTask extends AsyncTask<MyTaskParams, Void, Parser.WorkDaysAndWeek> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setContentView(R.layout.loading_layout);
        }

        @Override
        protected Parser.WorkDaysAndWeek doInBackground(MyTaskParams... params) {
            return Parser.getWorkDaysAndWeek(params[0].url, params[0].doctor, params[0].weekNumber);
        }

        @Override
        protected void onPostExecute(Parser.WorkDaysAndWeek workDaysAndWeek) {
            super.onPostExecute(workDaysAndWeek);
            if (workDaysAndWeek == null){
                setContentView(R.layout.error_net_layout);
            }
            else {
                ArrayList<WorkDay> workDays = workDaysAndWeek.getWorkDays();
                String week = workDaysAndWeek.getWeek();
                setContentView(R.layout.work_days_layout);
                TextView nameDoctor = (TextView)findViewById(R.id.nameDoctor);
                nameDoctor.setText(doctor.getName());
                TextView specDoctor = (TextView)findViewById(R.id.specializationDoctor);
                specDoctor.setText(doctor.getSpecialization());
                WorkDayAdapter adapter = new WorkDayAdapter(WorkDaysActivity.this, workDays);
                ListView listView = (ListView) findViewById(R.id.lvDepartments);
                listView.setAdapter(adapter);
                TextView emptyText = (TextView)findViewById(R.id.textCenter);
                emptyText.setText("На данной неделе талонов нет. Выберите другую неделю.");
                listView.setEmptyView(emptyText);

                Button prevWeek = (Button) findViewById(R.id.prevWeek);
                prevWeek.setText("Пред.");
                if (weekNumber==1){
                    prevWeek.setEnabled(false);
                }
                prevWeek.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        weekNumber--;
                        MyTaskParams paramsTask = new MyTaskParams(department.getUrl(), doctor, weekNumber);
                        WorkDaysActivity.this.new GetListWorkDaysTask().execute(paramsTask);
                    }
                });

                Button nextWeek = (Button) findViewById(R.id.nextWeek);
                nextWeek.setText("След.");
                if (weekNumber==Constants.MAX_WEEK_NUMBER){
                    nextWeek.setEnabled(false);
                }
                nextWeek.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        weekNumber++;
                        MyTaskParams paramsTask = new MyTaskParams(department.getUrl(), doctor, weekNumber);
                        WorkDaysActivity.this.new GetListWorkDaysTask().execute(paramsTask);
                    }
                });

                TextView tVweek = (TextView) findViewById(R.id.weekDates);
                tVweek.setText(week);
            }
        }
    }



}
