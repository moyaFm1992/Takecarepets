package com.fernandomoya.appproyectofinal.model;


public class Perros {

    private String url;
    private String descripcion;
    private Double latitud;
    private Double longitud;

    public Perros() {
    }

    public Perros(String url, String descripcion, Double latitud, Double longitud) {
        this.url = url;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
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


}
