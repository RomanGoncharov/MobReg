package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Ticket;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;
import com.romanusynin.mobreg.mobreg.objects.WorkTime;

import java.util.ArrayList;

public class SuccessActivity extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_layout);
        Intent intent = getIntent();
        WorkTime currentTime = (WorkTime) intent.getExtras().getSerializable("currentTime");
        TextView regionTv = (TextView) findViewById(R.id.regionOfTicket);
        regionTv.setText(currentTime.getRegionName());
        TextView hospitalTv = (TextView) findViewById(R.id.hospitalOfTicket);
        hospitalTv.setText(currentTime.getHospitalName());
        TextView departmentTv = (TextView) findViewById(R.id.departmentOfTicket);
        departmentTv.setText(currentTime.getDepartmentName());
        TextView doctorNameTv = (TextView) findViewById(R.id.doctorNameOfTicket);
        doctorNameTv.setText(currentTime.getDoctorName());
        TextView doctorSpecTv = (TextView) findViewById(R.id.doctorSpecOfTicket);
        doctorSpecTv.setText(currentTime.getDoctorSpec());
        TextView doctorOfficeTv = (TextView) findViewById(R.id.doctorOfficeOfTIcket);
        doctorOfficeTv.setText(currentTime.getDoctorOffice());
        TextView dateTv = (TextView) findViewById(R.id.dateOfTicket);
        dateTv.setText(currentTime.getWorkDate());
        TextView timeTv = (TextView) findViewById(R.id.timeOfTicket);
        timeTv.setText(currentTime.getTime());
        Button mainMenu = (Button) findViewById(R.id.buttonMainMenu);
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, SelectRegionActivity.class);
                startActivity(intent);
            }
        });
        //ArrayList<Ticket> myTickets = (ArrayList<Ticket>) intent.getExtras().getSerializable("tickets");
    }
}
