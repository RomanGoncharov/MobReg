package com.romanusynin.mobreg.mobreg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        MyTask task = new MyTask();
        task.execute();



    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        ArrayList<Region> regions;

        @Override
        protected Void doInBackground(Void... params) {
            regions = Parser.getRegions();


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            RegionAdapter adapter = new RegionAdapter(MainActivity.this, this.regions);
            ListView listView = (ListView) findViewById(R.id.lvMain);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> view, View v, int position,long id){

                    Region selectedRegion = (Region) view.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, HospitalsActivity.class);
                    intent.putExtra("region", selectedRegion);
                    startActivity(intent);
                }

            });
            listView.setAdapter(adapter);
        }
    }
}
