package com.fernandomoya.appproyectofinal;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fernandomoya.appproyectofinal.model.AdapterApp;
import com.fernandomoya.appproyectofinal.model.Perros;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements ItemClickListener {

    RecyclerView rw;
    List<Perros> listaPerros;
    AdapterApp adapterApp;
    String passengerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_activity);

        listaPerros = new ArrayList<>();
        rw = findViewById(R.id.recycleV);
        rw.setLayoutManager(new LinearLayoutManager(this));
        rw.setItemAnimator(new DefaultItemAnimator());
        DatabaseReference myRef;
        passengerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        adapterApp = new AdapterApp(listaPerros, R.layout.row_recycler, this);
        rw.setAdapter(adapterApp);
        adapterApp.setClickListener(this);

        myRef = FirebaseDatabase.getInstance().getReference().child("perros").child(passengerID);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Perros perros = ds.getValue(Perros.class);
                    listaPerros.add(perros);
                }


                adapterApp.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("databaseError", databaseError.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view, int position) {
        Perros perros = listaPerros.get(position);
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("descripcion", perros.getDescripcion());
        i.putExtra("latitud", perros.getLatitud().toString());
        i.putExtra("longitud", perros.getLongitud().toString());
        startActivity(i);
    }
}
