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
import com.fernandomoya.appproyectofinal.model.Adoption;

import java.util.List;

public class ToAdoptAdapter extends RecyclerView.Adapter<ToAdoptAdapter.ToAdoptViewHolder> {
    final List<Adoption> listAdoption;
    final int rowLayout;
    final Context mContext;
    ItemClickListener clickListener;

    public ToAdoptAdapter(List<Adoption> listAdoption, int rowLayout, Context mContext) {
        this.listAdoption = listAdoption;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
    }

    @Override
    public ToAdoptViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return  (new ToAdoptViewHolder(v));
    }

    @Override
    public void onBindViewHolder(ToAdoptViewHolder holder, int position) {
        Adoption adoption = listAdoption.get(position);
        Glide.with(mContext).load(adoption.getUrl()).into(holder.imgPerro);
        holder.lstRvAdoptante.setText("Adoptante: " + adoption.getNombres());
        holder.lstRvTelefono.setText("Teléfono : " + adoption.getTelefono());
        holder.lstRvDireccion.setText("Dirección : " + adoption.getDireccion());
        holder.lstRvSolicitud.setText("Fecha de solicitud : " + adoption.getFechaRegistro());

        Boolean b = adoption.getEstado();
        if (Boolean.TRUE.equals(b)) {
            holder.lstRvProceso.setText("Adoptado");

        } else {

            holder.lstRvProceso.setText("En proceso de adopción");
        }


    }

    @Override
    public int getItemCount() {
        return listAdoption.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ToAdoptViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lstRvAdoptante;
        TextView lstRvTelefono;
        TextView lstRvSolicitud;
        TextView lstRvProceso;
        TextView lstRvDireccion;
        ImageView imgPerro;

        public ToAdoptViewHolder(View itemView) {
            super(itemView);
            imgPerro = itemView.findViewById(R.id.imgListPerroAdoptar);
            lstRvProceso = itemView.findViewById(R.id.lstEstadoAdoptar);
            lstRvAdoptante = itemView.findViewById(R.id.lstAdoptante);
            lstRvTelefono = itemView.findViewById(R.id.lstTelefono);
            lstRvSolicitud = itemView.findViewById(R.id.lstSolicitud);
            lstRvDireccion = itemView.findViewById(R.id.lstDireccion);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


