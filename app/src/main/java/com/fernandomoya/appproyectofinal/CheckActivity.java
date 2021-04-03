package com.fernandomoya.appproyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fernandomoya.appproyectofinal.model.Adoption;
import com.fernandomoya.appproyectofinal.model.Message;
import com.fernandomoya.appproyectofinal.model.Send;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.fernandomoya.appproyectofinal.model.Constant.SALUDO;

public class CheckActivity extends AppCompatActivity {
    TextView nombresApellidos;
    TextView cedula;
    TextView edad;
    TextView telefono;
    TextView email;
    TextView direccion;
    TextView ocupacion;
    TextView instruccion;
    TextView casa;
    TextView propio;
    TextView m2;
    TextView pregunta1;
    TextView pregunta2;
    TextView pregunta3;
    TextView pregunta4;
    TextView pregunta5;
    TextView pregunta6;
    TextView pregunta7;
    Button btnGuardarAdoptado;
    Button btnCancelar;
    CheckBox cbxParteI;
    CheckBox cbxParteII;
    CheckBox cbxParteIII;
    CheckBox cbxParteIV;
    DatabaseReference mDatabase;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser currentUser;
    Bundle infoAdopt;
    ProgressBar mProgressBar;
    TextView tiempoMensaje;
    int i = 0;
    Handler hdlr = new Handler();
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        mProgressBar = findViewById(R.id.simpleProgressBar);
        tiempoMensaje = findViewById(R.id.tv);
        infoAdopt = getIntent().getExtras();
        nombresApellidos = findViewById(R.id.lblCheckNombresApellidos);
        cedula = findViewById(R.id.lblCheckCedula);
        telefono = findViewById(R.id.lblCheckTelefono);
        edad = findViewById(R.id.lblCheckEdad);
        email = findViewById(R.id.lblCheckEmailAdoptante);
        ocupacion = findViewById(R.id.lblCheckOcupacion);
        direccion = findViewById(R.id.lblCheckDireccion);
        instruccion = findViewById(R.id.lblCheckPrimaria);
        casa = findViewById(R.id.lblCheckCasa);
        m2 = findViewById(R.id.lblCheckM2);
        propio = findViewById(R.id.lblCheckPropio);
        pregunta1 = findViewById(R.id.lblRespuesta1);
        pregunta2 = findViewById(R.id.lblRespuesta2);
        pregunta3 = findViewById(R.id.lblRespuesta3);
        pregunta4 = findViewById(R.id.lblRespuesta4);
        pregunta5 = findViewById(R.id.lblRespuestaAGastar);
        pregunta6 = findViewById(R.id.lblRespuestaVeterinario);
        pregunta7 = findViewById(R.id.lblRespuestaPorque);
        cbxParteI = findViewById(R.id.cbkParteI);
        cbxParteII = findViewById(R.id.cbkParteII);
        cbxParteIII = findViewById(R.id.cbkParteIII);
        cbxParteIV = findViewById(R.id.cbkParteIV);
        btnGuardarAdoptado = findViewById(R.id.btnCheckGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        String nombre = infoAdopt.getString("nombres");
        nombresApellidos.setText(nombre);
        String telf = infoAdopt.getString("telefono");
        telefono.setText(telf);
        String pin = infoAdopt.getString("cedula");
        cedula.setText(pin);
        String edadA = infoAdopt.getString("edad");
        edad.setText(edadA + " años");
        String mail = infoAdopt.getString("email");
        email.setText(mail);
        String ocup = infoAdopt.getString("ocupacion");
        ocupacion.setText(ocup);
        String direcc = infoAdopt.getString("direccion");
        direccion.setText(direcc);
        String inst = infoAdopt.getString("instruccion");
        instruccion.setText(inst);
        String tip = infoAdopt.getString("tipoinmueble");
        casa.setText(tip);
        String metros2 = infoAdopt.getString("m2");
        m2.setText(metros2 + " metros cuadrados");
        String prop = infoAdopt.getString("propio");
        propio.setText(prop);
        String preg1 = infoAdopt.getString("pregunta1");
        pregunta1.setText(preg1);
        String preg2 = infoAdopt.getString("pregunta2");
        pregunta2.setText(preg2);
        String preg3 = infoAdopt.getString("pregunta3");
        pregunta3.setText(preg3);
        String preg4 = infoAdopt.getString("pregunta4");
        pregunta4.setText(preg4);
        String preg5 = infoAdopt.getString("pregunta5");
        pregunta5.setText(preg5);
        String preg6 = infoAdopt.getString("pregunta6");
        pregunta6.setText(preg6);
        String preg7 = infoAdopt.getString("pregunta7");
        pregunta7.setText(preg7);
        final String userId = infoAdopt.getString("userId");


        inicializarFirebase();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardarAdoptado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = mProgressBar.getProgress();
                DatabaseReference myRef;
                final Adoption adoption = new Adoption();
                Date date = new Date();
                final Message message = new Message();
                Date fechaAdopcion = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
                String fechaRegistro = dt.format(fechaAdopcion);
                final Send envio = new Send();


                adoption.setFechaAdopcion(fechaRegistro);

                new Thread(new Runnable() {
                    public void run() {

                        while (i < 100) {
                            i += 1;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    mProgressBar.setProgress(i);
                                    btnGuardarAdoptado.setVisibility(View.GONE);
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    tiempoMensaje.setVisibility(View.VISIBLE);
                                    tiempoMensaje.setText("Un momento, por favor");
                                    Log.i("Message: ", i + "/" + mProgressBar.getMax());
                                    if (i == 100) {
                                        btnGuardarAdoptado.setVisibility(View.VISIBLE);
                                        mProgressBar.setVisibility(View.GONE);
                                        tiempoMensaje.setVisibility(View.GONE);
                                        finish();
                                    }
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds to show the progress slowly.
                                Thread.sleep(100);
                            } catch (Exception e) {
                                Log.i("InterruptedException: ", e.getMessage());
                            }
                        }
                    }
                }).start();

                if (cbxParteI.isChecked() && cbxParteII.isChecked() && cbxParteIII.isChecked() && cbxParteIV.isChecked()) {


                    adoption.setEstado(Boolean.TRUE);
                    adoption.setFechaAdopcion(fechaRegistro);

                    myRef = FirebaseDatabase.getInstance().getReference("adopcion");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                                snapshot.getRef().child(userId).child("fechaAdopcion").setValue(adoption.getFechaAdopcion());
                                snapshot.getRef().child(userId).child("estado").setValue(adoption.getEstado());
                            }
                            envio.enviar(email.getText().toString(), SALUDO + nombresApellidos.getText() + message.emailAceptado());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.d("databaseError", error.getMessage());
                        }

                    });

                    finish();

                } else {
                    adoption.setEstado(Boolean.FALSE);
                    adoption.setFechaAdopcion(fechaRegistro);
                    myRef = FirebaseDatabase.getInstance().getReference("adopcion");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot tasksSnapshot) {
                            for (DataSnapshot snapshot : tasksSnapshot.getChildren()) {
                                Log.i("Message 1: ", snapshot.getRef().child(userId).toString());
                                snapshot.getRef().child(userId).child("fechaAdopcion").setValue(adoption.getFechaAdopcion());
                                snapshot.getRef().child(userId).child("estado").setValue(adoption.getEstado());
                            }
                            envio.enviar(email.getText().toString(), SALUDO + nombresApellidos.getText() + message.emailNegado());
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
