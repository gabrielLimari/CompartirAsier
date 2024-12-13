package com.example.fragmnetejerciciotarea_12_12;

import java.io.Serializable;

public class Tarea implements Serializable {
    private String titulo;
    private String descripcion;
    private boolean realizada = false;

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public Tarea(String titulo, String descripcion) {
        this.descripcion = descripcion;
        this.titulo = titulo;

    }
}
