package com.fernandomoya.appproyectofinal.model;

public class User {
    private Boolean isAdmin;

    private Boolean isVeterinary;

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getVeterinary() {
        return isVeterinary;
    }

    public void setVeterinary(Boolean veterinary) {
        isVeterinary = veterinary;
    }
}
