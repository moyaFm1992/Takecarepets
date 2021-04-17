package com.fernandomoya.appproyectofinal.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fernandomoya.appproyectofinal.CheckActivity;
import com.fernandomoya.appproyectofinal.ItemClickListener;
import com.fernandomoya.appproyectofinal.R;
import com.fernandomoya.appproyectofinal.adapter.ToAdoptAdapter;
import com.fernandomoya.appproyectofinal.model.Adoption;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.fernandomoya.appproyectofinal.model.Constant.ADOPTION;

public class ToAdoptListActivity extends AppCompatActivity implements ItemClickListener {

    private RecyclerView rwAdoptar;
    private List<Adoption> listaAdoptado;
    private ToAdoptAdapter adapterAdoptado;
    private String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_to_adopt);
        listaAdoptado = new ArrayList<>();
        rwAdoptar = findViewById(R.id.rvToAdopt);
        rwAdoptar.setLayoutManager(new LinearLayoutManager(this));
        rwAdoptar.setItemAnimator(new DefaultItemAnimator());
        DatabaseReference myRef;
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapterAdoptado = new ToAdoptAdapter(listaAdoptado, R.layout.row_recycler_to_adopt, this);
        rwAdoptar.setAdapter(adapterAdoptado);
        adapterAdoptado.setClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference().child(ADOPTION);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAdoptado.removeAll(listaAdoptado);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dch : ds.getChildren()) {
                        if (dch.child("estado").getValue(Boolean.class).equals(Boolean.FALSE)) {
                            Adoption adoption = dch.getValue(Adoption.class);
                            listaAdoptado.add(adoption);
                        }

                    }
                }
                adapterAdoptado.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view, int position) {
        Adoption adoption = listaAdoptado.get(position);
        Intent i = new Intent(this, CheckActivity.class);
        i.putExtra("nombres", adoption.getNombres());
        i.putExtra("telefono", adoption.getTelefono());
        i.putExtra("cedula", adoption.getCedula());
        i.putExtra("edad", adoption.getEdad());
        i.putExtra("email", adoption.getEmail());
        i.putExtra("ocupacion", adoption.getOcupacion());
        i.putExtra("direccion", adoption.getDireccion());
        i.putExtra("instruccion", adoption.getInstruccion());
        i.putExtra("tipoinmueble", adoption.getTipoInmueble());
        i.putExtra("m2", adoption.getM2());
        i.putExtra("propio", adoption.getPropio());
        i.putExtra("pregunta1", adoption.getPregunta1());
        i.putExtra("pregunta2", adoption.getPregunta2());
        i.putExtra("pregunta3", adoption.getPregunta3());
        i.putExtra("pregunta4", adoption.getPregunta4());
        i.putExtra("pregunta5", adoption.getValorAGastar());
        i.putExtra("pregunta6", adoption.getMascotaEnferma());
        i.putExtra("pregunta7", adoption.getVisitaMensual());
        i.putExtra("userId", adoption.getuId());
        startActivity(i);
    }
}