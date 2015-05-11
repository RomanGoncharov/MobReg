package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.TextValidator;
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
//        department = (Department) intent.getExtras().getSerializable("department");
//        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
//        ticket = (Ticket) intent.getExtras().getSerializable("ticket");
        EditText numberPolicyField = (EditText) findViewById(R.id.policy_field);
        numberPolicyField.addTextChangedListener(new TextValidator(numberPolicyField) {
            @Override
            public void validate(EditText textView, String text) {
                int numberPolicyLength = text.length();
                if (numberPolicyLength == 0) {
                    textView.setError("Это поле обязательное");
                } else if (numberPolicyLength < 6) {
                    textView.setError("Длина не должна быть меньше 6 цифр");
                } else if (numberPolicyLength > 16) {
                    textView.setError("Длина не должна превышать 16 цифр");
                } else textView.setError(null);
            }
        });

        EditText dateBirthField = (EditText) findViewById(R.id.date_birth_field);
        dateBirthField.addTextChangedListener(new TextValidator(numberPolicyField) {
            @Override
            public void validate(EditText textView, String text) {
                // add validate for dateField
            }
        });
        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
