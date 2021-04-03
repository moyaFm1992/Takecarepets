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
import com.fernandomoya.appproyectofinal.model.MedicalEvaluation;

import java.util.List;

public class MedicalListAdapter extends RecyclerView.Adapter<MedicalListAdapter.MedicalViewHolder> {
    final List<MedicalEvaluation> listMedicalEvaluation;
    final int rowLayout;
    final Context mContext;
    ItemClickListener clickListener;

    public MedicalListAdapter(List<MedicalEvaluation> listMedicalEvaluation, int rowLayout, Context mContext) {
        this.listMedicalEvaluation = listMedicalEvaluation;
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
        MedicalEvaluation medicalEvaluation = listMedicalEvaluation.get(position);
        Glide.with(mContext).load(medicalEvaluation.getUrl()).into(holder.imgListPerroValoracion);
        holder.lstRvEstado.setText("Valoración: "+medicalEvaluation.getEstadoInicial());
        holder.lstRvEdad.setText("Edad: " + medicalEvaluation.getEdad() + " años");
        holder.lstRvSexo.setText("Sexo: " + medicalEvaluation.getSexo());
        holder.lstRvObservaciones.setText(medicalEvaluation.getObservaciones());
        holder.lstRvTiempo.setText("Recuperación en :" + medicalEvaluation.getTiempoRecuperacion() + " días");
        holder.lstRvFracturas.setText("Fracturas: " + medicalEvaluation.getFracturas());

    }

    @Override
    public int getItemCount() {
        return listMedicalEvaluation.size();
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class MedicalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lstRvEstado;
        TextView lstRvEdad;
        TextView lstRvSexo;
        TextView lstRvObservaciones;
        TextView lstRvFracturas;
        TextView lstRvTiempo;
        ImageView imgListPerroValoracion;


        public MedicalViewHolder(View itemView) {
            super(itemView);
            imgListPerroValoracion = itemView.findViewById(R.id.imgListPerroEvaluado);
            lstRvEstado = itemView.findViewById(R.id.lstEstadoListEvaluacion);
            lstRvEdad = itemView.findViewById(R.id.lstEdadListEvaluacion);
            lstRvSexo = itemView.findViewById(R.id.lstSexoListEvaluacion);
            lstRvObservaciones = itemView.findViewById(R.id.lstObservacionesListEvaluacion);
            lstRvFracturas = itemView.findViewById(R.id.lstFracturasListEvaluacion);
            lstRvTiempo = itemView.findViewById(R.id.lstTiempoListEvaluacion);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
        }
    }
}


