package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN = 2500;

    ImageView imageView;
    TextView txtNombre;
    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        imageView = findViewById(R.id.imageView);
        txtNombre = findViewById(R.id.txtNombre);
        top = AnimationUtils.loadAnimation(this, R.anim.top);
        bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
        imageView.setAnimation(top);
        txtNombre.setAnimation(bottom);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (!isNetDisponible() && !isOnlineNet()) {
                    Toast.makeText(SplashActivity.this, "!No existe conexion a Intenet!", Toast.LENGTH_LONG).show();
                    Log.e("error", "ok");

                } else {

                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);

                    Log.e("message", "ok");
                }
                finish();
                Log.e("netHabilitada", Boolean.toString(isNetDisponible()));
                Log.e("accInternet", Boolean.toString(isOnlineNet()));

            }
        }, SPLASH_SCREEN);

    }

    public Boolean isOnlineNet() {

        try {
            Process p = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.es");

            int val = p.waitFor();
            boolean reachable = (val == 0);
            return reachable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo actNetInfo = connectivityManager.getActiveNetworkInfo();

        return (actNetInfo != null && actNetInfo.isConnected());
    }


}