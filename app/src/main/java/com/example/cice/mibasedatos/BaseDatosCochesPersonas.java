package com.example.cice.mibasedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cice on 17/1/17.
 */

public class BaseDatosCochesPersonas extends SQLiteOpenHelper {

    private static final String sqlCreacionTablaPersona = "CREATE TABLE PERSONA (id INTEGER PRIMARY KEY, nombre TEXT)";
    private static final String sqlCreacionTablaCoches = "CREATE TABLE COCHE (id INTEGER PRIMARY KEY AUTOINCREMENT, modelo TEXT, idpersona INTEGER, FOREIGN KEY (idPersona) REFERENCES PERSONA (id))";

    public BaseDatosCochesPersonas (Context context, String nombre, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreacionTablaPersona);
        db.execSQL(sqlCreacionTablaCoches);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        //1.EXTRAER LOS DATOS DE LA VIEJA VERSIÓN

        //2.CREAR LA NUEVA VERSIÓN

        //3.COPIAR LOS DATOS VIEJOS A LA NUEVA VERSIÓN

        //TODO por SQL
    }

    private void cerrarBaseDatos (SQLiteDatabase bd) {

        bd.close();

    }

    public void insertarPersona (Persona persona) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO PERSONA (id, nombre) VALUES (" + persona.getId() + " , '" + persona.getNombre() + "')");

    }

    public void insertarCoche (Coche coche) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO COCHE (modelo, idpersona) VALUES ('"+coche.getModelo() + "', " + coche.getPersona().getId()+")");
        cerrarBaseDatos(db);
    }


    public Persona buscarPersona(String nombre)
    {
        Persona persona = null;
        String consulta = "SELECT id, nombre FROM PERSONA WHERE nombre = '" +nombre+"'";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(consulta, null);

            if(cursor != null) {

                cursor.moveToFirst();

                do {
                    nombre = cursor.getString(1);
                    persona = new Persona(nombre);
                } while(cursor.moveToNext());
                cursor.close();
            }
            this.cerrarBaseDatos(db);

        return persona;
    }

    public List<Coche> buscarCochesPersona (Persona persona) {

        List<Coche> lista_coches = null;
        int n_resultados = 0;

        String modelo = null;
        String consulta = "SELECT modelo FROM COCHE WHERE idpersona = " + persona.getId();
        Coche coche_aux = null;


            SQLiteDatabase db = this.getReadableDatabase();//Obtengo base de datos en modo lectura
            Cursor cursor = db.rawQuery(consulta, null); //Ejecuto la consulta

            n_resultados = cursor.getCount();

            if(cursor != null && n_resultados >= 0) {

                //la consulta ha recuperado datos
                cursor.moveToFirst();//Cursor se mueve al primer registro
                lista_coches = new ArrayList<Coche>(n_resultados);

                do {
                    modelo = cursor.getString(0);
                    coche_aux = new Coche(modelo);
                    lista_coches.add(coche_aux);
                } while (cursor.moveToNext());

                cursor.close();
            }

            this.cerrarBaseDatos(db);

        return lista_coches;
    }
}
