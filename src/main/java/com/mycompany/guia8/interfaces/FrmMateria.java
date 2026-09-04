package com.mycompany.guia8.interfaces;

import com.mycompany.guia8.beans.MateriaBeans;
import com.mycompany.guia8.datos.MateriaDatos;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FrmMateria extends JFrame {

    private JTextField txtId, txtNombre, txtUnidades;
    private JButton btnGuardar, btnEliminar, btnLimpiar;
    private JTable tblDatos;
    private DefaultTableModel modelo;

    private MateriaBeans materiaBeans = null;
    private MateriaDatos materiaDatos = new MateriaDatos();

    public FrmMateria() {
        setTitle("Mantenimiento de Materias");
        setMinimumSize(new Dimension(650, 550));
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
        txtNombre = new JTextField();
        txtUnidades = new JTextField();

        int fila = 0;
        agregarCampo(panelForm, c, fila++, "Id:", txtId);
        agregarCampo(panelForm, c, fila++, "Ingrese nombre de materia:", txtNombre);
        agregarCampo(panelForm, c, fila++, "Ingrese unidades valorativas:", txtUnidades);

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
        modelo = materiaDatos.selectMateria();
        tblDatos.setModel(modelo);
    }

    private void guardarDatos() {
        try {
            int id = txtId.getText().isEmpty() ? 0 : Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            int unidades = Integer.parseInt(txtUnidades.getText());

            materiaBeans = new MateriaBeans(id, nombre, unidades);

            if (btnGuardar.getText().equals("Guardar")) {
                materiaDatos.insert(materiaBeans);
            } else if (btnGuardar.getText().equals("Editar")) {
                materiaDatos.update(materiaBeans);
            }

            modelo = materiaDatos.selectMateria();
            tblDatos.setModel(modelo);
            limpiarControles();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique que las unidades valorativas sean numericas.");
        }
    }

    private void eliminarDatos() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla primero.");
            return;
        }
        materiaDatos.delete(Integer.parseInt(txtId.getText()));
        limpiarControles();
        modelo = materiaDatos.selectMateria();
        tblDatos.setModel(modelo);
    }

    private void limpiarControles() {
        txtId.setText("");
        txtNombre.setText("");
        txtUnidades.setText("");
        btnGuardar.setText("Guardar");
    }

    private void obtenerFiladeTabla() {
        int fila = tblDatos.getSelectedRow();
        int columna = tblDatos.getSelectedColumn();
        if (fila > -1 && columna > -1) {
            txtId.setText(modelo.getValueAt(fila, 0).toString());
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtUnidades.setText(modelo.getValueAt(fila, 2).toString());
            btnGuardar.setText("Editar");
        }
    }
}

