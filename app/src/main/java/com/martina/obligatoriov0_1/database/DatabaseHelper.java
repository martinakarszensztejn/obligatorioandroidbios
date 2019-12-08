package com.martina.obligatoriov0_1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.martina.obligatoriov0_1.constantes.Constantes;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int CURRENT_VERSION = Constantes.CURRENT_DATABASE_VERSION;
    private static final String DATABASE_NAME = Constantes.DATABASE_NAME;
    public DatabaseHelper(Context contexto){
        super(contexto,DATABASE_NAME,null,CURRENT_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase mydb) {
        String sql = "CREATE TABLE st (id INTEGER PRIMARY KEY,origen TEXT UNIQUE NOT NULL,estado TEXT UNIQUE NOT NULL );";

        mydb.execSQL(sql);
        String sql_1 = "CREATE TABLE t(id INTEGER PRIMARY KEY,estado TEXT NOT NULL,fecha TEXT,origen_direccion TEXT NOT NULL,origen_latitud REAL NOT NULL,origen_longitud REAL NOT NULL,destino_direccion TEXT NOT NULL,destino_latitud REAL NOT NULL,destino_longitud REAL NOT NULL,vehiculo_marca TEXT,vehiculo_modelo TEXT,vehiculo_matricula TEXT,vehiculo_chofer TEXT,recepcion_nombre_receptor TEXT,recepcion_observacion TEXT,recepcion_fecha TEXT);";
        mydb.execSQL(sql_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int old_ver, int cur_ver) {

    }
}
