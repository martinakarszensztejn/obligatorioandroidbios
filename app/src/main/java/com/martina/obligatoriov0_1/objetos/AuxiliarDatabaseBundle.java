package com.martina.obligatoriov0_1.objetos;

import com.martina.obligatoriov0_1.database.stDatabase;

import java.util.List;

public class AuxiliarDatabaseBundle {
    private List<Integer> idList;
    private List<String> estadoList;
    private List<String> origenList;
    private stDatabase db;

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public List<String> getEstadoList() {
        return estadoList;
    }

    public void setEstadoList(List<String> estadoList) {
        this.estadoList = estadoList;
    }

    public List<String> getOrigenList() {
        return origenList;
    }

    public void setOrigenList(List<String> origenList) {
        this.origenList = origenList;
    }

    public stDatabase getDb() {
        return db;
    }

    public void setDb(stDatabase db) {
        this.db = db;
    }
}
