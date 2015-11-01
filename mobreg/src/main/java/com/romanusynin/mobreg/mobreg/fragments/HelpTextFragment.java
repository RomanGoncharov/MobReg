package com.romanusynin.mobreg.mobreg.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.romanusynin.mobreg.mobreg.R;

public class HelpTextFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.help_title)
                .setMessage(R.string.alert_of_policy)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
