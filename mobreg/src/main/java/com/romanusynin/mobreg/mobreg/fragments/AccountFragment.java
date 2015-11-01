package com.romanusynin.mobreg.mobreg.fragments;


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
import com.romanusynin.mobreg.mobreg.TextValidator;
import com.romanusynin.mobreg.mobreg.objects.Account;
import com.romanusynin.mobreg.mobreg.objects.HelperFactory;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountFragment extends Fragment{
    private Account account;
    private EditText titleField;
    private EditText numberPolicyField;
    private EditText dateBirthField;
    private Button saveButton;
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
            account = (Account)getArguments().getSerializable("selectedAccount");
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
        saveButton = (Button) v.findViewById(R.id.saveButton);
        if (!isUpdateAccount) saveButton.setEnabled(false);

        titleField.addTextChangedListener(new TextValidator(titleField) {
            @Override
            public void validate(EditText editText, String text) {
                if (text.length() == 0) {
                    editText.setError(getString(R.string.required_error));
                    saveButton.setEnabled(false);
                } else {
                    editText.setError(null);
                    if (dateBirthField.getError() == null && dateBirthField.getText().length() > 0 &&
                            numberPolicyField.getError() == null && numberPolicyField.getText().length() > 0)
                        saveButton.setEnabled(true);
                }
            }
        });

        numberPolicyField.addTextChangedListener(new TextValidator(numberPolicyField) {
            @Override
            public void validate(EditText editText, String text) {
                int numberPolicyLength = text.length();
                if (numberPolicyLength == 0) {
                    editText.setError(getString(R.string.required_error));
                    saveButton.setEnabled(false);
                } else if (numberPolicyLength < 6) {
                    editText.setError(getString(R.string.less_6_error));
                    saveButton.setEnabled(false);
                } else if (numberPolicyLength > 16) {
                    editText.setError(getString(R.string.more_16_error));
                    saveButton.setEnabled(false);
                } else {
                    editText.setError(null);
                    if (dateBirthField.getError()==null && dateBirthField.getText().length() > 0 &&
                            titleField.getError()==null && titleField.getText().length() > 0)
                        saveButton.setEnabled(true);
                }
            }
        });

        dateBirthField.addTextChangedListener(new TextValidator(dateBirthField) {
            @Override
            public void validate(EditText editText, String text) {
                Pattern p = Pattern.compile("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.(19|20)\\d\\d$");
                    Matcher m = p.matcher(text);
                if (!m.matches()) {
                    editText.setError("Введите дату в формате dd.mm.yyyy");
                    saveButton.setEnabled(false);
                } else {
                    editText.setError(null);
                    if (numberPolicyField.getError() == null && numberPolicyField.getText().length() > 0 &&
                            titleField.getError()==null && titleField.getText().length() > 0)
                        saveButton.setEnabled(true);
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                account.setTitle(titleField.getText().toString());
                account.setNumberPolicy(numberPolicyField.getText().toString());
                account.setDateBirth(dateBirthField.getText().toString());

                try {
                    if (isUpdateAccount) {
                        HelperFactory.getHelper().getAccountDAO().update(account);
                    } else {
                        HelperFactory.getHelper().getAccountDAO().create(account);
                    }
                } catch (SQLException e) {
                    Log.e(TAG, e.toString());
                }
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        return v;
    }
}
