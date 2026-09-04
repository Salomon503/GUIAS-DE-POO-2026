package com.mycompany.guia8.interfaces;

import com.mycompany.guia8.beans.AlumnoBeans;
import com.mycompany.guia8.datos.AlumnoDatos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmAlumno extends JFrame {

    private JTextField txtId, txtNombre, txtEdad, txtCarrera, txtTelefono;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private JTable tblDatos;
    private DefaultTableModel modelo;

    private AlumnoBeans alumnoBeans = null;
    private AlumnoDatos alumnoDatos = new AlumnoDatos();

    public FrmAlumno() {
        setTitle("Mantenimiento de Alumnos");
        setMinimumSize(new Dimension(750, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
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
        txtNombre = new JTextField();
        txtEdad = new JTextField();
        txtCarrera = new JTextField();
        txtTelefono = new JTextField();

        int fila = 0;
        agregarCampo(panelForm, c, fila++, "Id:", txtId);
        agregarCampo(panelForm, c, fila++, "Ingrese su nombre:", txtNombre);
        agregarCampo(panelForm, c, fila++, "Ingrese su edad:", txtEdad);
        agregarCampo(panelForm, c, fila++, "Ingrese su carrera:", txtCarrera);
        agregarCampo(panelForm, c, fila++, "Ingrese su telefono:", txtTelefono);

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
        c.weightx = 0.3;
        panel.add(new JLabel(etiqueta), c);
        c.gridx = 1;
        c.weightx = 0.7;
        panel.add(campo, c);
    }

    private void cargarDatosIniciales() {
        modelo = alumnoDatos.selectAlumno();
        tblDatos.setModel(modelo);
    }

    private void guardarDatos() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String carrera = txtCarrera.getText();
            String telefono = txtTelefono.getText();

            alumnoBeans = new AlumnoBeans(id, nombre, edad, carrera, telefono);

            if (btnGuardar.getText().equals("Guardar")) {
                alumnoDatos.insert(alumnoBeans);
            } else if (btnGuardar.getText().equals("Editar")) {
                alumnoDatos.update(alumnoBeans);
            }

            modelo = alumnoDatos.selectAlumno();
            tblDatos.setModel(modelo);
            limpiarControles();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique que la edad sea numerica.");
        }
    }

    private void eliminarDatos() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla primero.");
            return;
        }
        alumnoDatos.delete(Integer.parseInt(txtId.getText()));
        limpiarControles();
        modelo = alumnoDatos.selectAlumno();
        tblDatos.setModel(modelo);
    }

    private void limpiarControles() {
        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtCarrera.setText("");
        txtTelefono.setText("");
        btnGuardar.setText("Guardar");
    }

    private void obtenerFiladeTabla() {
        int fila = tblDatos.getSelectedRow();
        int columna = tblDatos.getSelectedColumn();
        if (fila > -1 && columna > -1) {
            txtId.setText(modelo.getValueAt(fila, 0).toString());
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtEdad.setText(modelo.getValueAt(fila, 2).toString());
            txtCarrera.setText(modelo.getValueAt(fila, 3).toString());
            txtTelefono.setText(modelo.getValueAt(fila, 4) == null ? "" : modelo.getValueAt(fila, 4).toString());
            btnGuardar.setText("Editar");
        }
    }
}

