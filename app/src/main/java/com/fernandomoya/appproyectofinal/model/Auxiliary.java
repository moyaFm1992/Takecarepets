package com.fernandomoya.appproyectofinal.model;

import android.util.Log;

public class Auxiliary {


    public void trabajando() {
        try {
            Thread.sleep((long)(Math.random() * (900) + 100));
        } catch (Exception ex) {
            Log.i("Exception", ex.getMessage());
        }

    }

}
