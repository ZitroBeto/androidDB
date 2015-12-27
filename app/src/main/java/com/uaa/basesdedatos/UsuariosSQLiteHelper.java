package com.uaa.basesdedatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Robert on 28/10/2015.
 */
public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    private String sqlCreate = "CREATE IF NOT EXIST Usuarios (id INTEGER, nombre TEXT";

    public UsuariosSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //se ejeccuta la sentencia de sql de creación de la tabla
        db.execSQL(sqlCreate);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //NOTA: por simplicidad del ejemplo aqui solo borramos la tabla y la volvemos a crear
        //sin embargo el proceso normal es migrar todos los datos de la tabla antigua a la nueva
        //(con un formato distinto, nuevas columnas) por lo que este metodo debería ser mas elaborado

        //se elimina la versión anterior de la tabla
        db.execSQL("DROP TABE IF EXIST Usuarios");

        //se crea la nueva version de los datos
        db.execSQL(sqlCreate);
    }
}
