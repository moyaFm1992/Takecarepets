package com.fernandomoya.appproyectofinal.list;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fernandomoya.appproyectofinal.AdoptionFormActivity;
import com.fernandomoya.appproyectofinal.ItemClickListener;
import com.fernandomoya.appproyectofinal.R;
import com.fernandomoya.appproyectofinal.adapter.MedicalAdapter;
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

public class AdoptionListActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView rw;
    private List<MedicalEvaluation> listaValoracion;
    private MedicalAdapter medicalAdapter;
    private String passengerID;

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

        myRef = FirebaseDatabase.getInstance().getReference().child(EVALUATION);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaValoracion.removeAll(listaValoracion);
                for (DataSnapshot dp : dataSnapshot.getChildren()) {
                    for (DataSnapshot dch : dp.getChildren()) {
                        if (dch.child("adoptable").getValue(Boolean.class).equals(Boolean.TRUE) && (dch.child("adoptado").getValue(Boolean.class).equals(Boolean.FALSE))) {
                            MedicalEvaluation valoracion = dch.getValue(MedicalEvaluation.class);
                            listaValoracion.add(valoracion);
                        }
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
        i.putExtra("userId", valoracion.getuId());
        startActivity(i);
    }
}
