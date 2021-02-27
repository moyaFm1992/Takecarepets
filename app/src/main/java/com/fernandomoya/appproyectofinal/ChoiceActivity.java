package com.fernandomoya.appproyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;


public class ChoiceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imgBtnFrmLocation:
                Intent intentLocation = new Intent(ChoiceActivity.this, HomeActivity.class);
                startActivity(intentLocation);
                break;
            case R.id.imgBtnFrmEvaluation:
                Intent intentEvaluation = new Intent(ChoiceActivity.this, MedicalEvaluationActivity.class);
                startActivity(intentEvaluation);
                break;
            case R.id.imgBtnFrmAdoption:
                Intent intentAdoption = new Intent(ChoiceActivity.this, PoliticsActivity.class);
                startActivity(intentAdoption);
                break;
            case R.id.imgBtnListAdoption:
                Intent intentListAdoption = new Intent(ChoiceActivity.this, ToAdoptListActivity.class);
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