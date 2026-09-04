package com.mycompany.guia8.datos;

import com.mycompany.guia8.beans.AlumnoBeans;
import com.mycompany.guia8.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

public class AlumnoDatos {

    private final String SQL_INSERT =
            "INSERT INTO alumno(nombre_alumno, edad, carrera, telefono) VALUES(?,?,?,?)";

    private final String SQL_UPDATE =
            "UPDATE alumno SET nombre_alumno=?, edad=?, carrera=?, telefono=? WHERE id_alumno=?";

    private final String SQL_DELETE =
            "DELETE FROM alumno WHERE id_alumno=?";

    private final String SQL_SELECT =
            "SELECT id_alumno, nombre_alumno, edad, carrera, telefono FROM alumno";

    private final String SQL_SELECT_ID =
            "SELECT id_alumno FROM alumno WHERE nombre_alumno=?";

    public int insert(AlumnoBeans alumnoBeans) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, alumnoBeans.getNombreAlumno());
            stmt.setInt(index++, alumnoBeans.getEdad());
            stmt.setString(index++, alumnoBeans.getCarrera());
            stmt.setString(index++, alumnoBeans.getTelefono());
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int update(AlumnoBeans alumnoBeans) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, alumnoBeans.getNombreAlumno());
            stmt.setInt(index++, alumnoBeans.getEdad());
            stmt.setString(index++, alumnoBeans.getCarrera());
            stmt.setString(index++, alumnoBeans.getTelefono());
            stmt.setInt(index++, alumnoBeans.getIdAlumno());
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int delete(int idAlumno) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idAlumno);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public DefaultTableModel selectAlumno() {
        DefaultTableModel dtm = new DefaultTableModel();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                dtm.addColumn(meta.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[numberOfColumns];
                for (int i = 0; i < numberOfColumns; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                dtm.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return dtm;
    }

    // Llena un combobox con "id - nombre" para usarlo en AlumnoMateria
    public DefaultComboBoxModel<String> selectAlumnosCombo() {
        DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dcbm.addElement(rs.getInt("id_alumno") + " - " + rs.getString("nombre_alumno"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return dcbm;
    }
}

