package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.*;
import com.romanusynin.mobreg.mobreg.adapters.DoctorAdapter;
import com.romanusynin.mobreg.mobreg.objects.Department;
import com.romanusynin.mobreg.mobreg.objects.Doctor;
import com.romanusynin.mobreg.mobreg.objects.Parser;

import java.util.ArrayList;

public class DoctorsActivity extends Activity{
    private Department department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        department = (Department) intent.getExtras().getSerializable("department");
        GetDoctorsList task = new GetDoctorsList();
        task.execute(department);
    }

    class GetDoctorsList extends AsyncTask<Department, ArrayList<Doctor>, ArrayList<Doctor>> {

        @Override
        protected ArrayList<Doctor> doInBackground(Department... params) {
            return Parser.getDoctors(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Doctor> results) {
            super.onPostExecute(results);
            if (results== null){
                setContentView(R.layout.error_net_layout);
            }
            else {
                setContentView(R.layout.doctors_layout);
                TextView nameDepartment = (TextView)findViewById(R.id.nameDepartment);
                nameDepartment.setText(department.getName());
                DoctorAdapter adapter = new DoctorAdapter(DoctorsActivity.this, results);
                ListView listView = (ListView) findViewById(R.id.lvDepartments);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> view, View v, int position,long id){

                        Doctor selectedDoctor = (Doctor) view.getItemAtPosition(position);
                        Intent intent = new Intent(DoctorsActivity.this, WorkDaysActivity.class);
                        intent.putExtra("doctor", selectedDoctor);
                        startActivity(intent);
                    }

                });
                listView.setAdapter(adapter);
            }
        }
    }
}
