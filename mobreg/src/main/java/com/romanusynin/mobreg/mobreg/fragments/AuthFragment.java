package com.romanusynin.mobreg.mobreg.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.TextValidator;
import com.romanusynin.mobreg.mobreg.activities.SuccessActivity;
import com.romanusynin.mobreg.mobreg.objects.Constants;
import com.romanusynin.mobreg.mobreg.objects.Parser;
import com.romanusynin.mobreg.mobreg.objects.WorkTime;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthFragment extends Fragment{
    private Button continueButton;
    private EditText numberPolicyField;
    private EditText dateBirthField;
    private WorkTime workTime;
    private String cookie;
    private SharedPreferences sPref;
    String n_polisa;
    String birthday;
    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        workTime = (WorkTime) bundle.getSerializable("worktime");
        cookie = bundle.getString("cookie");
        sPref = getActivity().getPreferences(getActivity().MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.take_ticket_layout, parent, false);
        getActivity().setTitle(R.string.title_take_ticket_activity);
        numberPolicyField = (EditText) v.findViewById(R.id.policy_field);
        numberPolicyField.setText(sPref.getString("N_POLISA", ""));
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
                    if (dateBirthField.getError() == null && dateBirthField.getText().length() > 0) {
                        continueButton.setEnabled(true);
                        n_polisa = numberPolicyField.getText().toString();
                        birthday = dateBirthField.getText().toString();
                    }
                }
            }
        });

        dateBirthField = (EditText) v.findViewById(R.id.date_birth_field);
        dateBirthField.setText(sPref.getString("BIRTHDAY", ""));
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
                    if (numberPolicyField.getError() == null && numberPolicyField.getText().length() > 0) {
                        continueButton.setEnabled(true);
                        n_polisa = numberPolicyField.getText().toString();
                        birthday = dateBirthField.getText().toString();
                    }
                }
            }
        });

        continueButton = (Button) v.findViewById(R.id.continueButton);
        //continueButton.setEnabled(false);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueButton.setEnabled(false);
                SendPersonalDataTask task = new SendPersonalDataTask();
                task.execute(workTime.getUrl());
                continueButton.setEnabled(false);

                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("N_POLISA", numberPolicyField.getText().toString());
                editor.putString("BIRTHDAY", dateBirthField.getText().toString());
                editor.apply();
                Intent intent = new Intent(getActivity(), SuccessActivity.class);
                intent.putExtra("currentTime", workTime);
                startActivity(intent);

            }
        });
        return v;
    }
    class SendPersonalDataTask extends AsyncTask<String, Parser.ResponseObject, Parser.ResponseObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Parser.ResponseObject doInBackground(String... params) {
            String url = Constants.DOMAIN + params[0]+"/";
            return Parser.sendAndParseResponse(url, n_polisa, birthday, cookie);
        }


        @Override
        protected void onPostExecute(Parser.ResponseObject responseObject) {
            super.onPostExecute(responseObject);
            if (responseObject.isSuccess()){
                SharedPreferences.Editor editor = sPref.edit();
                editor.putString("N_POLISA", numberPolicyField.getText().toString());
                editor.putString("BIRTHDAY", dateBirthField.getText().toString());
                editor.apply();
                Intent intent = new Intent(getActivity(), SuccessActivity.class);
                intent.putExtra("currentTime", workTime);
                startActivity(intent);

            }
            else {
                String title = "Ошибка";
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(title);
                builder.setMessage(responseObject.getErrorMessage());
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
}
