package com.fernandomoya.appproyectofinal.model;

public class CheckAdoption {

    private String checkParteI;
    private String checkParteII;
    private String checkParteIII;
    private String checkParteIV;


    public CheckAdoption() {

    }


    public CheckAdoption(String checkParteI, String checkParteII, String checkParteIII, String checkParteIV) {
        this.checkParteI = checkParteI;
        this.checkParteII = checkParteII;
        this.checkParteIII = checkParteIII;
        this.checkParteIV = checkParteIV;
    }

    public String getCheckParteI() {
        return checkParteI;
    }

    public void setCheckParteI(String checkParteI) {
        this.checkParteI = checkParteI;
    }

    public String getCheckParteII() {
        return checkParteII;
    }

    public void setCheckParteII(String checkParteII) {
        this.checkParteII = checkParteII;
    }

    public String getCheckParteIII() {
        return checkParteIII;
    }

    public void setCheckParteIII(String checkParteIII) {
        this.checkParteIII = checkParteIII;
    }

    public String getCheckParteIV() {
        return checkParteIV;
    }

    public void setCheckParteIV(String checkParteIV) {
        this.checkParteIV = checkParteIV;
    }


}
