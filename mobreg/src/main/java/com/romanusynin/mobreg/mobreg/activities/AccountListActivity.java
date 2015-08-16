package com.romanusynin.mobreg.mobreg.activities;

import android.support.v4.app.Fragment;
import com.romanusynin.mobreg.mobreg.fragments.AccountListFragment;


public class AccountListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new AccountListFragment();
    }
}
