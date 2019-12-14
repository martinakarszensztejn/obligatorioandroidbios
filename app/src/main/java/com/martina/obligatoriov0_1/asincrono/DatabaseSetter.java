package com.martina.obligatoriov0_1.asincrono;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import com.martina.obligatoriov0_1.constantes.Constantes;
import com.martina.obligatoriov0_1.database.stDatabase;
import com.martina.obligatoriov0_1.objetos.AuxiliarDatabaseBundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseSetter extends AsyncTask<AuxiliarDatabaseBundle,Void,Void>  {
    private Context contexto;

    public DatabaseSetter (Context context){
        contexto = context;
    }

    @Override
    protected Void doInBackground(AuxiliarDatabaseBundle... bundles) {
        stDatabase db = bundles[0].getDb();

        List idList = (List) bundles[0].getIdList();
        int size = idList.size();
        List estadoList = (List) bundles[0].getEstadoList();
        List origenList = (List) bundles[0].getOrigenList();

        for (int i = 0; i < idList.size(); i++) {
            db.setst((Integer) idList.get(i), (String) estadoList.get(i), (String) origenList.get(i));
        }


        return null;
    }
}
