package com.romanusynin.mobreg.mobreg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class WorkDaysActivity extends Activity {

    private Department department;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        department = (Department) intent.getExtras().getSerializable("department");
        String departmentUrl = department.getUrl();
        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
        MyTaskParams paramsTask = new MyTaskParams(departmentUrl, doctor.getId());
        MyTask task = new MyTask();
        task.execute(paramsTask);
    }

    private static class MyTaskParams {
        String url;
        int doctor_id;

        MyTaskParams(String url, int doctor_id) {
            this.doctor_id = doctor_id;
            this.url = url;
        }
    }

    class MyTask extends AsyncTask<MyTaskParams, Void, Void> {

        ArrayList<WorkDay> workDays;

        @Override
        protected Void doInBackground(MyTaskParams... params) {
            workDays = Parser.getWorkDays(params[0].url, params[0].doctor_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (this.workDays == null){
                setContentView(R.layout.error_net_layout);
            }
            else {
                setContentView(R.layout.work_days_layout);
                TextView nameDoctor = (TextView)findViewById(R.id.nameDoctor);
                nameDoctor.setText(doctor.getName());
                TextView specDoctor = (TextView)findViewById(R.id.specializationDoctor);
                specDoctor.setText(doctor.getSpecialization());
                WorkDayAdapter adapter = new WorkDayAdapter(WorkDaysActivity.this, this.workDays);
                ListView listView = (ListView) findViewById(R.id.lvDepartments);
                listView.setAdapter(adapter);
            }
        }
    }



}
