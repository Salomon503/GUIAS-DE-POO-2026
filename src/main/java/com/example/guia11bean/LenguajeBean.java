package com.example.guia11bean;

public class LenguajeBean {

    private String nombre;
    private String lenguaje;

    public LenguajeBean() { }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getLenguaje() { return lenguaje; }
    public void setLenguaje(String lenguaje) { this.lenguaje = lenguaje; }

    public String getComentarios() {
        if (lenguaje == null) {
            return "Aún no se ha elegido un lenguaje";
        }
        if (lenguaje.equals("Java")) {
            return "El rey de los Lenguajes Orientados a Objetos";
        } else if (lenguaje.equals("C++")) {
            return "Demasiado complejo";
        } else if (lenguaje.equals("Perl")) {
            return "OK si te gusta el código incomprensible";
        } else {
            return "Lo siento, no conozco el lenguaje " + lenguaje;
        }
    }
}
