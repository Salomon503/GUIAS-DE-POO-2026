package modelos;

import conexiones.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * CRUD para la tabla "alumno".
 * Usa JOptionPane solo para informar exito/error, tal como pide la guia.
 */
public class AlumnoDAO {

    // ---------- CREATE ----------
    public void crear() throws SQLException {
        Conexion con = new Conexion();

        String codS = JOptionPane.showInputDialog("Ingrese el codigo del alumno:");
        while (!Validaciones.esEntero(codS)) {
            codS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int cod = Integer.parseInt(codS);

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
        while (!Validaciones.esTextoValido(nombre)) {
            nombre = JOptionPane.showInputDialog("Nombre invalido. Ingrese solo letras:");
        }

        String apellido = JOptionPane.showInputDialog("Ingrese el apellido:");
        while (!Validaciones.esTextoValido(apellido)) {
            apellido = JOptionPane.showInputDialog("Apellido invalido. Ingrese solo letras:");
        }

        String edadS = JOptionPane.showInputDialog("Ingrese la edad:");
        while (!Validaciones.esEntero(edadS)) {
            edadS = JOptionPane.showInputDialog("Edad invalida. Ingrese solo numeros:");
        }
        int edad = Integer.parseInt(edadS);

        String direccion = JOptionPane.showInputDialog("Ingrese la direccion:");

        try {
            con.setQuery("insert into alumno values (" + cod + ",'" + nombre + "','"
                    + apellido + "'," + edad + ",'" + direccion + "')");
            JOptionPane.showMessageDialog(null, "Alumno ingresado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR al ingresar: " + e.getMessage());
        } finally {
            con.cerrarConexion();
        }
    }

    // ---------- READ ----------
    public void listar() throws SQLException {
        Conexion con = new Conexion();
        con.setRs("select * from alumno");
        ResultSet rs = con.getRs();

        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append("Cod: ").append(rs.getInt("cod_alumno"))
              .append(" | ").append(rs.getString("nombre")).append(" ").append(rs.getString("apellido"))
              .append(" | Edad: ").append(rs.getInt("edad"))
              .append(" | Direccion: ").append(rs.getString("direccion"))
              .append("\n");
        }
        if (sb.length() == 0) sb.append("No hay alumnos registrados.");

        JOptionPane.showMessageDialog(null, sb.toString(), "Listado de Alumnos", JOptionPane.INFORMATION_MESSAGE);
        con.cerrarConexion();
    }

    // ---------- UPDATE ----------
    public void actualizar() throws SQLException {
        Conexion con = new Conexion();

        String codS = JOptionPane.showInputDialog("Ingrese el codigo del alumno a modificar:");
        while (!Validaciones.esEntero(codS)) {
            codS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int cod = Integer.parseInt(codS);

        con.setRs("select * from alumno where cod_alumno=" + cod);
        if (!con.getRs().next()) {
            JOptionPane.showMessageDialog(null, "No existe un alumno con ese codigo");
            con.cerrarConexion();
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
        while (!Validaciones.esTextoValido(nombre)) {
            nombre = JOptionPane.showInputDialog("Nombre invalido. Ingrese solo letras:");
        }
        String apellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido:");
        while (!Validaciones.esTextoValido(apellido)) {
            apellido = JOptionPane.showInputDialog("Apellido invalido. Ingrese solo letras:");
        }
        String edadS = JOptionPane.showInputDialog("Ingrese la nueva edad:");
        while (!Validaciones.esEntero(edadS)) {
            edadS = JOptionPane.showInputDialog("Edad invalida. Ingrese solo numeros:");
        }
        int edad = Integer.parseInt(edadS);
        String direccion = JOptionPane.showInputDialog("Ingrese la nueva direccion:");

        try {
            con.setQuery("update alumno set nombre='" + nombre + "', apellido='" + apellido
                    + "', edad=" + edad + ", direccion='" + direccion
                    + "' where cod_alumno=" + cod);
            JOptionPane.showMessageDialog(null, "Alumno actualizado correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR al actualizar: " + e.getMessage());
        } finally {
            con.cerrarConexion();
        }
    }

    // ---------- DELETE ----------
    public void eliminar() throws SQLException {
        Conexion con = new Conexion();

        String codS = JOptionPane.showInputDialog("Ingrese el codigo del alumno a eliminar:");
        while (!Validaciones.esEntero(codS)) {
            codS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int cod = Integer.parseInt(codS);

        con.setRs("select * from alumno where cod_alumno=" + cod);
        if (!con.getRs().next()) {
            JOptionPane.showMessageDialog(null, "No existe un alumno con ese codigo");
            con.cerrarConexion();
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Esta seguro que desea eliminar al alumno " + cod + "?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                // primero se eliminan sus matriculas (llave foranea)
                con.setQuery("delete from alumno_materia where cod_alumno=" + cod);
                con.setQuery("delete from alumno where cod_alumno=" + cod);
                JOptionPane.showMessageDialog(null, "Alumno eliminado correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR al eliminar: " + e.getMessage());
            }
        }
        con.cerrarConexion();
    }
}
