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

public class TicketsActivity extends Activity{

    private Department department;
    private Doctor doctor;
    private WorkDay workDay;
    private String prevWorkDayUrl;
    private String nextWorkDayUrl;
    private String currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        department = (Department) intent.getExtras().getSerializable("department");
        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
        workDay = (WorkDay) intent.getExtras().getSerializable("workDay");
        new GetListTicketsTask().execute(workDay.getUrl());
    }

    class GetListTicketsTask extends AsyncTask<String, Void, Parser.TicketsAndWorkdaysUrlsObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setContentView(R.layout.loading_layout);
        }

        @Override
        protected Parser.TicketsAndWorkdaysUrlsObject doInBackground(String... params) {
            return Parser.getTicketsAndWorkdays(params[0]);
        }

        @Override
        protected void onPostExecute(Parser.TicketsAndWorkdaysUrlsObject ticketsAndWorkdaysUrls) {
            super.onPostExecute(ticketsAndWorkdaysUrls);
            if (ticketsAndWorkdaysUrls == null){
                setContentView(R.layout.error_net_layout);
            }
            else {
                ArrayList<Ticket> tickets = ticketsAndWorkdaysUrls.getTickets();
                prevWorkDayUrl = ticketsAndWorkdaysUrls.getPrevWorkDayUrl();
                nextWorkDayUrl = ticketsAndWorkdaysUrls.getNextWorkDayUrl();
                currentDate = ticketsAndWorkdaysUrls.getCurrentDate();
                setContentView(R.layout.tickets_layout);
                TextView nameDoctor = (TextView)findViewById(R.id.nameDoctor);
                nameDoctor.setText(doctor.getName());
                TextView specDoctor = (TextView)findViewById(R.id.specializationDoctor);
                specDoctor.setText(doctor.getSpecialization());
                TicketAdapter adapter = new TicketAdapter(TicketsActivity.this, tickets);
                ListView listView = (ListView) findViewById(R.id.lvTickets);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> view, View v, int position,long id){

                        Ticket selectedTicket = (Ticket) view.getItemAtPosition(position);
                        Intent intent = new Intent(TicketsActivity.this, TakeTicketActivity.class);
                        intent.putExtra("department", department);
                        intent.putExtra("doctor", doctor);
                        intent.putExtra("ticket", selectedTicket);
                        startActivity(intent);
                    }

                });
                listView.setAdapter(adapter);
                TextView emptyText = (TextView)findViewById(R.id.textEmpty);
                emptyText.setText("На данный день талонов нет. Выберите другой день.");
                listView.setEmptyView(emptyText);

                Button prevWorkDay = (Button) findViewById(R.id.prevWorkDay);
                prevWorkDay.setText("Пред.");
                if (prevWorkDayUrl==null){
                    prevWorkDay.setEnabled(false);
                }
                prevWorkDay.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {;
                        TicketsActivity.this.new GetListTicketsTask().execute(prevWorkDayUrl);
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
                        TicketsActivity.this.new GetListTicketsTask().execute(nextWorkDayUrl);
                    }
                });

                TextView tVweek = (TextView) findViewById(R.id.currentDate);
                tVweek.setText(currentDate);
            }
        }
    }
}
