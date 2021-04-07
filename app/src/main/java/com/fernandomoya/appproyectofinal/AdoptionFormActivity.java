package com.fernandomoya.appproyectofinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.fernandomoya.appproyectofinal.model.Adoption;
import com.fernandomoya.appproyectofinal.model.Message;
import com.fernandomoya.appproyectofinal.model.Send;
import com.fernandomoya.appproyectofinal.model.Validation;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.fernandomoya.appproyectofinal.model.Constant.SALUDO;


public class AdoptionFormActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private EditText nombresApellidos;
    private EditText cedula;
    private EditText edad;
    private EditText telefono;
    private EditText email;
    private EditText direccion;
    private EditText nombresApellidosReferencia;
    private EditText parentesco;
    private EditText telefonoReferencia;
    private EditText instruccion;
    private EditText visita;
    private EditText m2;
    private EditText ocupacion;
    private EditText otros;
    private EditText pregunta1;
    private EditText pregunta2;
    private EditText pregunta3;
    private EditText pregunta4;
    private TextView edadAdopcion;
    private TextView tamanoAdopcion;
    private TextView tipoAdopcion;
    private TextView sexoAdopcion;
    private TextView tiempoMensaje;
    private RadioButton rdbPrimaria;
    private RadioButton rdbSecundario;
    private RadioButton rdbUniversidad;
    private RadioButton rdbPostgrado;
    private RadioButton rdbCasa;
    private RadioButton rdbDepartamento;
    private RadioButton rdbPropio;
    private RadioButton rdbOtros;
    private RadioButton rdbArrendado;
    private RadioButton rdbCinco;
    private RadioButton rdbVeinte;
    private RadioButton rdbCincuenta;
    private RadioButton rdbMas;
    private RadioButton rdbVeterinario;
    private RadioButton rdbMedica;
    private RadioButton rdbSane;
    private RadioButton rdbSi;
    private RadioButton rdbNo;
    private Button btnGuardar;
    private Button btnCancelar;
    private ImageView imagenPerroAdopcion;
    private String urlPerroAdopcion;
    private URL url;
    private FirebaseDatabase firebaseDatabase;
    private ProgressBar mProgressBar;
    private Bundle infoMedicalEvaluation;
    private Handler hdlr = new Handler();
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String passengerID;
    private int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_form);

        mProgressBar = findViewById(R.id.simpleProgressBar);
        infoMedicalEvaluation = getIntent().getExtras();
        edadAdopcion = findViewById(R.id.lstEdadAdopcion);
        tipoAdopcion = findViewById(R.id.lstTipoAdopcion);
        sexoAdopcion = findViewById(R.id.lstSexoAdopcion);
        tamanoAdopcion = findViewById(R.id.lstTamanoAdopcion);
        tiempoMensaje = findViewById(R.id.tv);
        String edadAdoptante = infoMedicalEvaluation.getString("edad");
        edadAdopcion.setText(edadAdoptante);
        String tipoAdoptante = infoMedicalEvaluation.getString("tipo");
        tipoAdopcion.setText(tipoAdoptante);
        String sexoAdoptante = infoMedicalEvaluation.getString("sexo");
        sexoAdopcion.setText(sexoAdoptante);
        String tamanoAdoptante = infoMedicalEvaluation.getString("tamano");
        tamanoAdopcion.setText(tamanoAdoptante);
        imagenPerroAdopcion = findViewById(R.id.imgListPerroAdopcion);
        String urlAdoptante = infoMedicalEvaluation.getString("url");
        urlPerroAdopcion = urlAdoptante;


        try {
            url = new URL(urlAdoptante);
        } catch (MalformedURLException e) {
            Log.i("MalformedURLException ", e.getMessage());
        }

        Glide.with(getApplicationContext()).load(urlAdoptante).into(imagenPerroAdopcion);

        nombresApellidos = findViewById(R.id.txtNombresApellidos);
        edad = findViewById(R.id.txtEdad);
        edad.setInputType(InputType.TYPE_CLASS_NUMBER);
        cedula = findViewById(R.id.txtCedula);
        telefono = findViewById(R.id.txtTelefono);
        email = findViewById(R.id.txtEmailAdoptante);
        ocupacion = findViewById(R.id.txtOcupacion);
        direccion = findViewById(R.id.txtDireccion);
        nombresApellidosReferencia = findViewById(R.id.txtNombresReferencia);
        telefonoReferencia = findViewById(R.id.txtTelefonoReferencia);
        parentesco = findViewById(R.id.txtParentesco);
        instruccion = findViewById(R.id.txtInstruccion);
        otros = findViewById(R.id.txtTipo);
        visita = findViewById(R.id.eTMTVisitas);
        rdbPrimaria = findViewById(R.id.rbPrimaria);
        rdbSecundario = findViewById(R.id.rbSecundaria);
        rdbUniversidad = findViewById(R.id.rbGrado);
        rdbPostgrado = findViewById(R.id.rbPostgrado);
        rdbCasa = findViewById(R.id.rbCasa);
        rdbDepartamento = findViewById(R.id.rbDepartamento);
        rdbPropio = findViewById(R.id.rbPropio);
        rdbArrendado = findViewById(R.id.rbArrendado);
        rdbOtros = findViewById(R.id.rbOtros);
        m2 = findViewById(R.id.txtm2);
        m2.setInputType(InputType.TYPE_CLASS_NUMBER);
        rdbCinco = findViewById(R.id.rb5a20);
        rdbVeinte = findViewById(R.id.rb21a50);
        rdbCincuenta = findViewById(R.id.rb51a80);
        rdbMas = findViewById(R.id.rbMas50);
        rdbVeterinario = findViewById(R.id.rbVeterinario);
        rdbMedica = findViewById(R.id.rbMismo);
        rdbSane = findViewById(R.id.rbSolo);
        rdbSi = findViewById(R.id.rbSi);
        rdbNo = findViewById(R.id.rbNo);
        pregunta1 = findViewById(R.id.txtPregunta1);
        pregunta2 = findViewById(R.id.txtPregunta2);
        pregunta3 = findViewById(R.id.txtPregunta3);
        pregunta4 = findViewById(R.id.txtPregunta4);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnGuardar.setVisibility(View.VISIBLE);
        inicializarFirebase();

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = mProgressBar.getProgress();

                validarPermisos();

                Adoption adoption = new Adoption();
                Message message = new Message();
                Date date = new Date();
                final Send envio = new Send();
                Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
                final String userId = mDatabase.push().getKey();
                final String apellido = nombresApellidos.getText().toString();
                final String tieneEdad = edad.getText().toString();
                final String pin = cedula.getText().toString();
                final String mail = email.getText().toString();
                final String metrosC = m2.getText().toString();
                final String telefonoRef = telefonoReferencia.getText().toString();
                final String telef = telefono.getText().toString();
                final String dirc = direccion.getText().toString();
                final String apellidoRef = nombresApellidosReferencia.getText().toString();
                final String parentescoRef = parentesco.getText().toString();
                final String preg1 = pregunta1.getText().toString();
                final String preg2 = pregunta2.getText().toString();
                final String preg3 = pregunta3.getText().toString();
                final String preg4 = pregunta4.getText().toString();
                final String visitaMensual = visita.getText().toString();
                final String ocupacionAdoptante = ocupacion.getText().toString();
                String fechaRegistro = dt.format(newDate);


                if (rdbPrimaria.isChecked()) {
                    adoption.setInstruccion(rdbPrimaria.getText().toString());
                }
                if (rdbSecundario.isChecked()) {
                    adoption.setInstruccion(rdbSecundario.getText().toString());
                }

                if (rdbUniversidad.isChecked()) {
                    adoption.setInstruccion(rdbUniversidad.getText().toString());
                }
                if (rdbPostgrado.isChecked()) {
                    adoption.setInstruccion(rdbPostgrado.getText().toString());
                }

                if (rdbCasa.isChecked()) {
                    adoption.setTipoInmueble(rdbCasa.getText().toString());
                }

                if (rdbDepartamento.isChecked()) {
                    adoption.setTipoInmueble(rdbDepartamento.getText().toString());
                }

                if (rdbPropio.isChecked()) {
                    adoption.setPropio(rdbPropio.getText().toString());
                }

                if (rdbArrendado.isChecked()) {
                    adoption.setPropio(rdbArrendado.getText().toString());
                }

                if (rdbCinco.isChecked()) {
                    adoption.setValorAGastar(rdbCinco.getText().toString());
                }
                if (rdbVeinte.isChecked()) {
                    adoption.setValorAGastar(rdbVeinte.getText().toString());
                }
                if (rdbCincuenta.isChecked()) {
                    adoption.setValorAGastar(rdbCincuenta.getText().toString());
                }
                if (rdbMas.isChecked()) {
                    adoption.setValorAGastar(rdbMas.getText().toString());
                }

                if (rdbVeterinario.isChecked()) {
                    adoption.setMascotaEnferma(rdbVeterinario.getText().toString());
                }

                if (rdbMedica.isChecked()) {
                    adoption.setMascotaEnferma(rdbMedica.getText().toString());
                }

                if (rdbSane.isChecked()) {
                    adoption.setMascotaEnferma(rdbSane.getText().toString());
                }


                if (visitaMensual != null) {
                    adoption.setVisitaMensual(visitaMensual);
                } else {
                    adoption.setVisitaMensual("Sin registro");
                }


                adoption.setVisitaMensual(rdbSi.getText().toString());


                if (rdbNo.isChecked()) {
                    adoption.setVisitaMensual(rdbNo.getText().toString());
                }
                adoption.setNombresApellidos(apellido);
                adoption.setEdad(tieneEdad);
                adoption.setCedula(pin);
                adoption.setTelefono(telef);
                adoption.setDireccion(dirc);
                adoption.setOcupacion(ocupacionAdoptante);
                adoption.setEmail(mail);
                adoption.setM2(metrosC);
                adoption.setNombresApellidosReferencia(apellidoRef);
                adoption.setTelefonoReferencia(telefonoRef);
                adoption.setParentesco(parentescoRef);
                adoption.setPregunta1(preg1);
                adoption.setPregunta2(preg2);
                adoption.setPregunta3(preg3);
                adoption.setPregunta4(preg4);
                adoption.setUrl(urlPerroAdopcion);
                adoption.setEstado(Boolean.FALSE);
                adoption.setFechaRegistro(fechaRegistro);
                adoption.setFechaAdopcion(dt.format(newDate));
                adoption.setuId(userId);
                if (validarAdopcion()) {
                    return;
                }
                mDatabase.child("adopcion").child(passengerID).child(userId).setValue(adoption);


                if (!(mail.trim().isEmpty()) || (mail != null)) {
                    envio.enviar(mail, SALUDO + apellido + message.email());
                }

                new Thread(new Runnable() {
                    public void run() {

                        while (i < 100) {
                            i += 1;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    mProgressBar.setProgress(i);
                                    visita.setFocusable(Boolean.FALSE);
                                    btnGuardar.setVisibility(View.GONE);
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    tiempoMensaje.setVisibility(View.VISIBLE);
                                    tiempoMensaje.setText("Un momento, por favor");
                                    Log.i("Message: ", i + "/" + mProgressBar.getMax());
                                    if (i == 100) {
                                        btnGuardar.setVisibility(View.VISIBLE);
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
                                Log.i("databaseError", e.getMessage());
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                }).start();
            }
        });
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public boolean validarAdopcion() {
        Validation validador = new Validation();
        if (validador.vacio(nombresApellidos)) {
            nombresApellidos.setError("Nombres y Apellidos obligatorios.");
            nombresApellidos.requestFocus();
            return true;
        }
        if (validador.vacio(cedula)) {
            cedula.setError("Número de cédula obligatorio.");
            cedula.requestFocus();
            return true;
        }
        if (cedula != null && !(validador.isValid(cedula.getText().toString()))) {
            cedula.setError("Número de cédula ingresado incorrecto.");
            cedula.requestFocus();
            return true;
        }

        if (validador.vacio(telefono)) {
            telefono.setError("Número de teléfono obligatorio.");
            telefono.requestFocus();
            return true;
        }

        if (validador.vacio(edad)) {
            edad.setError("Edad obligatoria.");
            edad.requestFocus();
            return true;
        }

        if (validador.vacio(email)) {
            email.setError("Email obligatorio.");
            email.requestFocus();
            return true;
        }

        if (!validador.isValidEmailId(email.getText().toString())) {
            email.setError("Email ingresado incorrecto.");
            email.requestFocus();
            return true;
        }

        if (validador.vacio(nombresApellidosReferencia)) {
            nombresApellidosReferencia.setError("Nombres y Apellidos de referencia obligatorio.");
            nombresApellidosReferencia.requestFocus();
            return true;
        }

        if (validador.vacio(parentesco)) {
            parentesco.setError("Parentesco obligatorio.");
            parentesco.requestFocus();
            return true;
        }

        if (validador.vacio(telefonoReferencia)) {
            telefonoReferencia.setError("Teléfono de referencia obligatorio.");
            telefonoReferencia.requestFocus();
            return true;
        }


        if (validador.vacio(direccion)) {
            direccion.setError("Dirección obligatoria.");
            direccion.requestFocus();
            return true;
        }


        if (!(rdbCasa.isChecked()
                || rdbDepartamento.isChecked()
                || rdbOtros.isChecked())) {
            otros.setError("Tipo inmbueble obligatorio.");
            otros.requestFocus();
            return true;
        }

        if (!(rdbPropio.isChecked()
                || rdbArrendado.isChecked())) {
            otros.setError("Tipo inmbueble obligatorio.");
            otros.requestFocus();
            return true;
        }

        if (validador.vacio(m2)) {
            m2.setError("Tamaño del inmueble en m² es obligatorio.");
            m2.requestFocus();
            return true;
        }


        if (validador.vacio(pregunta1)) {
            pregunta1.setError("¿Por qué desea adoptar una mascota? obligatoria.");
            pregunta1.requestFocus();
            return true;
        }

        if (validador.vacio(pregunta2)) {
            pregunta2.setError("¿Responsable de cubrir los gastos de la mascota? obligatoria.");
            pregunta2.requestFocus();
            return true;
        }

        if (validador.vacio(pregunta3)) {
            pregunta3.setError("Si por algún motivo tuviera que cambiar de domicilio, ¿qué pasaría con su mascota? obligatoria.");
            pregunta3.requestFocus();
            return true;
        }

        if (validador.vacio(pregunta4)) {
            pregunta4.setError("Con relación a la anterior pregunta ¿qué pasaría si los dueños de la nueva casa no aceptacen mascotas? obligatoria.");
            pregunta4.requestFocus();
            return true;
        }

        if (rdbSi.isChecked() && validador.vacio(visita)) {

            visita.setError("Ingrese el motivo para la visita.");
            visita.requestFocus();
            return true;
        }

        if (rdbNo.isChecked() && validador.vacio(visita)) {
            visita.setError("Ingrese el motivo para no realizar la visita.");
            visita.requestFocus();
            return true;

        }

        return false;
    }


    public void validarPermisos() {
        if (
                ActivityCompat.checkSelfPermission(
                        AdoptionFormActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AdoptionFormActivity.this, new String[]
                    {Manifest.permission.SEND_SMS,}, 1000);
        }
    }

}

