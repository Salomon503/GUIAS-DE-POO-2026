package com.example.guia11bean;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaBean de la tabla "persona".
 * Los nombres de las propiedades coinciden con los "name" de los campos del formulario,
 * así <jsp:setProperty property="*"/> los llena automáticamente.
 */
public class PersonaBean {

    private int idPersona;
    private String nombrePersona;
    private int edadPersona;
    private String telefonoPersona;
    private String sexoPersona;
    private int idOcupacion;
    private String nombreOcupacion;   // viene del JOIN con "ocupaciones"
    private String fechaNac;          // formato yyyy-MM-dd
    private String error;

    private static final String SELECT_BASE =
            "SELECT p.id_persona, p.nombre_persona, p.edad_persona, p.telefono_persona, "
          + "p.sexo_persona, p.id_ocupacion, p.fecha_nac, o.ocupacion "
          + "FROM persona p INNER JOIN ocupaciones o ON p.id_ocupacion = o.id_ocupacion";

    public PersonaBean() { }

    // ----------------- Métodos de propiedad -----------------
    public int getIdPersona() { return idPersona; }
    public void setIdPersona(int idPersona) { this.idPersona = idPersona; }

    public String getNombrePersona() { return nombrePersona; }
    public void setNombrePersona(String nombrePersona) { this.nombrePersona = nombrePersona; }

    public int getEdadPersona() { return edadPersona; }
    public void setEdadPersona(int edadPersona) { this.edadPersona = edadPersona; }

    public String getTelefonoPersona() { return telefonoPersona; }
    public void setTelefonoPersona(String telefonoPersona) { this.telefonoPersona = telefonoPersona; }

    public String getSexoPersona() { return sexoPersona; }
    public void setSexoPersona(String sexoPersona) { this.sexoPersona = sexoPersona; }

    public int getIdOcupacion() { return idOcupacion; }
    public void setIdOcupacion(int idOcupacion) { this.idOcupacion = idOcupacion; }

    public String getNombreOcupacion() { return nombreOcupacion; }
    public void setNombreOcupacion(String nombreOcupacion) { this.nombreOcupacion = nombreOcupacion; }

    public String getFechaNac() { return fechaNac; }
    public void setFechaNac(String fechaNac) { this.fechaNac = fechaNac; }

    public String getError() { return error; }

    // ----------------- Lógica de negocio -----------------
    public boolean validar() {
        if (nombrePersona == null || nombrePersona.trim().isEmpty()) {
            error = "El nombre es obligatorio.";
            return false;
        }
        if (nombrePersona.trim().length() > 100) {
            error = "El nombre no puede superar los 100 caracteres.";
            return false;
        }
        if (edadPersona < 1 || edadPersona > 120) {
            error = "La edad debe estar entre 1 y 120 años.";
            return false;
        }
        if (telefonoPersona == null || !telefonoPersona.trim().matches("\\d{4}-\\d{4}")) {
            error = "El teléfono debe tener el formato 0000-0000.";
            return false;
        }
        if (!"Masculino".equals(sexoPersona) && !"Femenino".equals(sexoPersona)) {
            error = "Seleccione un sexo válido.";
            return false;
        }
        if (idOcupacion <= 0) {
            error = "Seleccione una ocupación.";
            return false;
        }
        try {
            LocalDate.parse(fechaNac == null ? "" : fechaNac.trim());
        } catch (DateTimeParseException e) {
            error = "La fecha de nacimiento no es válida.";
            return false;
        }
        return true;
    }

    private static PersonaBean mapear(ResultSet rs) throws SQLException {
        PersonaBean p = new PersonaBean();
        p.setIdPersona(rs.getInt("id_persona"));
        p.setNombrePersona(rs.getString("nombre_persona"));
        p.setEdadPersona(rs.getInt("edad_persona"));
        p.setTelefonoPersona(rs.getString("telefono_persona"));
        p.setSexoPersona(rs.getString("sexo_persona"));
        p.setIdOcupacion(rs.getInt("id_ocupacion"));
        p.setFechaNac(rs.getString("fecha_nac"));
        p.setNombreOcupacion(rs.getString("ocupacion"));
        return p;
    }

    public List<PersonaBean> listar() {
        error = null;
        List<PersonaBean> lista = new ArrayList<>();
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(SELECT_BASE + " ORDER BY p.id_persona");
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
        }
        return lista;
    }

    /** Carga en ESTE bean los datos de la persona con el id indicado. */
    public boolean cargar(int id) {
        error = null;
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(SELECT_BASE + " WHERE p.id_persona = ?")) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    PersonaBean p = mapear(rs);
                    idPersona = p.idPersona;
                    nombrePersona = p.nombrePersona;
                    edadPersona = p.edadPersona;
                    telefonoPersona = p.telefonoPersona;
                    sexoPersona = p.sexoPersona;
                    idOcupacion = p.idOcupacion;
                    nombreOcupacion = p.nombreOcupacion;
                    fechaNac = p.fechaNac;
                    return true;
                }
            }
            error = "No se encontró la persona solicitada.";
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
        String sql = "INSERT INTO persona (nombre_persona, edad_persona, telefono_persona, "
                + "sexo_persona, id_ocupacion, fecha_nac) VALUES (?,?,?,?,?,?)";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, nombrePersona.trim());
            st.setInt(2, edadPersona);
            st.setString(3, telefonoPersona.trim());
            st.setString(4, sexoPersona);
            st.setInt(5, idOcupacion);
            st.setDate(6, Date.valueOf(fechaNac.trim()));
            st.executeUpdate();
            try (ResultSet keys = st.getGeneratedKeys()) {
                if (keys.next()) {
                    idPersona = keys.getInt(1);
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
        String sql = "UPDATE persona SET nombre_persona=?, edad_persona=?, telefono_persona=?, "
                + "sexo_persona=?, id_ocupacion=?, fecha_nac=? WHERE id_persona=?";
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement(sql)) {
            st.setString(1, nombrePersona.trim());
            st.setInt(2, edadPersona);
            st.setString(3, telefonoPersona.trim());
            st.setString(4, sexoPersona);
            st.setInt(5, idOcupacion);
            st.setDate(6, Date.valueOf(fechaNac.trim()));
            st.setInt(7, idPersona);
            if (st.executeUpdate() == 0) {
                error = "La persona que intenta modificar ya no existe.";
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
        try (Connection c = ConexionBD.getConexion();
             PreparedStatement st = c.prepareStatement("DELETE FROM persona WHERE id_persona = ?")) {
            st.setInt(1, idPersona);
            if (st.executeUpdate() == 0) {
                error = "La persona que intenta eliminar ya no existe.";
                return false;
            }
            return true;
        } catch (SQLException e) {
            error = ConexionBD.mensaje(e);
            return false;
        }
    }
}
