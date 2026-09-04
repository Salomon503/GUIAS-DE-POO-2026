package com.mycompany.guia8.beans;

public class AlumnoMateriaBeans {

    private int idAlumnoMateria;
    private int idAlumno;
    private int idMateria;
    private double nota;
    private String fechaInscripcion;

    public AlumnoMateriaBeans() {
    }

    public AlumnoMateriaBeans(int idAlumnoMateria, int idAlumno, int idMateria,
            double nota, String fechaInscripcion) {
        setIdAlumnoMateria(idAlumnoMateria);
        setIdAlumno(idAlumno);
        setIdMateria(idMateria);
        setNota(nota);
        setFechaInscripcion(fechaInscripcion);
    }

    public int getIdAlumnoMateria() {
        return idAlumnoMateria;
    }

    public void setIdAlumnoMateria(int idAlumnoMateria) {
        this.idAlumnoMateria = idAlumnoMateria;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}

