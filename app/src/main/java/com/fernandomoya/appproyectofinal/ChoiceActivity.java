package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static com.fernandomoya.appproyectofinal.model.Constant.ADMIN;
import static com.fernandomoya.appproyectofinal.model.Constant.VETER;


public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {
    String passengerID;
    ImageButton imgBtnFrmLocation;
    ImageButton imgBtnFrmChoiceEvaluation;
    ImageButton imgBtnFrmAdoption;
    ImageButton imgBtnListAdoption;
    ImageButton imgBtnListEvaluation;
    TextView lblLocation;
    TextView lblEvaluation;
    TextView lblFrmAdoption;
    TextView lblAdoption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        imgBtnFrmLocation = findViewById(R.id.imgBtnFrmLocation);
        imgBtnFrmChoiceEvaluation = findViewById(R.id.imgBtnFrmEvaluation);
        imgBtnFrmAdoption = findViewById(R.id.imgBtnFrmAdoption);
        imgBtnListAdoption = findViewById(R.id.imgBtnListAdoption);
        imgBtnListEvaluation = findViewById(R.id.imgBtnFrmListEvaluation);
        lblLocation = findViewById(R.id.lblLocation);
        lblEvaluation = findViewById(R.id.lblEvaluation);
        lblFrmAdoption = findViewById(R.id.lblFrmAdoption);
        lblAdoption = findViewById(R.id.lblAdoption);

        imgBtnFrmLocation.setVisibility(View.GONE);
        imgBtnFrmChoiceEvaluation.setVisibility(View.GONE);
        imgBtnFrmAdoption.setVisibility(View.GONE);
        imgBtnListAdoption.setVisibility(View.GONE);
        lblLocation.setVisibility(View.GONE);
        lblEvaluation.setVisibility(View.GONE);
        lblFrmAdoption.setVisibility(View.GONE);
        lblAdoption.setVisibility(View.GONE);

        if (passengerID.equals(ADMIN)) {
            imgBtnFrmLocation.setVisibility(View.VISIBLE);
            imgBtnFrmChoiceEvaluation.setVisibility(View.VISIBLE);
            imgBtnFrmAdoption.setVisibility(View.VISIBLE);
            imgBtnListAdoption.setVisibility(View.VISIBLE);
            lblLocation.setVisibility(View.VISIBLE);
            lblEvaluation.setVisibility(View.VISIBLE);
            lblFrmAdoption.setVisibility(View.VISIBLE);
            lblAdoption.setVisibility(View.VISIBLE);
        } else {
            if (passengerID.equals(VETER)) {
                imgBtnFrmChoiceEvaluation.setVisibility(View.VISIBLE);
                lblEvaluation.setVisibility(View.VISIBLE);
                imgBtnListAdoption.setVisibility(View.VISIBLE);
                lblAdoption.setVisibility(View.VISIBLE);

            } else {
                imgBtnFrmLocation.setVisibility(View.VISIBLE);
                imgBtnFrmAdoption.setVisibility(View.VISIBLE);
                lblLocation.setVisibility(View.VISIBLE);
                lblFrmAdoption.setVisibility(View.VISIBLE);
            }
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.imgBtnFrmLocation:

                Intent intentLocation = new Intent(ChoiceActivity.this, HomeActivity.class);
                startActivity(intentLocation);
                break;

            case R.id.imgBtnFrmEvaluation:

                Intent intentEvaluation = new Intent(ChoiceActivity.this, ChoiceMedicalActivity.class);
                startActivity(intentEvaluation);
                break;

            case R.id.imgBtnFrmAdoption:
                Intent intentAdoption = new Intent(ChoiceActivity.this, PoliticsActivity.class);
                startActivity(intentAdoption);
                break;
            case R.id.imgBtnListAdoption:
                Intent intentListAdoption = new Intent(ChoiceActivity.this, ChoiceToAdoptActivity.class);
                startActivity(intentListAdoption);
                break;
            case R.id.imgBtnSalir:
                FirebaseAuth.getInstance().signOut();
                Intent intentSalir = new Intent(ChoiceActivity.this, LoginActivity.class);
                startActivity(intentSalir);
                break;
            default:
        }
    }


}