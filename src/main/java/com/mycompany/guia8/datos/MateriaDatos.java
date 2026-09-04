package com.mycompany.guia8.datos;

import com.mycompany.guia8.beans.MateriaBeans;
import com.mycompany.guia8.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

public class MateriaDatos {

    private final String SQL_INSERT =
            "INSERT INTO materia(nombre_materia, unidades_valorativas) VALUES(?,?)";

    private final String SQL_UPDATE =
            "UPDATE materia SET nombre_materia=?, unidades_valorativas=? WHERE id_materia=?";

    private final String SQL_DELETE =
            "DELETE FROM materia WHERE id_materia=?";

    private final String SQL_SELECT =
            "SELECT id_materia, nombre_materia, unidades_valorativas FROM materia";

    public int insert(MateriaBeans materiaBeans) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setString(index++, materiaBeans.getNombreMateria());
            stmt.setInt(index++, materiaBeans.getUnidadesValorativas());
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int update(MateriaBeans materiaBeans) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setString(index++, materiaBeans.getNombreMateria());
            stmt.setInt(index++, materiaBeans.getUnidadesValorativas());
            stmt.setInt(index++, materiaBeans.getIdMateria());
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int delete(int idMateria) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idMateria);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public DefaultTableModel selectMateria() {
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
    public DefaultComboBoxModel<String> selectMateriasCombo() {
        DefaultComboBoxModel<String> dcbm = new DefaultComboBoxModel<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while (rs.next()) {
                dcbm.addElement(rs.getInt("id_materia") + " - " + rs.getString("nombre_materia"));
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
