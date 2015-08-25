package com.romanusynin.mobreg.mobreg.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.objects.Account;
import com.romanusynin.mobreg.mobreg.objects.HelperFactory;

import java.sql.SQLException;

public class AccountFragment extends Fragment{
    private Account account;
    private EditText titleField;
    private EditText numberPolicyField;
    private EditText dateBirthField;
    private boolean isUpdateAccount;
    private static final String TAG = "AccountFragment";

    private static final String DIALOG_HELP = "help";

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_account_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        HelpTextFragment helpDialog = new HelpTextFragment();
        helpDialog.show(fm, DIALOG_HELP);
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        try {
            account = (Account) getActivity().getIntent().getExtras().getSerializable("selectedAccount");
            getActivity().setTitle(account.getTitle());
            isUpdateAccount = true;
        }
        catch (NullPointerException e){
            account = new Account();
            getActivity().setTitle(R.string.add_account_text);
            isUpdateAccount = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_account, parent, false);
        titleField = (EditText) v.findViewById(R.id.title_field);
        numberPolicyField = (EditText) v.findViewById(R.id.policy_field);
        dateBirthField = (EditText) v.findViewById(R.id.date_birth_field);
        titleField.setText(account.getTitle());
        numberPolicyField.setText(account.getNumberPolicy());
        dateBirthField.setText(account.getDateBirth());
        Button saveButton = (Button) v.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account.setTitle(titleField.getText().toString());
                account.setNumberPolicy(numberPolicyField.getText().toString());
                account.setDateBirth(dateBirthField.getText().toString());

                try {
                    if (isUpdateAccount) {
                        HelperFactory.getHelper().getAccountDAO().update(account);
                    }
                    else {
                        HelperFactory.getHelper().getAccountDAO().create(account);
                    }
                }
                catch (SQLException e){
                    Log.e(TAG, e.toString());
                }
                getActivity().finish();
            }
        });
        return v;
    }
}
