package com.romanusynin.mobreg.mobreg.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.romanusynin.mobreg.mobreg.R;
import com.romanusynin.mobreg.mobreg.fragments.LoadingFragment;
import com.romanusynin.mobreg.mobreg.fragments.SelectRegionFragment;

public class ChooseAndGetTicketActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment(){
        Fragment fragment = new SelectRegionFragment();
        //LoadingFragment fragment = new LoadingFragment();
        //Bundle bundle = new Bundle();
        //bundle.putInt("id", 4);
        //fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (getSupportFragmentManager().getBackStackEntryCount() == 0){
                this.finish();
                return false;
            }
            else{
                getSupportFragmentManager().popBackStack();
                removeCurrentFragment();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void removeCurrentFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment currentFrag =  getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (currentFrag!= null)
            transaction.remove(currentFrag);
        transaction.commit();

    }
}
