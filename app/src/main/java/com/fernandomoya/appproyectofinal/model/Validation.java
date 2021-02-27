package com.fernandomoya.appproyectofinal.model;


import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Validation {
    //Metodo para validar si es un valor numerico
    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    //Metodo para validar si es un email
    public boolean isValidEmailId(String email) {

        boolean isValid = false;
        String expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    //Metodo para validar cedula
    public boolean isValid(String cedula) {
        boolean cedulaCorrecta = false;

        try {

            if (cedula.length() == 10) {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else cedulaCorrecta = (10 - (suma % 10)) == verificador;
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            Log.i("Exception", "Una excepción ocurrio en el proceso de validación");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            Log.i("Exception", "La cédula ingresada es incorrecta");
        }
        return cedulaCorrecta;
    }

    //Metodo para validar si el campo esta vacio

    public boolean vacio(EditText campo) {
        String dato = campo.getText().toString().trim();
        return TextUtils.isEmpty(dato);
    }

}
