package com.fernandomoya.appproyectofinal.model;

public class MedicalEvaluation {

    private String estadoInicial;
    private String edad;
    private String tipo;
    private String sexo;
    private String tamano;
    private String fracturas;
    private String fracturasSiNo;
    private String observaciones;
    private String medicamentos;
    private String vacunas;
    private String url;
    private String tiempoRecuperacion;
    private Boolean adoptable;
    private String fechaValoracion;
    private String uId;

    public MedicalEvaluation() {

    }

    public MedicalEvaluation(String edad, String tipo, String sexo, String tamano, String fracturas, String fracturasSiNo, String medicamentos,String uId) {
        this.edad = edad;
        this.tipo = tipo;
        this.sexo = sexo;
        this.tamano = tamano;
        this.fracturas = fracturas;
        this.fracturasSiNo = fracturasSiNo;
        this.medicamentos = medicamentos;
        this.uId = uId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public String getFracturas() {
        return fracturas;
    }

    public void setFracturas(String fracturas) {
        this.fracturas = fracturas;
    }

    public String getFracturasSiNo() {
        return fracturasSiNo;
    }

    public void setFracturasSiNo(String fracturasSiNo) {
        this.fracturasSiNo = fracturasSiNo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean getAdoptable() {
        return adoptable;
    }

    public void setAdoptable(Boolean adoptable) {
        this.adoptable = adoptable;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }


    public String getTiempoRecuperacion() {
        return tiempoRecuperacion;
    }

    public void setTiempoRecuperacion(String tiempoRecuperacion) {
        this.tiempoRecuperacion = tiempoRecuperacion;
    }

    public String getFechaValoracion() {
        return fechaValoracion;
    }

    public void setFechaValoracion(String fechaValoracion) {
        this.fechaValoracion = fechaValoracion;
    }


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getVacunas() {
        return vacunas;
    }

    public void setVacunas(String vacunas) {
        this.vacunas = vacunas;
    }
}
