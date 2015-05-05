package com.romanusynin.mobreg.mobreg;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class DoctorsActivity extends Activity{
    private Department department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        this.department= (Department) intent.getExtras().getSerializable("department");
        String departmentUrl = department.getUrl();
        MyTask task = new MyTask();
        task.execute(departmentUrl);
    }

    class MyTask extends AsyncTask<String, Void, Void> {

        ArrayList<Doctor> doctors;

        @Override
        protected Void doInBackground(String... params) {
            doctors = Parser.getDoctors(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (this.doctors == null){
                setContentView(R.layout.error_net_layout);
            }
            else {
                setContentView(R.layout.doctors_layout);
                TextView nameDepartment = (TextView)findViewById(R.id.nameDepartment);
                nameDepartment.setText(department.getName());
                DoctorAdapter adapter = new DoctorAdapter(DoctorsActivity.this, this.doctors);
                ListView listView = (ListView) findViewById(R.id.lvDepartments);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> view, View v, int position,long id){

                        Doctor selectedDoctor = (Doctor) view.getItemAtPosition(position);
                        Intent intent = new Intent(DoctorsActivity.this, WorkDaysActivity.class);
                        intent.putExtra("department", department);
                        intent.putExtra("doctor", selectedDoctor);
                        startActivity(intent);
                    }

                });
                listView.setAdapter(adapter);
            }
        }
    }
}
