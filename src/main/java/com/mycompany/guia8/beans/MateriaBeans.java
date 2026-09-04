package com.mycompany.guia8.beans;

public class MateriaBeans {

    private int idMateria;
    private String nombreMateria;
    private int unidadesValorativas;

    public MateriaBeans() {
    }

    public MateriaBeans(int idMateria, String nombreMateria, int unidadesValorativas) {
        setIdMateria(idMateria);
        setNombreMateria(nombreMateria);
        setUnidadesValorativas(unidadesValorativas);
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public int getUnidadesValorativas() {
        return unidadesValorativas;
    }

    public void setUnidadesValorativas(int unidadesValorativas) {
        this.unidadesValorativas = unidadesValorativas;
    }
}

