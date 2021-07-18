package com.example.examendoctores;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Alta_registro extends AppCompatActivity {
    private EditText numeroAlta, nombreAlta, localidadAlta, correoAlta;
    private String num, nomb, local, mail;
    private Validaciones objetoValidar;  //objeto de nuestra clase validaciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_registro);

        // Bundle bundle =getIntent().getExtras();  si hubiera parametros para recuperar
        //enlazo controles a la vista
        numeroAlta= (EditText) findViewById(R.id.et_Numero_Alta);
        nombreAlta= (EditText) findViewById(R.id.et_Nombre_Alta);
        localidadAlta = (EditText) findViewById(R.id.et_Localidad_Alta);
        correoAlta=(EditText) findViewById(R.id.et_Correo_Alta);

        objetoValidar= new Validaciones();



    }


    //02

    public void leerServicio(View view) {
        try {
            num = numeroAlta.getText().toString();
            nomb = nombreAlta.getText().toString();
            local= localidadAlta.getText().toString();
            mail = correoAlta.getText().toString();
            //validamos que los editext no esten vacíos
            if (!objetoValidar.Vacio(numeroAlta) && !objetoValidar.Vacio(nombreAlta)
                    && !objetoValidar.Vacio(localidadAlta) && !objetoValidar.Vacio(correoAlta))
            {
                //validamos que el campo numeroAlta tiene un valor string que puede castearse a numero
                if (!objetoValidar.isNumeric(num)) {
                    Toast.makeText(this, "Introduzca un número , por favor", Toast.LENGTH_SHORT).show();
                } else {
                    if (!objetoValidar.isEmail(mail)){
                        Toast.makeText(this, "Introduzca un correo válido , por favor", Toast.LENGTH_SHORT).show();
                    } else {
                        mostrarDialogoConfirmacion(view);
                        }

                }
            }

        } catch (Exception e) {
        // manage exceptions
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }



  //03

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urls[0]);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
                nameValuePairs.add(new BasicNameValuePair("numero", num));
                nameValuePairs.add(new BasicNameValuePair("nombre", nomb));
                nameValuePairs.add(new BasicNameValuePair("localidad", local));


                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
            return "Executed";
            // return recuperarContenido(urls[0]);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


        }
    }

    //04
    public void cerrarVentana(View view) {
        finish();
    }

    //05
    public void mostrarDialogoConfirmacion(View view) {
        DialogoConfirmacion confirmacion  = new DialogoConfirmacion();
        confirmacion .show(getFragmentManager(), "Cuadro confirmación");
    }

    //06
    public void accionAceptar() {

        String url = "https://webapidepartamentos20210716114825.azurewebsites.net/api/Departamentos";
        new HttpAsyncTask().execute(url);
        mensajePersonalizado("Insertando Registro, gracias");
    }

    //07
    public void accionCancelar() {
        mensajePersonalizado("Cancelando Envio");
    }

    //08 creación del mensaje personalizado Toast según sea aceptado o cancelado

    public void mensajePersonalizado(String opcion) {
        Toast mensaje = new Toast(getApplicationContext());

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.capa_toast,
                (ViewGroup) findViewById(R.id.lytLayout));

        TextView txtMsg = (TextView)layout.findViewById(R.id.txtMensaje);
        txtMsg.setText(opcion);

        mensaje.setDuration(Toast.LENGTH_SHORT);
        mensaje.setView(layout);
        mensaje.show();
    }
}