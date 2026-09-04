package com.mycompany.guia8.datos;

import com.mycompany.guia8.beans.AlumnoMateriaBeans;
import com.mycompany.guia8.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class AlumnoMateriaDatos {

    private final String SQL_INSERT =
            "INSERT INTO alumno_materia(id_alumno, id_materia, nota, fecha_inscripcion) VALUES(?,?,?,?)";

    private final String SQL_UPDATE =
            "UPDATE alumno_materia SET id_alumno=?, id_materia=?, nota=?, fecha_inscripcion=? "
            + "WHERE id_alumno_materia=?";

    private final String SQL_DELETE =
            "DELETE FROM alumno_materia WHERE id_alumno_materia=?";

    // Select con INNER JOIN para mostrar nombres en vez de solo los ids.
    // Se incluyen tambien am.id_alumno y am.id_materia al final como columnas
    // "ocultas" (se esconden en la vista de la tabla) para poder reconstruir
    // el registro completo cuando el usuario selecciona una fila a editar.
    private final String SQL_SELECT =
            "SELECT am.id_alumno_materia, a.nombre_alumno, m.nombre_materia, am.nota, am.fecha_inscripcion, "
            + "am.id_alumno, am.id_materia "
            + "FROM alumno_materia am "
            + "INNER JOIN alumno a ON am.id_alumno = a.id_alumno "
            + "INNER JOIN materia m ON am.id_materia = m.id_materia";

    public int insert(AlumnoMateriaBeans beans) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            int index = 1;
            stmt.setInt(index++, beans.getIdAlumno());
            stmt.setInt(index++, beans.getIdMateria());
            stmt.setDouble(index++, beans.getNota());
            stmt.setString(index++, beans.getFechaInscripcion());
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int update(AlumnoMateriaBeans beans) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            int index = 1;
            stmt.setInt(index++, beans.getIdAlumno());
            stmt.setInt(index++, beans.getIdMateria());
            stmt.setDouble(index++, beans.getNota());
            stmt.setString(index++, beans.getFechaInscripcion());
            stmt.setInt(index++, beans.getIdAlumnoMateria());
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public int delete(int idAlumnoMateria) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, idAlumnoMateria);
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }

    public DefaultTableModel selectAlumnoMateria() {
        DefaultTableModel dtm = new DefaultTableModel();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            // Las 2 ultimas columnas (id_alumno, id_materia) se ocultaran en el JTable
            String[] columnas = {"Id", "Alumno", "Materia", "Nota", "Fecha Inscripcion", "id_alumno", "id_materia"};
            for (String col : columnas) {
                dtm.addColumn(col);
            }
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getInt("id_alumno_materia");
                fila[1] = rs.getString("nombre_alumno");
                fila[2] = rs.getString("nombre_materia");
                fila[3] = rs.getDouble("nota");
                fila[4] = rs.getString("fecha_inscripcion");
                fila[5] = rs.getInt("id_alumno");
                fila[6] = rs.getInt("id_materia");
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
}

