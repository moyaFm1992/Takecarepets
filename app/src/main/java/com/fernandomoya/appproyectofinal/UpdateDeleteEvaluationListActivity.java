package com.fernandomoya.appproyectofinal;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.fernandomoya.appproyectofinal.model.MedicalEvaluation;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.fernandomoya.appproyectofinal.model.Constant.EVALUATION;

public class UpdateDeleteEvaluationListActivity extends AppCompatActivity {
    private TextView estadoEvaluado;
    private TextView edadEvaluado;
    private TextView sexoEvaluado;
    private EditText estadoInicial;
    private EditText observacionesEvaluado;
    private EditText fracturasEvaluado;
    private TextView tiempoEvaluado;
    private ImageView imgPerroEvaluado;
    private Button btnGuardar;
    private Button btnBorrar;
    private CheckBox cbxEstado;
    private Bundle infoEvaluado;
    private FirebaseUser currentUser;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private FirebaseAuth mFirebaseAuth;
    private String urlEvaluado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_evaluartion_list);
        inicializarFirebase();
        infoEvaluado = getIntent().getExtras();
        estadoEvaluado = findViewById(R.id.lstEstadoEvaluado);
        estadoInicial = findViewById(R.id.lstEstadoInicial);
        observacionesEvaluado = findViewById(R.id.lstObservacionesEvaluado);
        fracturasEvaluado = findViewById(R.id.lstFracturasEvaluado);
        edadEvaluado = findViewById(R.id.lstEdadEvaluado);
        sexoEvaluado = findViewById(R.id.lstSexoEvaluado);
        cbxEstado = findViewById(R.id.cbxEstadoAdoptable);
        btnGuardar = findViewById(R.id.btnActualizarList);
        btnBorrar = findViewById(R.id.btnEliminarList);

        final String estado = infoEvaluado.getString("estado");

        Boolean b = Boolean.getBoolean(estado);
        if (Boolean.TRUE.equals(b)) {
            estadoEvaluado.setText("Adoptable");
        } else {
            estadoEvaluado.setText("No adoptable");
        }

        String edad = infoEvaluado.getString("edad");
        edadEvaluado.setText(edad);

        String inicial = infoEvaluado.getString("inicial");
        estadoInicial.setText(inicial);

        String sexo = infoEvaluado.getString("sexo");
        sexoEvaluado.setText(sexo);
        String observaciones = infoEvaluado.getString("observaciones");
        observacionesEvaluado.setText(observaciones);
        String fracturas = infoEvaluado.getString("fracturas");
        fracturasEvaluado.setText(fracturas);
        String url = infoEvaluado.getString("url");
        urlEvaluado = url;

        final String userId = infoEvaluado.getString("userId");

        imgPerroEvaluado = findViewById(R.id.imgListPerroEvaluado);
        Glide.with(getApplicationContext()).load(urlEvaluado).into(imgPerroEvaluado);


        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef;
                myRef = FirebaseDatabase.getInstance().getReference().child(EVALUATION);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                            snapshot.getRef().child(userId).setValue(null);
                        }
                        Intent intentEvaluation = new Intent(UpdateDeleteEvaluationListActivity.this, ChoiceMedicalActivity.class);
                        startActivity(intentEvaluation);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.d("databaseError", error.getMessage());
                    }
                });

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatabaseReference myRef;
                final MedicalEvaluation medicalEvaluation = new MedicalEvaluation();

                if (cbxEstado.isChecked()) {
                    medicalEvaluation.setAdoptable(Boolean.TRUE);
                    medicalEvaluation.setEstadoInicial(estadoInicial.getText().toString());
                    medicalEvaluation.setObservaciones(observacionesEvaluado.getText().toString());
                    medicalEvaluation.setFracturas(fracturasEvaluado.getText().toString());

                    myRef = FirebaseDatabase.getInstance().getReference().child(EVALUATION);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {

                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {

                                Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                                snapshot.getRef().child(userId).child("adoptable").setValue(medicalEvaluation.getAdoptable());
                                snapshot.getRef().child(userId).child("estadoInicial").setValue(medicalEvaluation.getEstadoInicial());
                                snapshot.getRef().child(userId).child("fracturas").setValue(medicalEvaluation.getFracturas());
                                snapshot.getRef().child(userId).child("observaciones").setValue(medicalEvaluation.getObservaciones());
                            }

                            Intent intentEvaluation = new Intent(UpdateDeleteEvaluationListActivity.this, ChoiceMedicalActivity.class);
                            startActivity(intentEvaluation);
                        }


                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.d("databaseError", error.getMessage());
                        }
                    });


                } else {
                    medicalEvaluation.setAdoptable(Boolean.FALSE);
                    medicalEvaluation.setEstadoInicial(estadoInicial.getText().toString());
                    medicalEvaluation.setObservaciones(observacionesEvaluado.getText().toString());
                    medicalEvaluation.setFracturas(fracturasEvaluado.getText().toString());
                    myRef = FirebaseDatabase.getInstance().getReference().child(EVALUATION);
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {

                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {

                                Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                                snapshot.getRef().child(userId).child("adoptable").setValue(medicalEvaluation.getAdoptable());
                                snapshot.getRef().child(userId).child("estadoInicial").setValue(medicalEvaluation.getEstadoInicial());
                                snapshot.getRef().child(userId).child("fracturas").setValue(medicalEvaluation.getFracturas());
                                snapshot.getRef().child(userId).child("observaciones").setValue(medicalEvaluation.getObservaciones());
                            }

                            Intent intentEvaluation = new Intent(UpdateDeleteEvaluationListActivity.this, ChoiceMedicalActivity.class);
                            startActivity(intentEvaluation);
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.d("databaseError", error.getMessage());
                        }
                    });
                }
            }
        });
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        mFirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mFirebaseAuth.getCurrentUser();
    }

}