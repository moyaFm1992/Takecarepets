package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fernandomoya.appproyectofinal.list.MedicalEvaluationListActivity;
import com.google.firebase.auth.FirebaseAuth;

import static com.fernandomoya.appproyectofinal.model.Constant.ADMIN;
import static com.fernandomoya.appproyectofinal.model.Constant.VETER;

public class ChoiceMedicalActivity extends AppCompatActivity implements View.OnClickListener {
    String passengerID;
    ImageButton imgBtnFrmEvaluation;
    ImageButton imgBtnListEvaluation;
    TextView lblEvaluation;
    TextView lblListEvaluation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_medical);
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        imgBtnFrmEvaluation = findViewById(R.id.imgBtnFrmEvaluation);
        imgBtnListEvaluation = findViewById(R.id.imgBtnFrmListEvaluation);
        lblEvaluation = findViewById(R.id.lblEvaluation);
        lblListEvaluation = findViewById(R.id.lblListEvaluation);


        imgBtnFrmEvaluation.setVisibility(View.GONE);
        imgBtnListEvaluation.setVisibility(View.GONE);
        lblEvaluation.setVisibility(View.GONE);
        lblListEvaluation.setVisibility(View.GONE);

        if (passengerID.equals(ADMIN)) {
            imgBtnFrmEvaluation.setVisibility(View.VISIBLE);
            imgBtnListEvaluation.setVisibility(View.VISIBLE);
            lblEvaluation.setVisibility(View.VISIBLE);
            lblListEvaluation.setVisibility(View.VISIBLE);
        } else {
            if (passengerID.equals(VETER)) {
                imgBtnFrmEvaluation.setVisibility(View.VISIBLE);
                lblEvaluation.setVisibility(View.VISIBLE);
                imgBtnListEvaluation.setVisibility(View.VISIBLE);
                lblListEvaluation.setVisibility(View.VISIBLE);

            }
        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.imgBtnFrmEvaluation:

                Intent intentEvaluation = new Intent(ChoiceMedicalActivity.this, MedicalEvaluationActivity.class);
                startActivity(intentEvaluation);
                break;

            case R.id.imgBtnFrmListEvaluation:

                Intent intentListEvaluation = new Intent(ChoiceMedicalActivity.this, MedicalEvaluationListActivity.class);
                startActivity(intentListEvaluation);
                break;
            case R.id.imgBtnSalir:
                FirebaseAuth.getInstance().signOut();
                Intent intentSalir = new Intent(ChoiceMedicalActivity.this, LoginActivity.class);
                startActivity(intentSalir);
                break;
            default:
        }
    }


}