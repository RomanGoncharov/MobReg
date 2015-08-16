package com.romanusynin.mobreg.mobreg;

import android.app.Application;

import com.romanusynin.mobreg.mobreg.objects.HelperFactory;

public class MobRegApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        HelperFactory.setHelper(getApplicationContext());
    }
    @Override
    public void onTerminate() {
        HelperFactory.releaseHelper();
        super.onTerminate();
    }
}
