package com.example.examendoctores;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Listado_registros extends AppCompatActivity {
    private RecyclerView miRecicler;
    private RecyclerView.Adapter miAdpater;
    private static final String TAG= MainActivity.class.getSimpleName();

    //01
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_registros);
        leerServicio();
        //Buscamos el control para cargar los datos
        miRecicler=(RecyclerView) findViewById(R.id.rv_Departamentos_Consulta);
        //añadimos que el tamaño del RecyclerView no se cambiará
        //que tiene hijos (items) que tienen anchura y altura fijas
        //permite que el recyclerView optimice mejor.
        miRecicler.setHasFixedSize(true);
        miRecicler.setLayoutManager((new LinearLayoutManager(this)));

    }

    //02
    public void leerServicio() {
        try {
            String url = "https://webapidepartamentos20210716114825.azurewebsites.net/api/Departamentos";
            new HttpAsyncTask().execute(url);
        } catch (Exception e){
            //manage exception
            System.out.println(e.toString());
            Log.i(TAG, "Error leyendo datos del servicio");
            e.printStackTrace();
        }
    }

    //03
    public String recuperarContenido(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        String resultado = null;
        HttpGet httpget = new HttpGet(url);
        HttpResponse respuesta = null;
        InputStream stream = null;
        try {
            respuesta = httpclient.execute(httpget);
            HttpEntity entity =respuesta.getEntity();


            if (entity != null){
                stream = entity.getContent();
                resultado = convertirInputToString(stream);
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }  finally {
                try {
                    if (stream != null) {
                        stream.close();
                    }
                }catch (Exception e) {
                        System.out.println(e.toString());
                    }
            }
        return resultado;
    }

    //04

    private String convertirInputToString ( InputStream inputStream) throws IOException {
        BufferedReader buferredReader = new BufferedReader(new InputStreamReader(inputStream));
        String line ="";
        String resultado = "";
        while( (line =buferredReader.readLine()) != null)
            resultado += line;
        inputStream.close();
        return resultado;
    }

    //05
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String... urls) {
            return recuperarContenido(urls[0]);
        }
        //onPostExecute displays the results of the AsyncTask.
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext() , "Datos recibidos", Toast.LENGTH_SHORT).show();
            try {
                JSONArray jsonarray = new JSONArray(result);
                List<Departamentos> lista = convertirJsonDepartamentos(jsonarray);
                //Especificamos el adaptador con la lista a visualizar
                miAdpater= new Adaptador(lista);
                miRecicler.setAdapter(miAdpater);

            } catch (JSONException e){
                System.out.println(e.toString());
                }

        }


        public List<Departamentos> convertirJsonDepartamentos(JSONArray jsonarray) throws JSONException {
            List<Departamentos> lista = new ArrayList<>();

            for ( int i=0; i< jsonarray.length(); i++) {
                Departamentos dept = new Departamentos();
                String num, nom, loc;

                num = jsonarray.getJSONObject(i).optString("numero").toString();
                nom = jsonarray.getJSONObject(i).optString("nombre").toString();
                loc = jsonarray.getJSONObject(i).optString("localidad").toString();
                dept.setNumero(num);
                dept.setNombre(nom);
                dept.setLocalidad(loc);
                lista.add(dept);
            }

            return lista;

        }



    }

    // 06 método  para infrar el menu superior

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navegacion, menu);
        return true;
    }

    //07 método para gestionar el item seleccionado

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        Intent accion;

        if (id== R.id.item_consultar)
        {
            Intent i = new Intent(this, Listado_registros.class);
            startActivity(i);

        }else if (id== R.id.item_alta_registro) {
            Intent i = new Intent(this, Alta_registro.class);
            startActivity(i);
        } else if( id== R.id.item_signup){
            Intent i = new Intent(this, SignUp_Registro.class);
            startActivity(i);
        }else if (id== R.id.item_navegar){
            accion = new Intent("android.intent.action.VIEW", Uri.parse("http://developer.android.com"));
            startActivity(accion);
        }

        return true;

    }


}
