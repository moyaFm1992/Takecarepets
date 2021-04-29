package com.fernandomoya.appproyectofinal.model;

import android.os.StrictMode;
import android.telephony.SmsManager;
import android.util.Log;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Send {

    Session session;

    //Envio de correo electronico
    public void enviar(final String correo, final String mensaje) {
        final String correoPruebas = "takecarepetsapp@gmail.com";
        final String passPruebas = "****";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Properties properties = new Properties();


        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.host", "smtp.googlemail.com");
        properties.put("mail.smtp.timeout", 1000);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");

        try {
            session = Session.getDefaultInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correoPruebas, passPruebas);
                }
            });
            if (session != null) {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("Takecarepetsapp"));
                message.setSubject("Proceso de Adopción");
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo));
                message.setContent(mensaje, "text/html;  charset=utf-8");
                Transport.send(message);
            }
        } catch (Exception e) {
            Log.i("Exception ",e.getMessage());
        }
    }


}
