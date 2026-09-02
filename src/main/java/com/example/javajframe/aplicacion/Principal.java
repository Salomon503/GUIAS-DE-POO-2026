package com.example.javajframe.aplicacion;

import com.example.javajframe.interfaceswing.frmPersona;

public class Principal {
    public static void main(String[] args) {
        frmPersona formpersona = new frmPersona("Ingreso de datos de una persona");
        formpersona.setVisible(true);
    }
}