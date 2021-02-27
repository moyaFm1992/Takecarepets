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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.fernandomoya.appproyectofinal.HomeActivity.REQUEST_TAKE_PHOTO;
import static com.fernandomoya.appproyectofinal.model.Constant.MY_PERMISSIONS_REQUEST_READ_CONTACTS;

public class MedicalEvaluationActivity extends AppCompatActivity implements View.OnClickListener {
    DatabaseReference mDatabase;
    EditText estadoInicial;
    EditText edad;
    EditText fracturas;
    EditText observaciones;
    EditText medicamentos;
    EditText tiempo;
    EditText tipo;
    RadioButton rdbMacho;
    RadioButton rdbHembra;
    RadioButton rdbAdulto;
    RadioButton rdbCachorro;
    RadioButton rdbGrande;
    RadioButton rdbMediano;
    RadioButton rdbPequeno;
    RadioButton rdbSi;
    RadioButton rdbNo;
    CheckBox cbxDrontal;
    CheckBox cbxRimadyltal;
    CheckBox cbxPipeta;
    CheckBox cbxEmulgel;
    CheckBox cbxMeloxic;
    CheckBox cbxBrillo;
    CheckBox cbxCoccigan;
    CheckBox cbxSuero;
    CheckBox cbxAdoptable;
    Button btnGuardar;
    FirebaseDatabase firebaseDatabase;
    ImageView imgPerroEvaluacion;
    ImageButton imgBtnCameraEvaluacion;
    Uri imageURIEvaluacion;
    StorageReference mStorageReference;
    StorageTask mUploadImg;
    String mCurrentPhotoPath;
    TextView tiempoMensaje;
    ProgressBar mProgressBar;
    String passengerID;
    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_evaluation);
        imgPerroEvaluacion = findViewById(R.id.imgPerroEvaluacion);
        imgBtnCameraEvaluacion = findViewById(R.id.imgBtnCameraEvaluacion);
        estadoInicial = findViewById(R.id.eTMLEstadoInicial);
        edad = findViewById(R.id.txtEdadEvaluacion);
        edad.setInputType(InputType.TYPE_CLASS_NUMBER);
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
        btnGuardar = findViewById(R.id.btnGuardarEvaluacion);
        tipo = findViewById(R.id.txtTipo);
        tiempoMensaje = findViewById(R.id.tv);
        mProgressBar = findViewById(R.id.simpleProgressBar);
        imgBtnCameraEvaluacion.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(MedicalEvaluationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MedicalEvaluationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MedicalEvaluationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }

        inicializarFirebase();

    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference("valoracion");
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

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    @Override
    public void onClick(final View view) {

        switch (view.getId()) {

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

            case R.id.btnGuardarEvaluacion:


                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MedicalEvaluationActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }

                if (imageURIEvaluacion == null) {
                    Toast.makeText(MedicalEvaluationActivity.this, "La fotografía es necesaria", Toast.LENGTH_SHORT).show();
                    return;
                }


                StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                        + "." + getFileExtension(imageURIEvaluacion));
                mUploadImg = fileReference.putFile(imageURIEvaluacion)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                MedicalEvaluation v = new MedicalEvaluation();
                                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uri.isComplete()) ;
                                Uri url = uri.getResult();

                                final String estado = estadoInicial.getText().toString();
                                final String tieneObservaciones = observaciones.getText().toString();
                                final String medicamentosSum = medicamentos.getText().toString();
                                final List<String> listaMedicamentos = new LinkedList<>();
                                final String tieneEdad = edad.getText().toString();
                                final String tiempoRecu = tiempo.getText().toString();
                                final String tieneFracturas = fracturas.getText().toString();

                                if (rdbMacho.isChecked()) {
                                    v.setSexo(rdbMacho.getText().toString());
                                }
                                if (rdbHembra.isChecked()) {
                                    v.setSexo(rdbHembra.getText().toString());
                                }

                                if (rdbAdulto.isChecked()) {
                                    v.setTipo(rdbAdulto.getText().toString());
                                }
                                if (rdbCachorro.isChecked()) {
                                    v.setTipo(rdbCachorro.getText().toString());
                                }

                                if (rdbGrande.isChecked()) {
                                    v.setTamano(rdbGrande.getText().toString());
                                }

                                if (rdbMediano.isChecked()) {
                                    v.setTamano(rdbMediano.getText().toString());
                                }

                                if (rdbPequeno.isChecked()) {
                                    v.setTamano(rdbPequeno.getText().toString());
                                }

                                if (rdbNo.isChecked()) {
                                    v.setFracturasSiNo(rdbNo.getText().toString());
                                    v.setFracturas("Sin fracturas");
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

                                if (medicamentosSum != null) {
                                    listaMedicamentos.add(medicamentosSum);
                                } else {
                                    listaMedicamentos.add("");
                                }

                                StringBuilder  medicamentosSuministrados = new StringBuilder ();
                                for (String med : listaMedicamentos) {
                                    medicamentosSuministrados.append(med);
                                }
                                String med = medicamentosSuministrados.toString();

                                if (cbxAdoptable.isChecked()) {
                                    v.setAdoptable(Boolean.TRUE);
                                } else {
                                    v.setAdoptable(Boolean.FALSE);
                                }

                                v.setMedicamentos(med);
                                v.setTiempoRecuperacion(tiempoRecu);
                                v.setFracturasSiNo(rdbSi.getText().toString());
                                v.setFracturas(tieneFracturas);
                                v.setEstadoInicial(estado);
                                v.setEdad(tieneEdad);
                                v.setObservaciones(tieneObservaciones);
                                v.setTiempoRecuperacion(tiempoRecu);
                                v.setUrl(url.toString());
                                v.setFechaValoracion(dt.format(new Date()));

                                if (validarEvaluacion()) {
                                    return;
                                }
                                mDatabase.child("valoracion").child(passengerID).push().setValue(v);
                            }


                        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                Log.i("Message: ", "La carga esta al" + progress + "%");

                                int currentprogress = (int) progress;
                                btnGuardar.setVisibility(View.GONE);
                                mProgressBar.setVisibility(View.VISIBLE);
                                tiempoMensaje.setVisibility(View.VISIBLE);
                                tiempoMensaje.setText("Un momento, por favor");
                                mProgressBar.setProgress(currentprogress);

                                if (currentprogress == 100) {
                                    btnGuardar.setVisibility(View.VISIBLE);
                                    mProgressBar.setVisibility(View.GONE);
                                    tiempoMensaje.setVisibility(View.GONE);
                                    finish();
                                }
                            }
                        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {

                                Log.i("Message: ", "La carga está pausada");
                            }
                        });
                break;
            default:
        }
    }

    public boolean validarEvaluacion() {
        Validation validador = new Validation();
        if (validador.vacio(estadoInicial)) {
            estadoInicial.setError("La valoración inicial es obligatoria.");
            estadoInicial.requestFocus();
            return true;
        }


        if (validador.vacio(edad)) {
            edad.setError("La edad es obligatoria.");
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
            tipo.setError("Seleccione el tipo.");
            tipo.requestFocus();
            return true;
        }

        if (!(rdbAdulto.isChecked() ||
                rdbCachorro.isChecked())) {
            tipo.setError("Seleccione la edad.");
            tipo.requestFocus();
            return true;
        }

        if (!(rdbGrande.isChecked() ||
                rdbMediano.isChecked() ||
                rdbPequeno.isChecked())) {
            tipo.setError("Seleccione el tamaño.");
            tipo.requestFocus();
            return true;
        }


        if (validador.vacio(tiempo)) {
            tiempo.setError("Tiempo de recuperación es obligatorio.");
            tiempo.requestFocus();
            return true;
        }
        if (validador.vacio(observaciones)) {
            observaciones.setError("Las observaciones son obligatorias.");
            observaciones.requestFocus();
            return true;
        }

        return false;
    }


}



