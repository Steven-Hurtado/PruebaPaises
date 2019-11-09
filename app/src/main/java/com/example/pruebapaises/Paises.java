package com.example.pruebapaises;

import org.json.JSONException;

public class Paises {

    private String nombre;
    private String imagen;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Paises(String nombre, String imagen) {
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Paises() throws JSONException {
    }


}
