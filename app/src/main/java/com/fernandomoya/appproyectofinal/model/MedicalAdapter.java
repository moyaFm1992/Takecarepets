package com.fernandomoya.appproyectofinal.model;


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

import java.util.List;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MedicalViewHolder> {
    final List<MedicalEvaluation> listValoracion;
    final int rowLayout;
    final Context mContext;
    ItemClickListener clickListener;

    public MedicalAdapter(List<MedicalEvaluation> listValoracion, int rowLayout, Context mContext) {
        this.listValoracion = listValoracion;
        this.rowLayout = rowLayout;
        this.mContext = mContext;
    }

    @Override
    public MedicalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return (new MedicalViewHolder(v));
    }

    @Override
    public void onBindViewHolder(MedicalViewHolder holder, int position) {
        MedicalEvaluation valoracion = listValoracion.get(position);
        Glide.with(mContext).load(valoracion.getUrl()).into(holder.imgPerroValoracion);
        holder.lstRvEdad.setText("Edad: " + valoracion.getEdad() + " años");
        holder.lstRvSexo.setText("Sexo: " + valoracion.getSexo());
        holder.lstRvTamano.setText("Tamaño: " + valoracion.getTamano());
        holder.lstRvTipo.setText("Tipo: " + valoracion.getTipo());
        holder.lstRvFracturas.setText("Fracturas: " + valoracion.getFracturas());


    }

    @Override
    public int getItemCount() {
        return listValoracion.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MedicalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lstRvEdad;
        TextView lstRvSexo;
        TextView lstRvTamano;
        TextView lstRvTipo;
        TextView lstRvFracturas;
        ImageView imgPerroValoracion;

        public MedicalViewHolder(View itemView) {
            super(itemView);
            imgPerroValoracion = itemView.findViewById(R.id.imgListPerroEvaluacion);
            lstRvEdad = itemView.findViewById(R.id.lstEdad);
            lstRvSexo = itemView.findViewById(R.id.lstSexo);
            lstRvTamano = itemView.findViewById(R.id.lstTamano);
            lstRvTipo = itemView.findViewById(R.id.lstTipo);
            lstRvFracturas = itemView.findViewById(R.id.lstFracturas);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


