package modelos;

import conexiones.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * CRUD para la tabla "materia".
 */
public class MateriaDAO {

    // ---------- CREATE ----------
    public void crear() throws SQLException {
        Conexion con = new Conexion();

        String codS = JOptionPane.showInputDialog("Ingrese el codigo de la materia:");
        while (!Validaciones.esEntero(codS)) {
            codS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int cod = Integer.parseInt(codS);

        String nombre = JOptionPane.showInputDialog("Ingrese el nombre de la materia:");
        while (!Validaciones.noVacio(nombre)) {
            nombre = JOptionPane.showInputDialog("El nombre no puede estar vacio:");
        }

        String descripcion = JOptionPane.showInputDialog("Ingrese la descripcion:");

        try {
            con.setQuery("insert into materia values (" + cod + ",'" + nombre + "','" + descripcion + "')");
            JOptionPane.showMessageDialog(null, "Materia ingresada correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR al ingresar: " + e.getMessage());
        } finally {
            con.cerrarConexion();
        }
    }

    // ---------- READ ----------
    public void listar() throws SQLException {
        Conexion con = new Conexion();
        con.setRs("select * from materia");
        ResultSet rs = con.getRs();

        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append("Cod: ").append(rs.getInt("cod_materia"))
              .append(" | Nombre: ").append(rs.getString("nombre"))
              .append(" | Descripcion: ").append(rs.getString("descripcion"))
              .append("\n");
        }
        if (sb.length() == 0) sb.append("No hay materias registradas.");

        JOptionPane.showMessageDialog(null, sb.toString(), "Listado de Materias", JOptionPane.INFORMATION_MESSAGE);
        con.cerrarConexion();
    }

    // ---------- UPDATE ----------
    public void actualizar() throws SQLException {
        Conexion con = new Conexion();

        String codS = JOptionPane.showInputDialog("Ingrese el codigo de la materia a modificar:");
        while (!Validaciones.esEntero(codS)) {
            codS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int cod = Integer.parseInt(codS);

        con.setRs("select * from materia where cod_materia=" + cod);
        if (!con.getRs().next()) {
            JOptionPane.showMessageDialog(null, "No existe una materia con ese codigo");
            con.cerrarConexion();
            return;
        }

        String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:");
        while (!Validaciones.noVacio(nombre)) {
            nombre = JOptionPane.showInputDialog("El nombre no puede estar vacio:");
        }
        String descripcion = JOptionPane.showInputDialog("Ingrese la nueva descripcion:");

        try {
            con.setQuery("update materia set nombre='" + nombre + "', descripcion='" + descripcion
                    + "' where cod_materia=" + cod);
            JOptionPane.showMessageDialog(null, "Materia actualizada correctamente");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ERROR al actualizar: " + e.getMessage());
        } finally {
            con.cerrarConexion();
        }
    }

    // ---------- DELETE ----------
    public void eliminar() throws SQLException {
        Conexion con = new Conexion();

        String codS = JOptionPane.showInputDialog("Ingrese el codigo de la materia a eliminar:");
        while (!Validaciones.esEntero(codS)) {
            codS = JOptionPane.showInputDialog("Codigo invalido. Ingrese solo numeros:");
        }
        int cod = Integer.parseInt(codS);

        con.setRs("select * from materia where cod_materia=" + cod);
        if (!con.getRs().next()) {
            JOptionPane.showMessageDialog(null, "No existe una materia con ese codigo");
            con.cerrarConexion();
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(null,
                "¿Esta seguro que desea eliminar la materia " + cod + "?",
                "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

        if (confirmar == JOptionPane.YES_OPTION) {
            try {
                con.setQuery("delete from alumno_materia where cod_materia=" + cod);
                con.setQuery("delete from materia where cod_materia=" + cod);
                JOptionPane.showMessageDialog(null, "Materia eliminada correctamente");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR al eliminar: " + e.getMessage());
            }
        }
        con.cerrarConexion();
    }
}
