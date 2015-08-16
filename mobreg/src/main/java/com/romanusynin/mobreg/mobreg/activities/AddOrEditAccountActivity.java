package com.romanusynin.mobreg.mobreg.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Account;
import com.romanusynin.mobreg.mobreg.objects.HelperFactory;

import java.sql.SQLException;

public class AddOrEditAccountActivity extends AppCompatActivity{
    Account editedAccount;
    boolean isUpdate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_account_layout);
        Intent intent = getIntent();
        final EditText titleField = (EditText) findViewById(R.id.title_field);
        final EditText numberPolicyField = (EditText) findViewById(R.id.policy_field);
        final EditText dateBirthField = (EditText) findViewById(R.id.date_birth_field);
        try {
            editedAccount = (Account) intent.getExtras().getSerializable("selectedAccount");
            titleField.setText(editedAccount.getTitle());
            numberPolicyField.setText(editedAccount.getNumberPolicy());
            dateBirthField.setText(editedAccount.getDateBirth());
        }
        catch (NullPointerException e){
            editedAccount = new Account();
            isUpdate = false;
        }
        Button continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    addOrEditAccount(titleField.getText().toString(),
                                     numberPolicyField.getText().toString(),
                                     dateBirthField.getText().toString(),
                                     editedAccount,
                                     isUpdate);
                    Intent intent = new Intent(AddOrEditAccountActivity.this, AccountListActivity.class);
                    startActivity(intent);
                }
        });
    }

    public void addOrEditAccount(String title, String numberPolicy, String dateBirth, Account a, boolean isUpdate){
        a.setTitle(title);
        a.setNumberPolicy(numberPolicy);
        a.setDateBirth(dateBirth);
        try {
            if (isUpdate) {
                HelperFactory.getHelper().getAccountDAO().update(a);
            }
            else {
                HelperFactory.getHelper().getAccountDAO().create(a);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
