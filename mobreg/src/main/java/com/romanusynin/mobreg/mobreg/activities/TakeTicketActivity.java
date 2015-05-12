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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TakeTicketActivity extends Activity{
    private Department department;
    private Doctor doctor;
    private Ticket ticket;
    private Button continueButton;
    private EditText numberPolicyField;
    private EditText dateBirthField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_ticket_layout);
        Intent intent = getIntent();
//        department = (Department) intent.getExtras().getSerializable("department");
//        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
//        ticket = (Ticket) intent.getExtras().getSerializable("ticket");

        numberPolicyField = (EditText) findViewById(R.id.policy_field);
        numberPolicyField.addTextChangedListener(new TextValidator(numberPolicyField) {
            @Override
            public void validate(EditText editText, String text) {
                int numberPolicyLength = text.length();
                if (numberPolicyLength == 0) {
                    editText.setError("Это поле обязательное");
                    continueButton.setEnabled(false);
                } else if (numberPolicyLength < 6) {
                    editText.setError("Длина не должна быть меньше 6 цифр");
                    continueButton.setEnabled(false);
                } else if (numberPolicyLength > 16) {
                    editText.setError("Длина не должна превышать 16 цифр");
                    continueButton.setEnabled(false);
                } else {
                    editText.setError(null);
                    if (dateBirthField.getError()==null && dateBirthField.getText().length() > 0)
                        continueButton.setEnabled(true);
                }
            }
        });

        dateBirthField = (EditText) findViewById(R.id.date_birth_field);
        dateBirthField.addTextChangedListener(new TextValidator(dateBirthField) {
            @Override
            public void validate(EditText editText, String text) {
                Pattern p = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d\\d$");
                Matcher m = p.matcher(text);
                if (!m.matches()) {
                    editText.setError("Введите дату в формате dd.mm.yyyy");
                    continueButton.setEnabled(false);
                } else {
                    editText.setError(null);
                    if (numberPolicyField.getError()==null && numberPolicyField.getText().length() > 0)
                        continueButton.setEnabled(true);
                }
            }
        });

        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setEnabled(false);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


    }
}
