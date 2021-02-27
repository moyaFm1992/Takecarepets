package com.fernandomoya.appproyectofinal;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fernandomoya.appproyectofinal.model.MedicalAdapter;
import com.fernandomoya.appproyectofinal.model.MedicalEvaluation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdoptionListActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView rw;
    List<MedicalEvaluation> listaValoracion;
    MedicalAdapter medicalAdapter;
    String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_medical_evaluation);
        listaValoracion = new ArrayList<>();
        rw = findViewById(R.id.rvMedicalEvaluation);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setItemAnimator(new DefaultItemAnimator());
        DatabaseReference myRef;
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        medicalAdapter = new MedicalAdapter(listaValoracion, R.layout.row_recycler_medical_evaluation, this);
        rw.setAdapter(medicalAdapter);
        medicalAdapter.setClickListener(this);
        myRef = FirebaseDatabase.getInstance().getReference().child("valoracion").child(passengerID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("adoptable").getValue(Boolean.class).equals(Boolean.TRUE)) {
                        MedicalEvaluation valoracion = ds.getValue(MedicalEvaluation.class);
                        listaValoracion.add(valoracion);
                    }


                }
                medicalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view, int position) {

        MedicalEvaluation valoracion = listaValoracion.get(position);
        Intent i = new Intent(this, AdoptionFormActivity.class);
        i.putExtra("edad", "Edad: " + valoracion.getEdad());
        i.putExtra("tipo", "Tipo: " + valoracion.getTipo());
        i.putExtra("sexo", "Sexo: " + valoracion.getSexo());
        i.putExtra("tamano", "Tamaño: " + valoracion.getTamano());
        i.putExtra("url", valoracion.getUrl());
        startActivity(i);
    }
}
