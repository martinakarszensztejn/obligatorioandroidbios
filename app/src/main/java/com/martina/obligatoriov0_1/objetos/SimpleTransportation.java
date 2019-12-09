package com.martina.obligatoriov0_1.objetos;

public class SimpleTransportation {
    private int id;
    private String estado;
    private String origen;
    private double origen_lat;
    private double origen_long;

    public String getOrigen() { return origen; }

    public void setOrigen(String origen) { this.origen = origen; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getOrigen_lat() {
        return origen_lat;
    }

    public void setOrigen_lat(double origen_lat) {
        this.origen_lat = origen_lat;
    }

    public double getOrigen_long() {
        return origen_long;
    }

    public void setOrigen_long(double origen_long) {
        this.origen_long = origen_long;
    }
}
