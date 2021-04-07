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
import com.fernandomoya.appproyectofinal.UpdateDeleteAdoptedActivity;
import com.fernandomoya.appproyectofinal.model.Adoption;
import com.fernandomoya.appproyectofinal.adapter.ToAdoptedAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ToAdoptedListActivity extends AppCompatActivity implements ItemClickListener {
    private RecyclerView rwAdoptado;
    private List<Adoption> listaAdoptado;
    private ToAdoptedAdapter adapterAdoptado;
    private DatabaseReference myRef;
    private String passengerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_to_adopted_list);
        listaAdoptado = new ArrayList<>();
        rwAdoptado = findViewById(R.id.rvToAdoptedList);
        rwAdoptado.setLayoutManager(new LinearLayoutManager(this));
        rwAdoptado.setItemAnimator(new DefaultItemAnimator());
        adapterAdoptado = new ToAdoptedAdapter(listaAdoptado, R.layout.row_recycler_to_adopted_list, this);
        rwAdoptado.setAdapter(adapterAdoptado);
        adapterAdoptado.setClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference().child("adopcion");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaAdoptado.removeAll(listaAdoptado);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    for (DataSnapshot dch : ds.getChildren()) {
                        Adoption adoption = dch.getValue(Adoption.class);
                        listaAdoptado.add(adoption);
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
        Intent i = new Intent(this, UpdateDeleteAdoptedActivity.class);
        i.putExtra("direccion", "Direccion: "+adoption.getDireccion());
        i.putExtra("nombres", "Adoptante: "+adoption.getNombresApellidos());
        i.putExtra("cedula", "Cedula: "+adoption.getCedula());
        i.putExtra("edad", "Edad: "+adoption.getEdad());
        i.putExtra("estado", adoption.getEstado().toString());
        i.putExtra("url", adoption.getUrl());
        i.putExtra("userId", adoption.getuId());
        startActivity(i);


    }
}