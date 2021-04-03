package com.fernandomoya.appproyectofinal.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.fernandomoya.appproyectofinal.ItemClickListener;
import com.fernandomoya.appproyectofinal.R;
import com.fernandomoya.appproyectofinal.model.Adoption;

import java.util.List;

public class ToAdoptedAdapter extends RecyclerView.Adapter<ToAdoptedAdapter.ToAdoptedViewHolder> {
    final List<Adoption> listAdoption;
    final int rowLayout;
    final Context mContext;
    ItemClickListener clickListener;

    public ToAdoptedAdapter(List<Adoption> listAdoption, int rowLayout, Context mContext) {
        this.listAdoption = listAdoption;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
    }

    @Override
    public ToAdoptedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return  (new ToAdoptedViewHolder(v));
    }

    @Override
    public void onBindViewHolder(ToAdoptedViewHolder holder, int position) {
        Adoption adoption = listAdoption.get(position);
        Glide.with(mContext).load(adoption.getUrl()).into(holder.imgPerro);
        holder.lstRvAdoptante.setText("Adoptante: " + adoption.getNombresApellidos());
        holder.lstRvCedula.setText("Cedula : " + adoption.getCedula());
        holder.lstRvEdad.setText("Edad : " + adoption.getEdad());
        holder.lstRvDireccion.setText("Dirección : " + adoption.getDireccion());
        Boolean b = adoption.getEstado();
        if (Boolean.TRUE.equals(b)) {
            holder.lstRvEstado.setText("Adoptado");

        } else {

            holder.lstRvEstado.setText("En proceso de adopción");
        }

    }

    @Override
    public int getItemCount() {
        return listAdoption.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ToAdoptedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lstRvAdoptante;
        TextView lstRvEdad;
        TextView lstRvCedula;
        TextView lstRvDireccion;
        TextView lstRvEstado;
        ImageView imgPerro;

        public ToAdoptedViewHolder(View itemView) {
            super(itemView);
            imgPerro = itemView.findViewById(R.id.imgListPerroAdoptado);
            lstRvAdoptante = itemView.findViewById(R.id.lstNombreAdoptante);
            lstRvEdad = itemView.findViewById(R.id.lstEdadAdoptante);
            lstRvCedula = itemView.findViewById(R.id.lstCedulaAdoptante);
            lstRvDireccion = itemView.findViewById(R.id.lstDireccionAdoptante);
            lstRvEstado = itemView.findViewById(R.id.lstEstado);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


