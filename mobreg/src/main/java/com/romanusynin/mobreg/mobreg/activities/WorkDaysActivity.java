package com.romanusynin.mobreg.mobreg.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.*;
import com.romanusynin.mobreg.mobreg.adapters.WorkDayAdapter;
import com.romanusynin.mobreg.mobreg.objects.*;

import java.util.ArrayList;


public class WorkDaysActivity extends Activity {

    private Doctor doctor;
    private int weekNumber=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
        new GetListWorkDaysTask().execute(doctor);
    }

    class GetListWorkDaysTask extends AsyncTask<Doctor, Void, Parser.WorkDaysAndWeek> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setContentView(R.layout.loading_layout);
        }

        @Override
        protected Parser.WorkDaysAndWeek doInBackground(Doctor... params) {
            return Parser.getWorkDaysAndWeek(params[0],weekNumber);
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
                /*ListView listView = (ListView) findViewById(R.id.lvWorkDays);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> view, View v, int position,long id){

                        WorkDay selectedWorkDay = (WorkDay) view.getItemAtPosition(position);
                        Intent intent = new Intent(WorkDaysActivity.this, WorkTimesActivity.class);
                        intent.putExtra("doctor", doctor);
                        intent.putExtra("workDay", selectedWorkDay);
                        startActivity(intent);
                    }

                });
                listView.setAdapter(adapter);
                TextView emptyText = (TextView)findViewById(R.id.textCenter);
                emptyText.setText("На данной неделе талонов нет. Выберите другую неделю.");
                listView.setEmptyView(emptyText);
*/
                Button prevWeek = (Button) findViewById(R.id.prevWeek);
                prevWeek.setText("Пред.");
                if (weekNumber==1){
                    prevWeek.setEnabled(false);
                }
                prevWeek.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        weekNumber--;
                        WorkDaysActivity.this.new GetListWorkDaysTask().execute(doctor);
                    }
                });

                Button nextWeek = (Button) findViewById(R.id.nextWeek);
                nextWeek.setText("След.");
                if (weekNumber== Constants.MAX_WEEK_NUMBER){
                    nextWeek.setEnabled(false);
                }
                nextWeek.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        weekNumber++;
                        WorkDaysActivity.this.new GetListWorkDaysTask().execute(doctor);
                    }
                });

                TextView tVweek = (TextView) findViewById(R.id.weekDates);
                tVweek.setText(week);
            }
        }
    }



}
