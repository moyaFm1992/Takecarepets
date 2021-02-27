package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PoliticsActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politics);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnContinuar:
                int id = view.getId();
                if (id == R.id.btnContinuar) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                    alertDialogBuilder.setTitle("Alerta");
                    alertDialogBuilder
                            .setMessage("Esta seguro de continuar con el proceso de adopción?")
                            .setCancelable(false)
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intentContinuar = new Intent(PoliticsActivity.this, AdoptionListActivity.class);
                                    startActivity(intentContinuar);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Intent intToMain = new Intent(PoliticsActivity.this, ChoiceActivity.class);
                                    startActivity(intToMain);
                                }
                            }).create().show();
                }

                break;
            case R.id.btnSalirPolitica:
                finish();
                break;
            default:
        }
    }

}