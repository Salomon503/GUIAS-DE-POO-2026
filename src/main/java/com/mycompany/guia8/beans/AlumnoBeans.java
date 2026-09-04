package com.mycompany.guia8.beans;

public class AlumnoBeans {

    private int idAlumno;
    private String nombreAlumno;
    private int edad;
    private String carrera;
    private String telefono;

    public AlumnoBeans() {
    }

    public AlumnoBeans(int idAlumno, String nombreAlumno, int edad, String carrera, String telefono) {
        setIdAlumno(idAlumno);
        setNombreAlumno(nombreAlumno);
        setEdad(edad);
        setCarrera(carrera);
        setTelefono(telefono);
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}

