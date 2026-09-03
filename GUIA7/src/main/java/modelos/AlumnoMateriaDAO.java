package modelos;

import conexiones.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Maneja la tabla intermedia "alumno_materia" (relacion muchos a muchos):
 * - Matricular a un alumno en una materia.
 * - Reporte: listar las materias que cursa un alumno especifico
 *   elegido por el usuario (punto 3 de los ejercicios complementarios).
 */
public class AlumnoMateriaDAO {

    public void matricular() throws SQLException {
        Conexion con = new Conexion();

        String codAlumnoS = JOptionPane.showInputDialog("Ingrese el codigo del alumno:");
        while (!Validaciones.esEntero(codAlumnoS)) {
            codAlumnoS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int codAlumno = Integer.parseInt(codAlumnoS);

        con.setRs("select * from alumno where cod_alumno=" + codAlumno);
        if (!con.getRs().next()) {
            JOptionPane.showMessageDialog(null, "No existe un alumno con ese codigo");
            con.cerrarConexion();
            return;
        }

        String codMateriaS = JOptionPane.showInputDialog("Ingrese el codigo de la materia:");
        while (!Validaciones.esEntero(codMateriaS)) {
            codMateriaS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int codMateria = Integer.parseInt(codMateriaS);

        con.setRs("select * from materia where cod_materia=" + codMateria);
        if (!con.getRs().next()) {
            JOptionPane.showMessageDialog(null, "No existe una materia con ese codigo");
            con.cerrarConexion();
            return;
        }

        try {
            con.setQuery("insert into alumno_materia values (" + codAlumno + "," + codMateria + ")");
            JOptionPane.showMessageDialog(null, "Matricula realizada correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR al matricular (¿ya estaba matriculado?): " + e.getMessage());
        } finally {
            con.cerrarConexion();
        }
    }

    /** Reporte: materias que cursa un alumno especifico elegido por el usuario. */
    public void reporteMateriasPorAlumno() throws SQLException {
        Conexion con = new Conexion();

        String codAlumnoS = JOptionPane.showInputDialog("Ingrese el codigo del alumno a consultar:");
        while (!Validaciones.esEntero(codAlumnoS)) {
            codAlumnoS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int codAlumno = Integer.parseInt(codAlumnoS);

        con.setRs("select nombre, apellido from alumno where cod_alumno=" + codAlumno);
        ResultSet rsAlumno = con.getRs();
        if (!rsAlumno.next()) {
            JOptionPane.showMessageDialog(null, "No existe un alumno con ese codigo");
            con.cerrarConexion();
            return;
        }
        String nombreCompleto = rsAlumno.getString("nombre") + " " + rsAlumno.getString("apellido");

        con.setRs("select m.nombre from materia m "
                + "inner join alumno_materia am on m.cod_materia = am.cod_materia "
                + "where am.cod_alumno = " + codAlumno);
        ResultSet rs = con.getRs();

        StringBuilder sb = new StringBuilder();
        sb.append("Materias que cursa ").append(nombreCompleto).append(":\n");
        boolean tieneMaterias = false;
        while (rs.next()) {
            sb.append("- ").append(rs.getString("nombre")).append("\n");
            tieneMaterias = true;
        }
        if (!tieneMaterias) {
            sb.append("El alumno no esta matriculado en ninguna materia.");
        }

        JOptionPane.showMessageDialog(null, sb.toString(), "Reporte de Materias", JOptionPane.INFORMATION_MESSAGE);
        con.cerrarConexion();
    }
}
