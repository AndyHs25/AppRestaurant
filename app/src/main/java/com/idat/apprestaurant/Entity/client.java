package com.idat.apprestaurant.Entity;

import java.io.Serializable;

public class client implements Serializable {

    public String dni;
    public String nombre;
    public String apellido;
    public String sexo;
    public String telefono;
    public String direccion;
    public String correo;
    public String estado;

    public client() {
    }

    public client(String dni, String nombre, String apellido, String sexo, String telefono, String direccion, String correo, String estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.telefono = telefono;
        this.direccion = direccion;
        this.correo = correo;
        this.estado = estado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
