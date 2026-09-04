package com.mycompany.guia8.interfaces;

import com.mycompany.guia8.beans.AlumnoMateriaBeans;
import com.mycompany.guia8.datos.AlumnoDatos;
import com.mycompany.guia8.datos.AlumnoMateriaDatos;
import com.mycompany.guia8.datos.MateriaDatos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class FrmAlumnoMateria extends JFrame {

    private JTextField txtId, txtNota, txtFecha;
    private JComboBox<String> cmbAlumno, cmbMateria;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private JTable tblDatos;
    private DefaultTableModel modelo;

    private AlumnoMateriaBeans alumnoMateriaBeans = null;
    private AlumnoMateriaDatos alumnoMateriaDatos = new AlumnoMateriaDatos();
    private AlumnoDatos alumnoDatos = new AlumnoDatos();
    private MateriaDatos materiaDatos = new MateriaDatos();

    public FrmAlumnoMateria() {
        setTitle("Mantenimiento de Materias Inscritas por Alumno");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        construirFormulario();
        cargarDatosIniciales();
        setLocationRelativeTo(null);
    }

    private void construirFormulario() {
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        txtId = new JTextField();
        txtId.setEditable(false);
        cmbAlumno = new JComboBox<>();
        cmbMateria = new JComboBox<>();
        txtNota = new JTextField();
        txtFecha = new JTextField();
        txtFecha.setToolTipText("Formato sugerido: yyyy-MM-dd");

        int fila = 0;
        agregarCampo(panelForm, c, fila++, "Id:", txtId);
        agregarCampo(panelForm, c, fila++, "Seleccione al alumno:", cmbAlumno);
        agregarCampo(panelForm, c, fila++, "Seleccione la materia:", cmbMateria);
        agregarCampo(panelForm, c, fila++, "Ingrese la nota:", txtNota);
        agregarCampo(panelForm, c, fila++, "Ingrese fecha de inscripcion:", txtFecha);

        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        btnGuardar.addActionListener(e -> guardarDatos());
        btnEliminar.addActionListener(e -> eliminarDatos());
        btnLimpiar.addActionListener(e -> limpiarControles());

        modelo = new DefaultTableModel();
        tblDatos = new JTable(modelo);
        tblDatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                obtenerFiladeTabla();
            }
        });
        JScrollPane scroll = new JScrollPane(tblDatos);

        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(panelForm, BorderLayout.NORTH);
        contenedor.add(panelBotones, BorderLayout.CENTER);
        contenedor.add(scroll, BorderLayout.SOUTH);
        setContentPane(contenedor);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints c, int fila, String etiqueta, JComponent campo) {
        c.gridx = 0;
        c.gridy = fila;
        c.weightx = 0.4;
        panel.add(new JLabel(etiqueta), c);
        c.gridx = 1;
        c.weightx = 0.6;
        panel.add(campo, c);
    }

    private void cargarDatosIniciales() {
        cmbAlumno.setModel(alumnoDatos.selectAlumnosCombo());
        cmbMateria.setModel(materiaDatos.selectMateriasCombo());
        cargarTabla();
    }

    private void cargarTabla() {
        modelo = alumnoMateriaDatos.selectAlumnoMateria();
        tblDatos.setModel(modelo);
        ocultarColumnasId();
    }

    // Oculta en la vista las columnas id_alumno / id_materia (indices 5 y 6),
    // que se usan internamente para reconstruir el registro al editar.
    private void ocultarColumnasId() {
        if (tblDatos.getColumnModel().getColumnCount() >= 7) {
            TableColumn colIdAlumno = tblDatos.getColumnModel().getColumn(6);
            TableColumn colIdMateria = tblDatos.getColumnModel().getColumn(5);
            tblDatos.getColumnModel().removeColumn(colIdAlumno);
            tblDatos.getColumnModel().removeColumn(colIdMateria);
        }
    }

    // Extrae el id numerico del texto "id - nombre" del combobox
    private int extraerId(String textoCombo) {
        return Integer.parseInt(textoCombo.split(" - ")[0].trim());
    }

    private void guardarDatos() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            int idAlumno = extraerId((String) cmbAlumno.getSelectedItem());
            int idMateria = extraerId((String) cmbMateria.getSelectedItem());
            double nota = Double.parseDouble(txtNota.getText());
            String fecha = txtFecha.getText();

            alumnoMateriaBeans = new AlumnoMateriaBeans(id, idAlumno, idMateria, nota, fecha);

            if (btnGuardar.getText().equals("Guardar")) {
                alumnoMateriaDatos.insert(alumnoMateriaBeans);
            } else if (btnGuardar.getText().equals("Editar")) {
                alumnoMateriaDatos.update(alumnoMateriaBeans);
            }

            cargarTabla();
            limpiarControles();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique que la nota sea un valor numerico.");
        }
    }

    private void eliminarDatos() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla primero.");
            return;
        }
        alumnoMateriaDatos.delete(Integer.parseInt(txtId.getText()));
        limpiarControles();
        cargarTabla();
    }

    private void limpiarControles() {
        txtId.setText("");
        if (cmbAlumno.getItemCount() > 0) {
            cmbAlumno.setSelectedIndex(0);
        }
        if (cmbMateria.getItemCount() > 0) {
            cmbMateria.setSelectedIndex(0);
        }
        txtNota.setText("");
        txtFecha.setText("");
        btnGuardar.setText("Guardar");
    }

    private void obtenerFiladeTabla() {
        int fila = tblDatos.getSelectedRow();
        int columna = tblDatos.getSelectedColumn();
        if (fila > -1 && columna > -1) {
            // El modelo subyacente conserva todas las columnas aunque se
            // hayan ocultado en la vista, por eso se accede a "modelo" directo.
            txtId.setText(modelo.getValueAt(fila, 0).toString());
            int idAlumno = Integer.parseInt(modelo.getValueAt(fila, 5).toString());
            int idMateria = Integer.parseInt(modelo.getValueAt(fila, 6).toString());
            seleccionarEnCombo(cmbAlumno, idAlumno);
            seleccionarEnCombo(cmbMateria, idMateria);
            txtNota.setText(modelo.getValueAt(fila, 3).toString());
            txtFecha.setText(modelo.getValueAt(fila, 4) == null ? "" : modelo.getValueAt(fila, 4).toString());
            btnGuardar.setText("Editar");
        }
    }

    // Ubica en el combobox el elemento cuyo id (antes del " - ") coincide
    private void seleccionarEnCombo(JComboBox<String> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (extraerId(combo.getItemAt(i)) == id) {
                combo.setSelectedIndex(i);
                break;
            }
        }
    }
}

