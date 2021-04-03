package com.fernandomoya.appproyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fernandomoya.appproyectofinal.model.Adoption;
import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateDeleteAdoptedActivity extends AppCompatActivity {
    TextView cedulaAdoptante;
    TextView nombreAdoptante;
    TextView edadAdoptante;
    TextView direccionAdoptante;
    TextView estadoAdoptado;
    ImageView imagenPerroAdoptado;
    CheckBox cbxEstado;
    Bundle infoAdopted;
    String urlAdoptado;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabase;
    FirebaseAuth mFirebaseAuth;
    Button btnGuardar;
    Button btnBorrar;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_adopted);

        infoAdopted = getIntent().getExtras();
        cedulaAdoptante = findViewById(R.id.lstCedulaAdoptante);
        nombreAdoptante = findViewById(R.id.lstNombreAdoptante);
        edadAdoptante = findViewById(R.id.lstEdadAdoptante);
        direccionAdoptante = findViewById(R.id.lstDireccionAdoptante);
        cbxEstado = findViewById(R.id.cbxEstadoAdoptado);
        estadoAdoptado = findViewById(R.id.lstEstado);
        btnGuardar = findViewById(R.id.btnActualizarAdoptado);
        btnBorrar = findViewById(R.id.btnEliminarAdoptado);
        String direccion = infoAdopted.getString("direccion");
        direccionAdoptante.setText(direccion);
        String nombre = infoAdopted.getString("nombres");
        nombreAdoptante.setText(nombre);
        String cedula = infoAdopted.getString("cedula");
        cedulaAdoptante.setText(cedula);
        String edad = infoAdopted.getString("edad");
        edadAdoptante.setText(edad);
        String estado = infoAdopted.getString("estado");
        Boolean b = Boolean.getBoolean(estado);
        if (Boolean.TRUE.equals(b)) {
            estadoAdoptado.setText("Adoptado");
        } else {
            estadoAdoptado.setText("En proceso de adopción");
        }

        final String userId = infoAdopted.getString("userId");

        imagenPerroAdoptado = findViewById(R.id.imgListPerroAdoptado);
        String url = infoAdopted.getString("url");
        urlAdoptado = url;
        Glide.with(getApplicationContext()).load(urlAdoptado).into(imagenPerroAdoptado);
        inicializarFirebase();

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference myRef;
                myRef = FirebaseDatabase.getInstance().getReference().child("adopcion");
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot tasksSnapshot) {

                        for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                            Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                            snapshot.getRef().child(userId).setValue(null);
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d("databaseError", error.getMessage());
                    }
                });
                finish();

            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Adoption adoption = new Adoption();

                if (cbxEstado.isChecked()) {
                    adoption.setEstado(Boolean.TRUE);

                    DatabaseReference myRef;
                    myRef = FirebaseDatabase.getInstance().getReference().child("adopcion");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {

                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                                snapshot.getRef().child(userId).child("estado").setValue(adoption.getEstado());
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("databaseError", error.getMessage());
                        }
                    });
                    finish();


                }


                if (!cbxEstado.isChecked()) {
                    adoption.setEstado(Boolean.FALSE);
                    DatabaseReference myRef;
                    myRef = FirebaseDatabase.getInstance().getReference().child("adopcion");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {

                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                                snapshot.getRef().child(userId).child("estado").setValue(adoption.getEstado());
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("databaseError", error.getMessage());
                        }
                    });
                    finish();

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