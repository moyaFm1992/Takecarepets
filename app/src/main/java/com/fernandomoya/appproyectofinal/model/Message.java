package com.fernandomoya.appproyectofinal.model;

import static com.fernandomoya.appproyectofinal.model.Constant.APROVADO;
import static com.fernandomoya.appproyectofinal.model.Constant.NEGADO;
import static com.fernandomoya.appproyectofinal.model.Constant.APROVADO_SMS;
import static com.fernandomoya.appproyectofinal.model.Constant.NEGADO_SMS;
import static com.fernandomoya.appproyectofinal.model.Constant.SMS_EMAIL;

public class Message {


    public String sms() {

        return SMS_EMAIL;
    }

    public String email() {

        return SMS_EMAIL;
    }

    public String emailNegado() {
        return NEGADO;
    }

    public String smsNegado() {
        return NEGADO_SMS;
    }

    public String emailAceptado() {

        return APROVADO;
    }

    public String smsAceptado() {

        return APROVADO_SMS;
    }

}
