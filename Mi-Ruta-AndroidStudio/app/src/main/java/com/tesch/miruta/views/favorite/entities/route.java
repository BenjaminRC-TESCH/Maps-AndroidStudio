package com.tesch.miruta.views.favorite.entities;

public class route {
    private Integer id;
    private String id_ruta;
    private String nombre;
    private String origen;
    private String destino;

    public route() {
        this.id = id;
        this.id_ruta = id_ruta;
        this.nombre = nombre;
        this.origen = origen;
        this.destino = destino;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getId_ruta() {
        return id_ruta;
    }

    public void setId_ruta(String id_ruta) {
        this.id_ruta = id_ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }
}
