package com.romanusynin.mobreg.mobreg.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.romanusynin.mobreg.mobreg.fragments.AccountListFragment;


public class AccountsActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new AccountListFragment();
    }

}
