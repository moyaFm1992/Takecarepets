package com.fernandomoya.appproyectofinal;

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
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fernandomoya.appproyectofinal.list.ListActivity;
import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
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
import java.util.Locale;

import static com.fernandomoya.appproyectofinal.model.Constant.DOGS;
import static com.fernandomoya.appproyectofinal.model.Constant.MY_PERMISSIONS_REQUEST_READ_CONTACTS;
import static com.fernandomoya.appproyectofinal.model.Constant.PHOTO;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    static final int REQUEST_TAKE_PHOTO = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private ImageView imgFoto;
    private ImageButton imgBtnCamera;
    private ImageButton btnGuardar;
    private ImageButton btnListar;
    private ImageButton btnGrupo;
    private TextView descripcionP;
    private TextView tiempoMensaje;
    private Location newLocation;
    private ProgressBar mProgressBar;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private StorageTask mUploadImg;
    private Uri mImageURI;
    private String mCurrentPhotoPath;
    private String passengerID;
    private SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mProgressBar = findViewById(R.id.simpleProgressBar);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        imgFoto = findViewById(R.id.imgPerroRescatar);
        imgBtnCamera = findViewById(R.id.imgBtnCamera);
        btnGuardar = findViewById(R.id.imgBtnGuardar);
        btnListar = findViewById(R.id.imgBtnListar);
        btnGrupo = findViewById(R.id.imgBtnGrupoD);
        descripcionP = findViewById(R.id.txtDescripcion);
        tiempoMensaje = findViewById(R.id.tv);

        btnGuardar.setOnClickListener(this);
        btnGrupo.setOnClickListener(this);
        btnListar.setOnClickListener(this);
        imgBtnCamera.setOnClickListener(this);

        inicializarFirebase();
        validarPermisos();

    }


    @Override
    public void onClick(final View view) {
        descripcionP = findViewById(R.id.txtDescripcion);
        switch (view.getId()) {

            case R.id.imgBtnGrupoD:
                Intent intentGrupo = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intentGrupo);
                break;
            case R.id.imgBtnListar:
                Intent intentListar = new Intent(HomeActivity.this, ListActivity.class);
                startActivity(intentListar);
                break;
            case R.id.imgBtnCamera:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(this,
                                PHOTO,
                                photoFile);
                        mImageURI = photoURI;

                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                    }
                }
                break;
            case R.id.imgBtnGuardar:
                if (
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                }

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                newLocation = location;

                                final String descripcion = descripcionP.getText().toString();
                                final String userId = mDatabase.push().getKey();
                                if (mImageURI != null) {

                                    StorageReference fileReference = mStorageReference.child(System.currentTimeMillis()
                                            + "." + getFileExtension(mImageURI));
                                    mUploadImg = fileReference.putFile(mImageURI)
                                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                                    while (!uri.isComplete()) ;
                                                    final Uri url = uri.getResult();
                                                    if (newLocation != null) {
                                                        Perros perros = new Perros();
                                                        Date date = new Date();
                                                        Date newDate = new Date(date.getTime() + (604800000L * 2) + (24 * 60 * 60));
                                                        perros.setFechaRegistro(dt.format(newDate));
                                                        perros.setDescripcion(descripcion);
                                                        perros.setLatitud(newLocation.getLatitude());
                                                        perros.setLongitud(newLocation.getLongitude());
                                                        perros.setUrl(url.toString());
                                                        perros.setuId(userId);
                                                        mDatabase.child(DOGS).child(passengerID).child(userId).setValue(perros);
                                                    }

                                                }
                                            })
                                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                                    Log.i("Message: ", "La carga esta al " + progress + "%");

                                                    int currentprogress = (int) progress;
                                                    descripcionP.setFocusable(Boolean.FALSE);
                                                    mProgressBar.setVisibility(View.VISIBLE);
                                                    tiempoMensaje.setVisibility(View.VISIBLE);
                                                    tiempoMensaje.setText("Un momento, por favor");
                                                    mProgressBar.setProgress(currentprogress);

                                                    if (currentprogress == 100) {
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

                                }


                            }
                        });
                break;
            default:
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            File imgPhto = new File(mCurrentPhotoPath);
            if (imgPhto.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgPhto.getAbsolutePath());
                imgFoto.setImageBitmap(myBitmap);
            }
        }
    }

    private File createImageFile() throws IOException {
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

    public void validarPermisos() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000);
        }
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mStorageReference = FirebaseStorage.getInstance().getReference(DOGS);
    }


}

