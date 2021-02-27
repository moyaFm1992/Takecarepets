package com.fernandomoya.appproyectofinal.model;


public class Adoption {

    private String nombresApellidos;
    private String cedula;
    private String telefono;
    private String edad;
    private String email;
    private String direccion;
    private String ocupacion;
    private String nombresApellidosReferencia;
    private String telefonoReferencia;
    private String parentesco;
    private String instruccion;
    private String tipoInmueble;
    private String tipo;
    private String m2;
    private String propio;
    private String pregunta1;
    private String pregunta2;
    private String pregunta3;
    private String pregunta4;
    private String valorAGastar;
    private String mascotaEnferma;
    private String visitaMensual;
    private Boolean estado;
    private String url;
    private String fechaRegistro;
    private String fechaAdopcion;


    public Adoption() {

    }

    public Adoption(String nombresApellidos, String cedula, String telefono, String edad, String email, String direccion, String ocupacion) {
        this.nombresApellidos = nombresApellidos;
        this.cedula = cedula;
        this.telefono = telefono;
        this.edad = edad;
        this.email = email;
        this.direccion = direccion;
        this.ocupacion = ocupacion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombresApellidos() {
        return nombresApellidos;
    }

    public void setNombresApellidos(String nombresApellidos) {
        this.nombresApellidos = nombresApellidos;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombresApellidosReferencia() {
        return nombresApellidosReferencia;
    }

    public void setNombresApellidosReferencia(String nombresApellidosReferencia) {
        this.nombresApellidosReferencia = nombresApellidosReferencia;
    }

    public String getTelefonoReferencia() {
        return telefonoReferencia;
    }

    public void setTelefonoReferencia(String telefonoReferencia) {
        this.telefonoReferencia = telefonoReferencia;
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
    }

    public String getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(String tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getM2() {
        return m2;
    }

    public void setM2(String m2) {
        this.m2 = m2;
    }

    public String getPregunta1() {
        return pregunta1;
    }

    public void setPregunta1(String pregunta1) {
        this.pregunta1 = pregunta1;
    }

    public String getPregunta2() {
        return pregunta2;
    }

    public void setPregunta2(String pregunta2) {
        this.pregunta2 = pregunta2;
    }

    public String getPregunta3() {
        return pregunta3;
    }

    public void setPregunta3(String pregunta3) {
        this.pregunta3 = pregunta3;
    }

    public String getPregunta4() {
        return pregunta4;
    }

    public void setPregunta4(String pregunta4) {
        this.pregunta4 = pregunta4;
    }

    public String getValorAGastar() {
        return valorAGastar;
    }

    public void setValorAGastar(String valorAGastar) {
        this.valorAGastar = valorAGastar;
    }

    public String getMascotaEnferma() {
        return mascotaEnferma;
    }

    public void setMascotaEnferma(String mascotaEnferma) {
        this.mascotaEnferma = mascotaEnferma;
    }

    public String getVisitaMensual() {
        return visitaMensual;
    }

    public void setVisitaMensual(String visitaMensual) {
        this.visitaMensual = visitaMensual;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFechaAdopcion() {
        return fechaAdopcion;
    }

    public void setFechaAdopcion(String fechaAdopcion) {
        this.fechaAdopcion = fechaAdopcion;
    }


    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getPropio() {
        return propio;
    }

    public void setPropio(String propio) {
        this.propio = propio;
    }
}
