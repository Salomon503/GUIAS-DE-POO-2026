package conexiones;

import java.sql.*;

/**
 * Clase encargada unicamente de administrar la conexion JDBC hacia la
 * base de datos "colegiobdd". Sigue el mismo patron usado en la guia
 * (clase Conexion de la Parte 5), reutilizada aqui para todo el CRUD.
 */
public class Conexion {

    private Connection conexion = null;
    private Statement s = null;
    private ResultSet rs = null;

    public Conexion() throws SQLException {
        try {
            // Obtenemos el driver para mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Se obtiene una conexion con la base de datos
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3307/colegiobdd", "root", "");
            // IMPORTANTE: el password por defecto va en blanco,
            // o coloque el password de su servidor MySQL.

            // Permite ejecutar sentencias SQL sin parametros
            s = conexion.createStatement();

            System.out.println("Conexion Exitosa");
        } catch (ClassNotFoundException e1) {
            System.out.println("ERROR: No encuentro el driver de la BD: " + e1.getMessage());
        } catch (SQLException e2) {
            System.out.println("ERROR: Fallo en SQL: " + e2.getMessage());
            throw e2;
        }
    }

    /** Devuelve el ultimo ResultSet obtenido por setRs(). */
    public ResultSet getRs() {
        return rs;
    }

    /** Ejecuta un SELECT y guarda el resultado en rs. */
    public void setRs(String sql) {
        try {
            this.rs = s.executeQuery(sql);
        } catch (SQLException e2) {
            System.out.println("ERROR: Fallo en SQL: " + e2.getMessage());
        }
    }

    /** Ejecuta un INSERT, UPDATE o DELETE. */
    public void setQuery(String sql) throws SQLException {
        this.s.executeUpdate(sql);
    }

    /** Cierra la conexion. NUNCA olvidar cerrarla. */
    public void cerrarConexion() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }
}