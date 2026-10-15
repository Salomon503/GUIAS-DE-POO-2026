package com.example.guia11bean;

public class PersonitaBean {

    private String nombre;
    private int edad;

    public PersonitaBean() { }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getTipo() {
        if (edad < 40) return "joven";
        return "no joven";
    }

    public boolean getJoven() {
        return edad < 40;
    }
}
