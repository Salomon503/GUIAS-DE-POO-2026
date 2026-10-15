package com.example.guia11bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3307/personabdd"
            + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private ConexionBD() { }

    public static Connection getConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. Revise la dependencia en el pom.xml.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    public static String mensaje(SQLException e) {
        switch (e.getErrorCode()) {
            case 1451: return "No se puede eliminar el registro porque está siendo utilizado por otros registros.";
            case 1452: return "La ocupación seleccionada no existe.";
            case 1049: return "La base de datos 'personabdd' no existe. Ejecute el script sql/personabdd.sql.";
            case 1045: return "Acceso denegado a MySQL. Revise usuario y contraseña en ConexionBD.java.";
            case 1146: return "Faltan tablas en la base de datos. Ejecute el script sql/personabdd.sql.";
            default:
                if (e.getSQLState() != null && e.getSQLState().startsWith("08")) {
                    return "No se pudo conectar con MySQL. Verifique que el servidor esté iniciado.";
                }
                return "Error de base de datos: " + e.getMessage();
        }
    }
}