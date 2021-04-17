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
import com.fernandomoya.appproyectofinal.adapter.MedicalAdapter;
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

public class MedicalEvaluationListActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView rw;
    private List<MedicalEvaluation> listaValoracion;
    private MedicalListAdapter medicalAdapter;
    private String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_medical_evaluation_list);
        listaValoracion = new ArrayList<>();
        rw = findViewById(R.id.rvMedicalEvaluationList);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setItemAnimator(new DefaultItemAnimator());
        DatabaseReference myRef;
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        medicalAdapter = new MedicalListAdapter(listaValoracion, R.layout.row_recycler_medical_evaluation_list, this);
        rw.setAdapter(medicalAdapter);
        medicalAdapter.setClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference().child(EVALUATION);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaValoracion.removeAll(listaValoracion);
                for (DataSnapshot dp : dataSnapshot.getChildren()) {
                    for (DataSnapshot dch : dp.getChildren()) {
                        MedicalEvaluation valoracion = dch.getValue(MedicalEvaluation.class);
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
        Intent i = new Intent(this, UpdateDeleteEvaluationListActivity.class);
        i.putExtra("estado", valoracion.getAdoptable().toString());
        i.putExtra("inicial", valoracion.getEstadoInicial());
        i.putExtra("edad", "Edad: " + valoracion.getEdad());
        i.putExtra("sexo", "Tipo: " + valoracion.getSexo());
        i.putExtra("observaciones", valoracion.getObservaciones());
        i.putExtra("fracturas", valoracion.getFracturas());
        i.putExtra("tiempo", "Recuperación: " + valoracion.getTiempoRecuperacion()+" días");
        i.putExtra("url", valoracion.getUrl());
        i.putExtra("userId", valoracion.getuId());
        startActivity(i);
    }
}
