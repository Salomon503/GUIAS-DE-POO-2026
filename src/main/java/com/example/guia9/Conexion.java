package com.example.guia9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase de conexion a la bdd personabddg9.
 * Se mantiene la estructura original de la guia (setRs / setQuery / cerrarConexion)
 * y se agrega getConexion() para poder usar PreparedStatement desde los servlets
 * nuevos (RegistrarPersona y AgregarOcupacion), evitando inyeccion SQL en los
 * datos que digita el usuario.
 */
public class Conexion {

    private Connection conexion = null;
    private Statement s = null;
    private ResultSet rs = null;

    // Constructor
    public Conexion() throws SQLException {
        try {
            // obtenemos el driver para mysql
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Se obtiene una conexion con la base de datos.
           conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/personabddg9"
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                "root", "");
            // Permite ejecutar sentencias SQL sin parametros
            s = conexion.createStatement();
        } catch (ClassNotFoundException e1) {
            // Error si no puedo leer el driver de MySQL
            System.out.println("ERROR:No encuentro el driver de la BD: " + e1.getMessage());
        }
    }

    // Metodo que expone la conexion cruda para poder crear PreparedStatement
    public Connection getConexion() {
        return conexion;
    }

    // Metodo que permite obtener los valores del resultset
    public ResultSet getRs() {
        return rs;
    }

    // Metodo que fija la tabla resultado de la pregunta SQL realizada (sin parametros)
    public void setRs(String consulta) {
        try {
            this.rs = s.executeQuery(consulta);
        } catch (SQLException e2) {
            System.out.println("ERROR:Fallo en SQL: " + e2.getMessage());
        }
    }

    // Metodo que recibe un sql como parametro que sea un update, insert o delete (sin parametros)
    public void setQuery(String query) throws SQLException {
        this.s.executeUpdate(query);
    }

    // Metodo que cierra la conexion
    public void cerrarConexion() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }
}

