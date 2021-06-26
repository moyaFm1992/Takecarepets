package com.fernandomoya.appproyectofinal.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fernandomoya.appproyectofinal.ItemClickListener;
import com.fernandomoya.appproyectofinal.R;
import com.fernandomoya.appproyectofinal.model.Perros;

import java.util.List;

public class AdapterApp extends RecyclerView.Adapter<AdapterApp.PerroViewHolder> {
    private final List<Perros> listPerros;
    private final int rowLayout;
    private final Context mContext;
    private ItemClickListener clickListener;

    public AdapterApp(List<Perros> listPerros, int rowLayout, Context mContext) {
        this.listPerros = listPerros;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
    }

    @Override
    public PerroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return (new PerroViewHolder(v));
    }

    @Override
    public void onBindViewHolder(PerroViewHolder holder, int position) {
        Perros perros = listPerros.get(position);
        Glide.with(mContext).load(perros.getUrl()).into(holder.imgPerro);
        holder.lstRvFecha.setText(perros.getFechaRegistro()!=null?"Fecha: " + perros.getFechaRegistro():"");
        holder.lstRvDescripcion.setText("Descripción: " + perros.getDescripcion());
        holder.lstRvLatitud.setText(perros.getLatitud() != null ? "Latitud: " + perros.getLatitud().toString() : "");
        holder.lstRvLongitud.setText(perros.getLongitud() != null ? "Longitud: " + perros.getLongitud().toString() : "");
    }

    @Override
    public int getItemCount() {
        return listPerros.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class PerroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lstRvDescripcion;
        TextView lstRvFecha;
        TextView lstRvLatitud;
        TextView lstRvLongitud;
        ImageView imgPerro;

        public PerroViewHolder(View itemView) {
            super(itemView);
            imgPerro = itemView.findViewById(R.id.dog_photo);
            lstRvFecha=itemView.findViewById(R.id.lstFecha);
            lstRvDescripcion = itemView.findViewById(R.id.lstDescripcion);
            lstRvLatitud = itemView.findViewById(R.id.lstLatitud);
            lstRvLongitud = itemView.findViewById(R.id.lstLongitud);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


