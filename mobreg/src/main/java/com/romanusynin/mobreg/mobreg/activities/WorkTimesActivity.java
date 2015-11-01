package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.*;
import com.romanusynin.mobreg.mobreg.adapters.*;
import com.romanusynin.mobreg.mobreg.objects.*;

import java.util.ArrayList;

public class WorkTimesActivity extends Activity{

    private WorkDay workDay;
    private int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        workDay = (WorkDay) intent.getExtras().getSerializable("workDay");
        flag = 0;
        new GetListTicketsTask().execute(workDay);
    }

    class GetListTicketsTask extends AsyncTask<WorkDay, Void, Parser.WorkTimesListAndCookieObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setContentView(R.layout.loading_layout);
        }

        @Override
        protected Parser.WorkTimesListAndCookieObject doInBackground(WorkDay... params) {
            return Parser.getWorkDateTimes(params[0], flag);
        }

        @Override
        protected void onPostExecute(final Parser.WorkTimesListAndCookieObject workTimesAndWorkdaysUrls) {
            super.onPostExecute(workTimesAndWorkdaysUrls);
            if (workTimesAndWorkdaysUrls == null){
                setContentView(R.layout.error_net_layout);
            }
            else {
                ArrayList<WorkTime> workTimes = workTimesAndWorkdaysUrls.getWorkTimes();
                String prevWorkDayUrl = workDay.getPrevWorkDayUrl();
                String nextWorkDayUrl = workDay.getNextWorkDayUrl();
                String currentDate = workDay.getDate();
                setContentView(R.layout.tickets_layout);
                TextView nameDoctor = (TextView)findViewById(R.id.nameDoctor);
                nameDoctor.setText(workDay.getDoctorName());
                TextView specDoctor = (TextView)findViewById(R.id.specializationDoctor);
                specDoctor.setText(workDay.getDoctorSpec());
                WorkTimeAdapter adapter = new WorkTimeAdapter(WorkTimesActivity.this, workTimes);
               /* ListView listView = (ListView) findViewById(R.id.lvTickets);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> view, View v, int position,long id){

                        WorkTime selectedWorkTime = (WorkTime) view.getItemAtPosition(position);
                        Intent intent = new Intent(WorkTimesActivity.this, AuthActivity.class);
                        intent.putExtra("workTime", selectedWorkTime);
                        intent.putExtra("cookie", workTimesAndWorkdaysUrls.getCookie());
                        startActivity(intent);
                    }

                });
                listView.setAdapter(adapter);
                TextView emptyText = (TextView)findViewById(R.id.textEmpty);
                emptyText.setText("На данный день талонов нет. Выберите другой день.");
                listView.setEmptyView(emptyText);
*/
                Button prevWorkDay = (Button) findViewById(R.id.prevWorkDay);
                prevWorkDay.setText("Пред.");
                if (prevWorkDayUrl==null){
                    prevWorkDay.setEnabled(false);
                }
                prevWorkDay.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {;
                        flag = 1;
                        WorkTimesActivity.this.new GetListTicketsTask().execute(workDay);
                    }
                });

                Button nextWorkDay = (Button) findViewById(R.id.nextWorkDay);
                nextWorkDay.setText("След.");
                if (nextWorkDayUrl == null){
                    nextWorkDay.setEnabled(false);
                }
                nextWorkDay.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        flag = 2;
                        WorkTimesActivity.this.new GetListTicketsTask().execute(workDay);
                    }
                });

                TextView tVweek = (TextView) findViewById(R.id.currentDate);
                tVweek.setText(currentDate);
            }
        }
    }
}
