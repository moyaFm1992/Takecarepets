package com.fernandomoya.appproyectofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.fernandomoya.appproyectofinal.model.MedicalEvaluation;
import com.fernandomoya.appproyectofinal.model.Validation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.fernandomoya.appproyectofinal.HomeActivity.REQUEST_TAKE_PHOTO;
import static com.fernandomoya.appproyectofinal.model.Constant.EVALUATION;
import static com.fernandomoya.appproyectofinal.model.Constant.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

public class MedicalEvaluationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tiempoMensaje;
    private EditText estadoInicial;
    private EditText edad;
    private EditText fracturas;
    private EditText observaciones;
    private EditText medicamentos;
    private EditText tiempo;
    private EditText tipo;
    private RadioButton rdbMacho;
    private RadioButton rdbHembra;
    private RadioButton rdbAdulto;
    private RadioButton rdbCachorro;
    private RadioButton rdbGrande;
    private RadioButton rdbMediano;
    private RadioButton rdbPequeno;
    private RadioButton rdbSi;
    private RadioButton rdbNo;
    private CheckBox cbxDrontal;
    private CheckBox cbxRimadyltal;
    private CheckBox cbxPipeta;
    private CheckBox cbxEmulgel;
    private CheckBox cbxMeloxic;
    private CheckBox cbxBrillo;
    private CheckBox cbxCoccigan;
    private CheckBox cbxSuero;
    private CheckBox cbxAdoptable;
    private CheckBox cbxMoquillo;
    private CheckBox cbxHepatitis;
    private CheckBox cbxParvovirosis;
    private CheckBox cbxLeptospirosis;
    private CheckBox cbxTos;
    private CheckBox cbxRabia;
    private CheckBox cbxBabesiosis;
    private CheckBox cbxLeishmaniasis;
    private CheckBox cbxLyme;
    private Button btnGuardar;
    private Button btnCancelar;
    private ImageView imgPerroEvaluacion;
    private ImageButton imgBtnCameraEvaluacion;
    private Uri imageURIEvaluacion;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageReference;
    private ProgressBar mProgressBar;
    private String mCurrentPhotoPath;
    private String passengerID;
    private Uri urlValoracion;
    private final SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private final Handler hdlr = new Handler();
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_evaluation);
        validarPermisos();
        inicializarFirebase();
        imgPerroEvaluacion = findViewById(R.id.imgPerroEvaluacion);
        imgBtnCameraEvaluacion = findViewById(R.id.imgBtnCameraEvaluacion);
        estadoInicial = findViewById(R.id.eTMLEstadoInicial);
        edad = findViewById(R.id.txtEdadEvaluacion);
        medicamentos = findViewById(R.id.txtOtrosMedicamentos);
        tiempo = findViewById(R.id.txtTiempo);
        tiempo.setInputType(InputType.TYPE_CLASS_NUMBER);
        fracturas = findViewById(R.id.eTMTFracturas);
        observaciones = findViewById(R.id.eTMLTObservaciones);
        rdbMacho = findViewById(R.id.rbMachoEvaluacion);
        rdbHembra = findViewById(R.id.rbHembraEvaluacion);
        rdbAdulto = findViewById(R.id.rbAdultoEvaluacion);
        rdbCachorro = findViewById(R.id.rbCachorroEvaluacion);
        rdbGrande = findViewById(R.id.rbGrandeEvaluacion);
        rdbMediano = findViewById(R.id.rbMedianoEvaluacion);
        rdbPequeno = findViewById(R.id.rbPequenoEvaluacion);
        rdbSi = findViewById(R.id.rbSiEvaluacion);
        rdbNo = findViewById(R.id.rbNoEvaluacion);
        cbxDrontal = findViewById(R.id.cbxDrontal);
        cbxRimadyltal = findViewById(R.id.cbxRimadyltal);
        cbxPipeta = findViewById(R.id.cbxPipeta);
        cbxEmulgel = findViewById(R.id.cbxEmulgel);
        cbxMeloxic = findViewById(R.id.cbxMeloxic);
        cbxBrillo = findViewById(R.id.cbxBrillo);
        cbxCoccigan = findViewById(R.id.cbxCoccigan);
        cbxSuero = findViewById(R.id.cbxSuero);
        cbxAdoptable = findViewById(R.id.cbxAdoptable);
        cbxMoquillo = findViewById(R.id.cbxMoquillo);
        cbxHepatitis = findViewById(R.id.cbxHepatitis);
        cbxParvovirosis = findViewById(R.id.cbxParvovirosis);
        cbxLeptospirosis = findViewById(R.id.cbxLeptospirosis);
        cbxTos = findViewById(R.id.cbxTos);
        cbxRabia = findViewById(R.id.cbxRabia);
        cbxBabesiosis = findViewById(R.id.cbxBabesiosis);
        cbxLyme = findViewById(R.id.cbxLyme);
        cbxLeishmaniasis = findViewById(R.id.cbxLeishmaniasis);
        btnGuardar = findViewById(R.id.btnGuardarEvaluacion);
        btnCancelar = findViewById(R.id.btnCancelar);
        tiempoMensaje = findViewById(R.id.tv);
        tipo = findViewById(R.id.txtTipoEdad);
        mProgressBar = findViewById(R.id.simpleProgressBar);
        imgBtnCameraEvaluacion.setOnClickListener(this);


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (imageURIEvaluacion == null) {
                    Toast.makeText(MedicalEvaluationActivity.this, "Fotografía necesaria", Toast.LENGTH_SHORT).show();
                    return;
                }

                final MedicalEvaluation medicalEvaluation = new MedicalEvaluation();


                final String userId = mDatabase.push().getKey();
                final String estado = estadoInicial.getText().toString();
                final String tieneObservaciones = observaciones.getText().toString();
                final String medicamentosSum = medicamentos.getText().toString();
                final List<String> listaMedicamentos = new LinkedList<>();
                final String tieneEdad = edad.getText().toString();
                final String tiempoRecu = tiempo.getText().toString();
                final String tieneFracturas = fracturas.getText().toString();


                if (rdbMacho.isChecked()) {
                    medicalEvaluation.setSexo(rdbMacho.getText().toString());
                }
                if (rdbHembra.isChecked()) {
                    medicalEvaluation.setSexo(rdbHembra.getText().toString());
                }

                if (rdbAdulto.isChecked()) {
                    medicalEvaluation.setTipo(rdbAdulto.getText().toString());
                }
                if (rdbCachorro.isChecked()) {
                    medicalEvaluation.setTipo(rdbCachorro.getText().toString());
                }

                if (rdbGrande.isChecked()) {
                    medicalEvaluation.setTamano(rdbGrande.getText().toString());
                }

                if (rdbMediano.isChecked()) {
                    medicalEvaluation.setTamano(rdbMediano.getText().toString());
                }

                if (rdbPequeno.isChecked()) {
                    medicalEvaluation.setTamano(rdbPequeno.getText().toString());
                }

                if (rdbNo.isChecked()) {
                    medicalEvaluation.setFracturasSiNo(rdbNo.getText().toString());
                    medicalEvaluation.setFracturas("Sin fracturas");
                }

                if (cbxDrontal.isChecked()) {
                    listaMedicamentos.add(cbxDrontal.getText().toString() + "/");
                }

                if (cbxRimadyltal.isChecked()) {
                    listaMedicamentos.add(cbxRimadyltal.getText().toString() + "/");
                }

                if (cbxEmulgel.isChecked()) {
                    listaMedicamentos.add(cbxEmulgel.getText().toString() + "/");
                }

                if (cbxMeloxic.isChecked()) {
                    listaMedicamentos.add(cbxMeloxic.getText().toString() + "/");
                }

                if (cbxBrillo.isChecked()) {
                    listaMedicamentos.add(cbxBrillo.getText().toString() + "/");
                }

                if (cbxCoccigan.isChecked()) {
                    listaMedicamentos.add(cbxCoccigan.getText().toString() + "/");
                }

                if (cbxSuero.isChecked()) {
                    listaMedicamentos.add(cbxSuero.getText().toString() + "/");
                }

                //Vacunas

                if (cbxMoquillo.isChecked()) {
                    listaMedicamentos.add(cbxMoquillo.getText().toString() + "/");
                }

                if (cbxHepatitis.isChecked()) {
                    listaMedicamentos.add(cbxHepatitis.getText().toString() + "/");
                }

                if (cbxParvovirosis.isChecked()) {
                    listaMedicamentos.add(cbxParvovirosis.getText().toString() + "/");
                }

                if (cbxLeptospirosis.isChecked()) {
                    listaMedicamentos.add(cbxLeptospirosis.getText().toString() + "/");
                }

                if (cbxTos.isChecked()) {
                    listaMedicamentos.add(cbxTos.getText().toString() + "/");
                }

                if (cbxRabia.isChecked()) {
                    listaMedicamentos.add(cbxRabia.getText().toString() + "/");
                }

                if (cbxBabesiosis.isChecked()) {
                    listaMedicamentos.add(cbxBabesiosis.getText().toString() + "/");
                }

                if (cbxLyme.isChecked()) {
                    listaMedicamentos.add(cbxLyme.getText().toString() + "/");
                }

                if (cbxLeishmaniasis.isChecked()) {
                    listaMedicamentos.add(cbxLeishmaniasis.getText().toString() + "/");
                }


                if (medicamentosSum != null) {
                    listaMedicamentos.add(medicamentosSum);
                } else {
                    listaMedicamentos.add("");
                }

                StringBuilder medicamentosSuministrados = new StringBuilder();
                for (String med : listaMedicamentos) {
                    medicamentosSuministrados.append(med);
                }
                String med = medicamentosSuministrados.toString();

                if (cbxAdoptable.isChecked()) {
                    medicalEvaluation.setAdoptable(Boolean.TRUE);
                } else {
                    medicalEvaluation.setAdoptable(Boolean.FALSE);
                }

                medicalEvaluation.setMedicamentos(med);
                medicalEvaluation.setTiempoRecuperacion(tiempoRecu);
                medicalEvaluation.setFracturasSiNo(rdbSi.getText().toString());
                medicalEvaluation.setFracturas(tieneFracturas);
                medicalEvaluation.setEstadoInicial(estado);
                medicalEvaluation.setEdad(tieneEdad);
                medicalEvaluation.setObservaciones(tieneObservaciones);
                medicalEvaluation.setTiempoRecuperacion(tiempoRecu);
                medicalEvaluation.setAdoptado(Boolean.FALSE);
                medicalEvaluation.setFechaValoracion(dt.format(new Date()));
                medicalEvaluation.setuId(userId);

                if (validarEvaluacion()) {
                    return;
                }

                StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                        + "." + fileExtension(imageURIEvaluacion));
                fileReference.putFile(imageURIEvaluacion)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uri.isComplete()) ;
                                Uri url = uri.getResult();
                                urlValoracion = url;
                                medicalEvaluation.setUrl(urlValoracion.toString());
                                mDatabase.child(EVALUATION).child(passengerID).child(userId).setValue(medicalEvaluation);
                            }
                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                    }
                }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                        Log.i("Message: ", "La carga está pausada");
                    }
                });

                new Thread(new Runnable() {
                    public void run() {
                        while (i < 100) {
                            i += 5;
                            // Update the progress bar and display the current value in text view
                            hdlr.post(new Runnable() {
                                public void run() {

                                    mProgressBar.setProgress(i);
                                    btnGuardar.setVisibility(View.GONE);
                                    btnCancelar.setVisibility(View.GONE);
                                    mProgressBar.setVisibility(View.VISIBLE);
                                    tiempoMensaje.setVisibility(View.VISIBLE);
                                    tiempoMensaje.setText("Un momento, por favor");


                                    Log.i("Message: ", i + "/" + mProgressBar.getMax());
                                    if (i == 100) {
                                        btnGuardar.setVisibility(View.VISIBLE);
                                        btnCancelar.setVisibility(View.VISIBLE);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            File imgPhto = new File(mCurrentPhotoPath);
            if (imgPhto.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgPhto.getAbsolutePath());
                imgPerroEvaluacion.setImageBitmap(myBitmap);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String fileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    @Override
    public void onClick(final View view) {

        switch (view.getId()) {

            case R.id.btnCancelar:
                finish();
                break;

            case R.id.imgBtnCameraEvaluacion:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.i("IOException", ex.getMessage());
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.fernandomoya.appproyectofinal.fileprovider",
                                photoFile);
                        imageURIEvaluacion = photoURI;

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
                break;
            default:
        }
    }

    public void validarPermisos() {
        if (ContextCompat.checkSelfPermission(MedicalEvaluationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MedicalEvaluationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MedicalEvaluationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MedicalEvaluationActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }


    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference(EVALUATION);
    }

    public boolean validarEvaluacion() {

        Validation validador = new Validation();
        if (validador.vacio(estadoInicial)) {
            estadoInicial.setError("Valoración inicial obligatoria.");
            estadoInicial.requestFocus();
            return true;
        }


        if (validador.vacio(edad)) {
            edad.setError("Edad obligatoria.");
            edad.requestFocus();
            return true;
        }


        if (rdbSi.isChecked() && validador.vacio(fracturas)) {

            fracturas.setError("Describa la fractura existente.");
            fracturas.requestFocus();
            return true;

        }


        if (!(rdbMacho.isChecked() ||
                rdbHembra.isChecked())) {
            tipo.setVisibility(View.VISIBLE);
            tipo.setError("Seleccione un tipo.");
            tipo.requestFocus();
            return true;
        }

        if (!(rdbAdulto.isChecked() ||
                rdbCachorro.isChecked())) {
            tipo.setVisibility(View.VISIBLE);
            tipo.setError("Seleccione un tipo.");
            tipo.requestFocus();
            return true;
        }

        if (!(rdbGrande.isChecked() ||
                rdbMediano.isChecked() ||
                rdbPequeno.isChecked())) {
            tipo.setVisibility(View.VISIBLE);
            tipo.setError("Seleccione un tamaño.");
            tipo.requestFocus();
            return true;
        }


        if (validador.vacio(tiempo)) {
            tiempo.setError("Tiempo de recuperación obligatorio.");
            tiempo.requestFocus();
            return true;
        }
        if (validador.vacio(observaciones)) {
            observaciones.setError("Observaciones obligatorias.");
            observaciones.requestFocus();
            return true;
        }

        return false;


    }


}