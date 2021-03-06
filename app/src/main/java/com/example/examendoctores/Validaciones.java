package com.example.examendoctores;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

public class Validaciones {

    // 01  metodo para validar si es un valor numérico
    public boolean isNumeric(String cadena) {
        boolean resultado;
        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }
        return resultado;
    }

    //02 metodo para validar si es un email
    public boolean isEmail(String cadena) {
        boolean resultado;
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            resultado = true;
        } else {
        resultado = false;
        }
        return resultado;
    }

    //metodo para validar si el editext esta vacio
    public boolean Vacio(EditText campo){
        String dato = campo.getText().toString().trim();
        if(TextUtils.isEmpty(dato)){
            campo.setError("Campo requerido");
            campo.requestFocus();
            return true;
        }
        else {
            return false;
        }
    }

}
