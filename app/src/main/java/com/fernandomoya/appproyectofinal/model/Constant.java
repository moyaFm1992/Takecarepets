package com.fernandomoya.appproyectofinal.model;

public  class Constant {

    private Constant() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SMS_EMAIL = "<p>Le informamos que ha iniciado el proceso de adopción, en los próximos días un técnico se comunicará para agendar una visita técnica.</p>\n" +
            "<p>Si no los solicitaste, ignora este correo electrónico.</p>\n" +
            "<p>Gracias,</p>\n" +
            "<p>Takecarepetsapp team</p>";
    public static final String APROVADO = "<p>Le informamos que su solicitud de adopción ha sido aceptada.</p>\n" +
            "<p>Si no lo solicitaste, ignora este correo electrónico.</p>\n" +
            "<p>Gracias,</p>\n" +
            "<p>Takecarepetsapp team</p>";
    public static final String NEGADO = "<p>Le informamos que su solicitud de adopción ha sido rechazada.</p>\n" +
            "<p>Si no lo solicitaste, ignora este correo electrónico.</p>\n" +
            "<p>Gracias,</p>\n" +
            "<p>Takecarepetsapp team</p>";
    public static final String SALUDO = "Estimado ";
    public static final String ADMIN = "9WBx76PIkrh6Ww8QLwjGKFKsNXF3";
    public static final String PHOTO = "com.fernandomoya.appproyectofinal.fileprovider";
    public static final String VETER = "b6q93u9z1lcRiJn19nfcJpKewUf2";
    public static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS= 100;
    public static final int REQUEST_IMAGE_CAPTURE = 1;

    public static final String DOGS = "perros";
    public static final String ADOPTION = "adopcion";
    public static final String EVALUATION = "valoracion";


}
