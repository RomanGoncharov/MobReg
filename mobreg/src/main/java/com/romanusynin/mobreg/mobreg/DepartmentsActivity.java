package com.romanusynin.mobreg.mobreg;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DepartmentsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospitals_layout);
        Intent intent = getIntent();
        Hospital hospital = (Hospital) intent.getExtras().getSerializable("hospital");
        String hospital_url = hospital.getUrl();
        MyTask task = new MyTask();
        task.execute(hospital_url);
    }

    class MyTask extends AsyncTask<String, Void, Void> {

        ArrayList<Department> departments;

        @Override
        protected Void doInBackground(String... params) {
            departments = Parser.getDepartment(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            DepartmentAdapter adapter = new DepartmentAdapter(DepartmentsActivity.this, this.departments);
            ListView listView = (ListView) findViewById(R.id.lvDepartments);
            listView.setAdapter(adapter);
        }
    }
}

