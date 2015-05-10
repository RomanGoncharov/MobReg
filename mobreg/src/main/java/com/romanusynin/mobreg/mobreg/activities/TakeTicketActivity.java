package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.*;

public class TakeTicketActivity extends Activity{
    private Department department;
    private Doctor doctor;
    private Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_ticket_layout);
        Intent intent = getIntent();
        department = (Department) intent.getExtras().getSerializable("department");
        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
        ticket = (Ticket) intent.getExtras().getSerializable("ticket");
    }
}
