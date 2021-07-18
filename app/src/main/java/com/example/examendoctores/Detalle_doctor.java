package com.example.examendoctores;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Detalle_doctor extends AppCompatActivity {
    private TextView codhosp, iddoctor, ;
    private Button botonRegresar;
    private static final String TAG= MainActivity.class.getSimpleName();

    //01
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_departamento);
        this.num = (TextView) findViewById(R.id.tv_lbl_Numero);
        this.nom =(TextView) findViewById(R.id.tv_lbl_Nombre);
        this.loc = (TextView) findViewById(R.id.tv_lbl_localidad);

        //Recogemos los parámetros enviados por el primer Activity a través del método getExras
        Bundle bundle = getIntent().getExtras();
        String idedept = bundle.getString( "NUMERODEPARTAMENTO");
        Log.i(TAG, "onCreate Detalle Departamento  : " + idedept);
        leerservicio(idedept);
    }

    //02
    public void cerrarVentana(View view) {
        finish();
    }

    //03
    public void leerservicio(String idedept) {
        try {
            String urlbase = "https://webapidepartamentos20210716114825.azurewebsites.net/api/Departamentos/";
            String url = urlbase + idedept;
            Log.i(TAG, "La url de acceso a los servicios web Restfull es : "+url);
            new Detalle_departamento.HttpAsyncTask().execute(url);

        } catch (Exception e){
            //manage exceptions
            System.out.println(e.toString());
            System.out.println("Error leyendo del Web Api RestFull");

        }
    }

    //04
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            return recuperarContenido(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Datos recibidos!", Toast.LENGTH_SHORT).show();
            try {
                JSONObject objetojson = new JSONObject (result);
                Departamentos departamento = new Departamentos();
                departamento = convertirJsonObjectDepartamentos(objetojson);
                num.setText(departamento.getNumero());
                nom.setText(departamento.getNombre());
                loc.setText(departamento.getLocalidad());

            } catch (JSONException e) {
                System.out.println(e.toString());
                System.out.println("onPostExecute");
            }

        }
    }

        //05
        public String recuperarContenido (String url) {
            HttpClient httpclient = new DefaultHttpClient();
            String resultado = null;
            HttpGet httpget = new HttpGet(url);
            HttpResponse respuesta = null;
            InputStream stream = null;
            try {
                respuesta = httpclient.execute(httpget);
                HttpEntity entity = respuesta.getEntity();
                if (entity != null ){
                    stream= entity.getContent();
                    resultado = convertirInputToString(stream);
                }
            } catch (Exception e){
                System.out.println( e.toString());
                System.out.println(" en método recuperancontenido(url");
            } finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                } catch (Exception e) {
                    System.out.println(e.toString());
                }
            }
            System.out.println("Se captur{o lo siguiente " + resultado);
            return resultado;
        }

        //06
        private String convertirInputToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line= "";
            String resultado = "";
            while ((line = bufferedReader.readLine()) != null)
                resultado += line;
            inputStream.close();
            return resultado;
        }

        //07
    public Departamentos convertirJsonObjectDepartamentos(JSONObject jsonObject) throws JSONException {
        Departamentos dep = new Departamentos();
        String num, nom, loc;
        num = jsonObject.optString("numero").toString();
        nom = jsonObject.optString ("nombre").toString();
        loc= jsonObject.optString("localidad").toString();
        dep.setNumero(num);
        dep.setNombre(nom);
        dep.setLocalidad(loc);

        return dep;
    }   // final convertirJsonObjectDepartamentos


}