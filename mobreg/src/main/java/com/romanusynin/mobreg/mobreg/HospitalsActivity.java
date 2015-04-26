package com.romanusynin.mobreg.mobreg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;

public class HospitalsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospitals_layout);
        Intent intent = getIntent();
        String region_url;
        try {
            Region region = (Region) intent.getExtras().getSerializable("region");
            region_url = region.getUrl();
        }
        catch (NullPointerException e){
            region_url = Constants.OMSK_URL;
        }
        MyTask task = new MyTask();
        task.execute(region_url);
    }

    class MyTask extends AsyncTask<String, Void, Void> {

        ArrayList<Hospital> hospitals;

        @Override
        protected Void doInBackground(String... params) {
            hospitals = Parser.getHospitals(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            HospitalAdapter adapter = new HospitalAdapter(HospitalsActivity.this, this.hospitals);
            ListView listView = (ListView) findViewById(R.id.lvHospitals);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> view, View v, int position,long id){

                    Hospital selectedHospital = (Hospital) view.getItemAtPosition(position);
                    Intent intent = new Intent(HospitalsActivity.this, DepartmentsActivity.class);
                    intent.putExtra("hospital", selectedHospital);
                    startActivity(intent);
                }

            });
            listView.setAdapter(adapter);

            listView.setAdapter(adapter);
        }
    }
}
