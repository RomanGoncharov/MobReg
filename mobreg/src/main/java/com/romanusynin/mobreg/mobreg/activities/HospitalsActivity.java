package com.romanusynin.mobreg.mobreg.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.romanusynin.mobreg.mobreg.*;
import com.romanusynin.mobreg.mobreg.adapters.HospitalAdapter;
import com.romanusynin.mobreg.mobreg.objects.Constants;
import com.romanusynin.mobreg.mobreg.objects.Hospital;
import com.romanusynin.mobreg.mobreg.objects.Parser;
import com.romanusynin.mobreg.mobreg.objects.Region;

import java.util.ArrayList;
import java.util.Locale;

public class HospitalsActivity extends Activity {
    private String region_url;
    private HospitalAdapter adapter;
    private EditText editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        Intent intent = getIntent();
        try {
            Region region = (Region) intent.getExtras().getSerializable("region");
            region_url = region.getUrl();
        }
        catch (NullPointerException e){
            region_url = Constants.OMSK_URL;
        }
        GetHospitalsListTask task = new GetHospitalsListTask();
        task.execute(region_url);
    }

    class GetHospitalsListTask extends AsyncTask<String, ArrayList<Hospital>, ArrayList<Hospital>> {


        @Override
        protected ArrayList<Hospital> doInBackground(String... params) {
            return Parser.getHospitals(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Hospital> hospitals) {
            super.onPostExecute(hospitals);
            if (hospitals == null){
                setContentView(R.layout.error_net_layout);
                Button reloadButton = (Button) findViewById(R.id.reloadButton);
                reloadButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setContentView(R.layout.loading_layout);
                        GetHospitalsListTask task = new GetHospitalsListTask();
                        task.execute(region_url);
                    }
                });
            }
            else {
                setContentView(R.layout.hospitals_layout);
                adapter = new HospitalAdapter(HospitalsActivity.this, hospitals);
                ListView listView = (ListView) findViewById(R.id.lvHospitals);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> view, View v, int position, long id) {

                        Hospital selectedHospital = (Hospital) view.getItemAtPosition(position);
                        Intent intent = new Intent(HospitalsActivity.this, DepartmentsActivity.class);
                        intent.putExtra("hospital", selectedHospital);
                        startActivity(intent);
                    }

                });
                listView.setAdapter(adapter);
                TextView emptyText = (TextView)findViewById(R.id.textCenter);
                emptyText.setText("По данному запросу медицинских учреждений не найдено.");
                listView.setEmptyView(emptyText);

                editsearch = (EditText) findViewById(R.id.search);
                editsearch.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void afterTextChanged(Editable s) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count,
                                                  int after) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before,
                                              int count) {
                        if (adapter != null) {
                            adapter.getFilter().filter(s);
                        }
                    }
                });

            }
        }
    }
}
