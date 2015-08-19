package com.romanusynin.mobreg.mobreg.activities;

import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.fragments.AccountListFragment;


public class AccountListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new AccountListFragment();
    }
}
