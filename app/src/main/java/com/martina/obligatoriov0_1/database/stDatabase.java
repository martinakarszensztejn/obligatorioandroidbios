package com.martina.obligatoriov0_1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.martina.obligatoriov0_1.objetos.SimpleTransportation;

import java.util.ArrayList;
import java.util.List;

public class stDatabase {
    private final Context contexto;
    public stDatabase(Context contexto) {
        this.contexto = contexto;
    }


    public SimpleTransportation getst(int id){
        DatabaseHelper databaseHelper = new DatabaseHelper(contexto);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String stringid = String.valueOf(id);
        String[] idor = new String[] {stringid};
        Cursor cursor = db.rawQuery(" SELECT * FROM contacto WHERE id=?;", idor);
        if (cursor.moveToFirst()) {

            String origen = cursor.getString(1);
            String estado = cursor.getString(2);
            SimpleTransportation result = new SimpleTransportation();
            result.setId(id);
            result.setEstado(estado);
            result.setOrigen(origen);
            cursor.close();
            db.close();
            databaseHelper.close();
            return result;
        }

        return null;
    }
    public List<SimpleTransportation> getstList(){
        DatabaseHelper databaseHelper = new DatabaseHelper(contexto);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        List<SimpleTransportation> resultList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM contacto;", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String origen = cursor.getString(1);
                String estado = cursor.getString(2);
                SimpleTransportation result = new SimpleTransportation();
                result.setId(id);
                result.setEstado(estado);
                result.setOrigen(origen);
                resultList.add(result);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        databaseHelper.close();
        return resultList;
    }
    public void guardar(int id, String estado, String origen) {
        DatabaseHelper databaseHelper = new DatabaseHelper(contexto);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("id", id);
        valores.put("origen", origen);
        valores.put("estado", estado);
        db.insert("st", null, valores);
        db.close();
        databaseHelper.close();
    }


}
