package com.fernandomoya.appproyectofinal.model;


public class Perros {

    private String url;
    private String descripcion;
    private Double latitud;
    private Double longitud;
    private String uId;
    private String fechaRegistro;

    public Perros() {
    }

    public Perros(String url, String descripcion, Double latitud, Double longitud,String uId) {
        this.url = url;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.uId = uId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String toString() {
        return descripcion + " " + latitud + " " + longitud;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
