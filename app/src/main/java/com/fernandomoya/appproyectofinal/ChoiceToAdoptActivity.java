package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fernandomoya.appproyectofinal.list.ToAdoptListActivity;
import com.fernandomoya.appproyectofinal.list.ToAdoptedListActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.fernandomoya.appproyectofinal.model.Constant.ADMIN;
import static com.fernandomoya.appproyectofinal.model.Constant.VETER;

public class ChoiceToAdoptActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton imgBtnFrmAdoption;
    private ImageButton imgBtnFrmListToAdopt;
    private TextView lblFrmAdoption;
    private TextView lblListToAdopt;
    private String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_to_adopt);
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        imgBtnFrmAdoption = findViewById(R.id.imgBtnFrmAdoption);
        imgBtnFrmListToAdopt = findViewById(R.id.imgBtnFrmListToAdopt);
        lblFrmAdoption = findViewById(R.id.lblFrmAdoption);
        lblListToAdopt = findViewById(R.id.lblListToAdopt);


        imgBtnFrmAdoption.setVisibility(View.GONE);
        imgBtnFrmListToAdopt.setVisibility(View.GONE);
        lblFrmAdoption.setVisibility(View.GONE);
        lblListToAdopt.setVisibility(View.GONE);

        if (passengerID.equals(ADMIN)) {
            imgBtnFrmAdoption.setVisibility(View.VISIBLE);
            imgBtnFrmListToAdopt.setVisibility(View.VISIBLE);
            lblFrmAdoption.setVisibility(View.VISIBLE);
            lblListToAdopt.setVisibility(View.VISIBLE);
        } else {
            if (passengerID.equals(VETER)) {
                imgBtnFrmAdoption.setVisibility(View.VISIBLE);
                lblFrmAdoption.setVisibility(View.VISIBLE);
                imgBtnFrmListToAdopt.setVisibility(View.VISIBLE);
                lblListToAdopt.setVisibility(View.VISIBLE);

            }
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.imgBtnFrmAdoption:

                Intent intentAdoption = new Intent(ChoiceToAdoptActivity.this, ToAdoptListActivity.class);
                startActivity(intentAdoption);
                break;

            case R.id.imgBtnFrmListToAdopt:

                Intent intentListToAdopt = new Intent(ChoiceToAdoptActivity.this, ToAdoptedListActivity.class);
                startActivity(intentListToAdopt);
                break;
            case R.id.imgBtnSalir:
                FirebaseAuth fAuth = FirebaseAuth.getInstance();
                fAuth.signOut();
                finish();
                break;
            default:
        }
    }


}