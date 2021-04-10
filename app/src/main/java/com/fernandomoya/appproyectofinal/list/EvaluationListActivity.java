package com.fernandomoya.appproyectofinal.list;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fernandomoya.appproyectofinal.ItemClickListener;
import com.fernandomoya.appproyectofinal.R;
import com.fernandomoya.appproyectofinal.UpdateDeleteEvaluationListActivity;
import com.fernandomoya.appproyectofinal.adapter.MedicalListAdapter;
import com.fernandomoya.appproyectofinal.model.MedicalEvaluation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.fernandomoya.appproyectofinal.model.Constant.EVALUATION;

public class EvaluationListActivity extends AppCompatActivity  implements ItemClickListener {

    private RecyclerView rwEvaluationList;
    private List<MedicalEvaluation> listaEvaluationList;
    private MedicalListAdapter medicalEvaluationListAdapter;
    private DatabaseReference myRef;
    private String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_medical_evaluation_list);
        listaEvaluationList = new ArrayList<>();
        rwEvaluationList = findViewById(R.id.rvMedicalEvaluationList);
        rwEvaluationList.setLayoutManager(new LinearLayoutManager(this));
        rwEvaluationList.setItemAnimator(new DefaultItemAnimator());
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        medicalEvaluationListAdapter = new MedicalListAdapter(listaEvaluationList, R.layout.row_recycler_medical_evaluation_list, this);
        rwEvaluationList.setAdapter(medicalEvaluationListAdapter);
        medicalEvaluationListAdapter.setClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference().child(EVALUATION);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaEvaluationList.removeAll(listaEvaluationList);
                for (DataSnapshot dp : dataSnapshot.getChildren()) {
                    for (DataSnapshot dch : dp.getChildren()) {
                        MedicalEvaluation valoracion = dch.getValue(MedicalEvaluation.class);
                        listaEvaluationList.add(valoracion);
                    }
                }
                medicalEvaluationListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.getMessage());
            }
        });

    }


    @Override
    public void onClick(View view, int position) {
        MedicalEvaluation medicalEvaluation = listaEvaluationList.get(position);
        Intent i = new Intent(this, UpdateDeleteEvaluationListActivity.class);
        i.putExtra("estado", medicalEvaluation.getEstadoInicial());
        i.putExtra("edad", "Edad: " + medicalEvaluation.getEdad());
        i.putExtra("sexo", "Sexo: " + medicalEvaluation.getSexo());
        i.putExtra("observaciones", medicalEvaluation.getObservaciones());
        i.putExtra("fracturas", "Fracturas: " + medicalEvaluation.getFracturas());
        i.putExtra("tiempo", "Tiempo: " + medicalEvaluation.getTiempoRecuperacion());
        i.putExtra("url", medicalEvaluation.getUrl());
        i.putExtra("userId", medicalEvaluation.getuId());

    }
}
