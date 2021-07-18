package com.example.examendoctores;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    ImageButton botonleer, botonalta, botonnavegar, botonregistro;
    TextView txtdatos;

    //01
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.txtdatos = (TextView) findViewById(R.id.txExamenAndroid);
        this.botonalta = (ImageButton) findViewById(R.id.button_topleft);
        this.botonleer = (ImageButton) findViewById(R.id.button_downleft);
        this.botonnavegar= (ImageButton) findViewById(R.id.button_topright);
        this.botonregistro=(ImageButton) findViewById(R.id.button_downright);


        // gestionamos la accion del boton insertar
        this.botonalta.setOnClickListener(new OnClickListener()  {
            public void onClick(View v) {
            insertarRegistro();
    }

    });
        //gestionamos la accion del boton leer
        this.botonleer.setOnClickListener(new OnClickListener()  {
            public void onClick(View v) {
                leerRegistros();
            }

        });
        // gestionamos el boton navegar
        this.botonnavegar.setOnClickListener(new OnClickListener()  {
            public void onClick(View v) {
                navegar();
            }

        });

        // gestionamos el registro del usuario
        this.botonregistro.setOnClickListener(new OnClickListener()  {
            public void onClick(View v) {
                registrosingup();
            }

        });

}

    //03  insertamos un registro nuevo
    public void insertarRegistro (){

        Intent i = new Intent(this, Alta_registro.class);
        startActivity(i);
    }

    //04 método para leer los registros

    public void leerRegistros (){


        Intent i = new Intent(this, Listado_registros.class);
        startActivity(i);

    }


    // 06 método  para inflar el menu superior

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

        if (id == R.id.item_consultar) {
            leerRegistros();
        } else if (id == R.id.item_alta_registro) {
            insertarRegistro();
        } else if( id== R.id.item_signup){
            registrosingup();
         }else if (id== R.id.item_navegar)
        {
            accion = new Intent("android.intent.action.VIEW", Uri.parse("http://developer.android.com"));
            startActivity(accion);
        }

        return true;

    }

    //08 Método para navegar por internet
    public void navegar () {
        Intent accion;
        accion = new Intent("android.intent.action.VIEW", Uri.parse("http://developer.android.com"));
        startActivity(accion);
    }
  // 08 Método para SIGNUP del usuario de la aplicación
    public void registrosingup() {
        Intent accion;
        accion = new Intent(this, SignUp_Registro.class);
        startActivity(accion);
    }


}
