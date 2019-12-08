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
        Cursor cursor = db.rawQuery(" SELECT * FROM st WHERE id=?;", idor);
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
        Cursor cursor = db.rawQuery("SELECT * FROM st;", null);

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
    public void setst(int id, String estado, String origen) {
        DatabaseHelper dh = new DatabaseHelper(contexto);
        SQLiteDatabase db = dh.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("origen", origen);
        values.put("estado", estado);
        db.insertWithOnConflict("st", null, values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        dh.close();
    }
//    public void updatest(int id_new, String estado_new, String origen_new){
//        DatabaseHelper dh = new DatabaseHelper(contexto);
//        SQLiteDatabase db = dh.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("id_new",id_new);
//        values.put("origen_new", origen_new);
//        values.put("estado_new",estado_new);
//        String[] argu = new String[1];
//        argu[0]=String.valueOf(id_new);
//        db.update("st",values,"ID = ?",argu);
//        db.close();
//        dh.close();
//
//    }



}
