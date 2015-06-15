package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Constants;
import com.romanusynin.mobreg.mobreg.objects.Ticket;
import com.romanusynin.mobreg.mobreg.objects.WorkDay;
import com.romanusynin.mobreg.mobreg.objects.WorkTime;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        String name_file = currentTime.getDoctorSpec()+"_"+ currentTime.getTime();
        String data = "\n" + currentTime.getRegionName() + "\n" + currentTime.getHospitalName() + "\n" +
                currentTime.getDepartmentName() + "\n" + currentTime.getDoctorName() + "\n" +
                currentTime.getDoctorSpec() + "\n" + currentTime.getDoctorOffice() + "\n" +
                currentTime.getWorkDate() + "\n" + currentTime.getTime();
        try {
            saveToFile(data, name_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, SelectRegionActivity.class);
                startActivity(intent);
            }
        });

        //ArrayList<Ticket> myTickets = (ArrayList<Ticket>) intent.getExtras().getSerializable("tickets");
    }

    public void saveToFile(String data, String name) throws Exception {
        String formatName = name.replaceAll("[|?*<\":>+\\[\\]/']", "_") + ".txt";
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/MobReg");
        myDir.mkdirs();
        File file = new File (myDir, formatName);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
