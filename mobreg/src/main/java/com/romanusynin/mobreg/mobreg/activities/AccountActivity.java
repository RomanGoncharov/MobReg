package com.romanusynin.mobreg.mobreg.activities;

import android.support.v4.app.Fragment;
import com.romanusynin.mobreg.mobreg.fragments.AccountFragment;


public class AccountActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new AccountFragment();
    }
}
