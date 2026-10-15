package com.example.guia11bean;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OcupacionBean {

    private int idOcupacion;
    private String ocupacion;
    private int totalPersonas;
    private String error;

    public OcupacionBean() { }

    public int getIdOcupacion() { return idOcupacion; }
    public void setIdOcupacion(int idOcupacion) { this.idOcupacion = idOcupacion; }

    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }

    public int getTotalPersonas() { return totalPersonas; }
    public void setTotalPersonas(int totalPersonas) { this.totalPersonas = totalPersonas; }

    public String getError() { return error; }

    public boolean validar() {
        if (ocupacion == null || ocupacion.trim().isEmpty()) {
            error = "El nombre de la ocupación es obligatorio.";
            return false;
        }
        if (ocupacion.trim().length() > 50) {
            error = "La ocupación no puede superar los 50 caracteres.";
            return false;
        }
        return true;
    }

    public List<OcupacionBean> listar() {
        error = null;
        List<OcupacionBean> lista = new ArrayList<>();
        String sql = "SELECT o.id_ocupacion, o.ocupacion, COUNT(p.id_persona) AS total "
                + "FROM ocupaciones o LEFT JOIN persona p ON p.id_ocupacion = o.id_ocupacion "
                + "GROUP BY o.id_ocupacion, o.ocupacion ORDER BY o.id_ocupacion";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                OcupacionBean o = new OcupacionBean();
                o.setIdOcupacion(rs.getInt("id_ocupacion"));
                o.setOcupacion(rs.getString("ocupacion"));
                o.setTotalPersonas(rs.getInt("total"));
                lista.add(o);
            }
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
        }
        return lista;
    }

    public boolean cargar(int id) {
        error = null;
        String sql = "SELECT id_ocupacion, ocupacion FROM ocupaciones WHERE id_ocupacion = ?";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    idOcupacion = rs.getInt("id_ocupacion");
                    ocupacion = rs.getString("ocupacion");
                    return true;
                }
            }
            error = "No se encontró la ocupación solicitada.";
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
        }
        return false;
    }

    public boolean insertar() {
        error = null;
        if (!validar()) {
            return false;
        }
        String sql = "INSERT INTO ocupaciones (ocupacion) VALUES (?)";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, ocupacion.trim());
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    idOcupacion = keys.getInt(1);
                }
            }
            return true;
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
            return false;
        }
    }

    public boolean actualizar() {
        error = null;
        if (!validar()) {
            return false;
        }
        String sql = "UPDATE ocupaciones SET ocupacion = ? WHERE id_ocupacion = ?";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, ocupacion.trim());
            st.setInt(2, idOcupacion);
            if (st.executeUpdate() == 0) {
                error = "La ocupación que intenta modificar ya no existe.";
                return false;
            }
            return true;
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
            return false;
        }
    }

    public boolean eliminar() {
        error = null;
        String sql = "DELETE FROM ocupaciones WHERE id_ocupacion = ?";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, idOcupacion);
            if (st.executeUpdate() == 0) {
                error = "La ocupación que intenta eliminar ya no existe.";
                return false;
            }
            return true;
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
            return false;
        }
    }
}