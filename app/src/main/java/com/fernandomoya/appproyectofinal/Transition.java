package com.fernandomoya.appproyectofinal;

import android.app.Application;
import android.os.SystemClock;

public class Transition extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
