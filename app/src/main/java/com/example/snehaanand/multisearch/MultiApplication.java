package com.example.snehaanand.multisearch;

import com.example.snehaanand.multisearch.utils.Utils;

/**
 * Created by ajr617 on 7/25/16.
 */

public class MultiApplication extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        //sets shared preferences when user first launches app
        Utils.setDefaultSharedPrefs(getApplicationContext());
    }
}
