package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.TextValidator;
import com.romanusynin.mobreg.mobreg.adapters.TicketAdapter;
import com.romanusynin.mobreg.mobreg.objects.*;
import com.squareup.okhttp.*;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TakeTicketActivity extends Activity{
    private Department department;
    private Doctor doctor;
    private Ticket ticket;
    private Button continueButton;
    private EditText numberPolicyField;
    private EditText dateBirthField;
    private final OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.take_ticket_layout);
        Intent intent = getIntent();
//        department = (Department) intent.getExtras().getSerializable("department");
//        doctor = (Doctor) intent.getExtras().getSerializable("doctor");
        ticket = (Ticket) intent.getExtras().getSerializable("ticket");

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
                continueButton.setEnabled(false);
                SendPersonalDataTask task = new SendPersonalDataTask();
                task.execute(ticket.getUrl());
                continueButton.setEnabled(false);

            }
        });


    }

    class SendPersonalDataTask extends AsyncTask<String, String , String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = Constants.DOMAIN + params[0];
            try{
                RequestBody formBody = new FormEncodingBuilder()
                        .add("s_polisa","")
                        .add("n_polisa", numberPolicyField.getText().toString())
                        .add("birthday", dateBirthField.getText().toString())
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Cache-Control", "no-cache")
                        .addHeader("Host", "www.omskzdrav.ru")
                        .post(formBody)
                        .build();
                Response response = client.newCall(request).execute();
                String responseHTML = response.body().string();
                return Parser.parseResponse(responseHTML);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }


        @Override
        protected void onPostExecute(String error_message) {
            super.onPostExecute(error_message);
            String title = "Ошибка";
            AlertDialog.Builder builder = new AlertDialog.Builder(TakeTicketActivity.this);
            builder.setTitle(title);
            builder.setMessage(error_message);
            builder.setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            continueButton.setEnabled(true);
        }


    }

}
