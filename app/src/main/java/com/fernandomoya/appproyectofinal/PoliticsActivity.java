package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.fernandomoya.appproyectofinal.list.AdoptionListActivity;

public class PoliticsActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politics);
        webView = findViewById(R.id.txtInformacion);
        webView.setText(Html.fromHtml(getResources().getString(R.string.mas_informacion)));
        webView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentPolitics = new Intent(PoliticsActivity.this, ViewActivity.class);
                startActivity(intentPolitics);
            }

        });

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