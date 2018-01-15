package com.pinsonn.foodfacts;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Julien on 12/01/2018.
 */
public class FoodFactsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
