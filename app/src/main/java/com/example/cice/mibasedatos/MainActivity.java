package com.example.cice.mibasedatos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BaseDatosCochesPersonas baseDatosCochesPersonas = new BaseDatosCochesPersonas(this, "MiDB", null, 1);

        Persona persona1 = new Persona(1, "Juan");
        Persona persona2 = new Persona(2, "Conchi");
        Persona persona3 = new Persona(3, "Manolo");


        baseDatosCochesPersonas.insertarPersona(persona1);
        baseDatosCochesPersonas.insertarPersona(persona2);
        baseDatosCochesPersonas.insertarPersona(persona3);

        Coche coche1 = new Coche("Ferrari", persona1);
        Coche coche2 = new Coche("Renault", persona2);
        Coche coche3 = new Coche("Fiat", persona3);


        baseDatosCochesPersonas.insertarCoche(coche1);
        baseDatosCochesPersonas.insertarCoche(coche2);
        baseDatosCochesPersonas.insertarCoche(coche3);

        List<Coche> lista_coches = baseDatosCochesPersonas.buscarCochesPersona(persona2);

        Log.d(getClass().getCanonicalName(), "La persona " + persona2.getNombre() + " tiene ");
        for(Coche coche : lista_coches) {

            Log.d(getClass().getCanonicalName(), coche.getModelo());

        }

        Persona mortal = baseDatosCochesPersonas.buscarPersona("Conchi");

        Log.d(getClass().getCanonicalName(), "El nombre de la persona es " + mortal.getNombre());
    }
}
