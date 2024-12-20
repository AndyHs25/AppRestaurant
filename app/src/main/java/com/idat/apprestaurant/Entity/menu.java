package com.idat.apprestaurant.Entity;
import java.io.Serializable;


public class menu implements Serializable {

    private String nombre;
    private String descripcion;
    private String tipo;
    private Long precio;
    private int imagen;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public menu(String nombre, String descripcion, String tipo, Long precio, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.precio = precio;
        this.imagen = imagen;
    }

    public menu() {
    }

}



