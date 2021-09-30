package com.example.javafirebasecrud.model;

public class Docente {
    public String uid;
    public String codigo;
    public String nombres;
    public String dni;
    public String telefono;
    public String correo;

    public Docente(){

    }

    public Docente(String uid, String codigo, String nombres, String dni, String telefono, String correo) {
        this.uid = uid;
        this.codigo = codigo;
        this.nombres = nombres;
        this.dni = dni;
        this.telefono = telefono;
        this.correo = correo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}
