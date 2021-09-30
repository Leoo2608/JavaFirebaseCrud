package com.example.javafirebasecrud.model;

public class Escuela {
    private String uid;
    private String nombre;
    private String facultad;

    public Escuela(){

    }
    public Escuela(String uid, String nombre, String facultad) {
        this.uid = uid;
        this.nombre = nombre;
        this.facultad = facultad;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFacultad() {
        return facultad;
    }

    public void setFacultad(String facultad) {
        this.facultad = facultad;
    }
}
