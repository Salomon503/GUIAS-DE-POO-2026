package com.example.javajframe.interfaceswing;

import com.example.javajframe.datos.GestorPersonasCSV;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Formulario "Datos de la Persona".
 *
 * NOTA: esta versión está escrita a mano (con GridBagLayout) en vez de
 * usar el diseñador visual de NetBeans (JFrameForm), para que puedas
 * probar YA los ejercicios complementarios sin depender de armar la
 * interfaz arrastrando controles. Los nombres de las variables
 * (txtId, txtNombre, txtEdad, txtTelefono, cmbSexo, tblDatos, modelo,
 * btnObtenerDatos, btnLimpiar) son los mismos que pide la Guía #6,
 * así que si luego rehaces el formulario con el diseñador visual,
 * la lógica de abajo (constructor, métodos, CSV) es la misma que debes
 * pegar ahí.
 */
public class frmPersona extends JFrame {

    // Controles del formulario
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtEdad;
    private JTextField txtTelefono;
    private JComboBox<String> cmbSexo;
    private JTable tblDatos;
    private JButton btnObtenerDatos;
    private JButton btnLimpiar;

    // Modelo de la tabla
    private DefaultTableModel modelo;

    // Ruta del archivo CSV dentro del paquete "datos" del proyecto
    private static final String RUTA_CSV =
            "src/main/java/com/example/javajframe/datos/personas.csv";

    public frmPersona(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(650, 550));
        this.setLocationRelativeTo(getParent());

        initComponents();

        // Ejercicio (a): cargar datos del CSV, solo se llama desde el constructor
        cargarDatosDesdeCSV();

        // Ejercicio (b): guarda automáticamente la tabla en el CSV al cerrar
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                guardarDatosEnCSV();
            }
        });
    }

    private void initComponents() {
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("Datos de la Persona");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);
        gbc.gridwidth = 1;

        txtId = new JTextField(15);
        txtNombre = new JTextField(15);
        txtEdad = new JTextField(15);
        txtTelefono = new JTextField(15);
        cmbSexo = new JComboBox<>(new String[]{"Masculino", "Femenino"});

        agregarFila(panelPrincipal, gbc, 1, "Ingrese su ID:", txtId);
        agregarFila(panelPrincipal, gbc, 2, "Ingrese su nombre:", txtNombre);
        agregarFila(panelPrincipal, gbc, 3, "Ingrese su edad:", txtEdad);
        agregarFila(panelPrincipal, gbc, 4, "Ingrese su teléfono:", txtTelefono);
        agregarFila(panelPrincipal, gbc, 5, "Seleccione su sexo:", cmbSexo);

        JPanel panelBotones = new JPanel(new FlowLayout());
        btnLimpiar = new JButton("Limpiar");
        btnObtenerDatos = new JButton("Obtener Datos");
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnObtenerDatos);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panelPrincipal.add(panelBotones, gbc);

        // Tabla
        String[] columnas = {"Id", "Nombres", "Edad", "Telefono", "Sexo"};
        modelo = new DefaultTableModel(null, columnas);
        tblDatos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tblDatos);
        scroll.setPreferredSize(new Dimension(580, 180));

        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        panelPrincipal.add(scroll, gbc);

        this.setContentPane(panelPrincipal);

        // Eventos
        btnObtenerDatos.addActionListener(e -> btnObtenerDatos());
        btnLimpiar.addActionListener(e -> btnLimpiar());

        // Clic en una fila de la tabla -> traslada los datos al formulario
        tblDatos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblObtenerFila(evt);
            }
        });
    }

    private void agregarFila(JPanel panel, GridBagConstraints gbc, int fila, String etiqueta, JComponent control) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.gridwidth = 1;
        panel.add(new JLabel(etiqueta), gbc);
        gbc.gridx = 1;
        panel.add(control, gbc);
    }

    // ------------------------------------------------------------
    // Lógica original de la Guía #6 (Parte 2)
    // ------------------------------------------------------------

    private void btnObtenerDatos() {
        String id = txtId.getText();
        String nombres = txtNombre.getText();
        String edad = txtEdad.getText();
        String telefono = txtTelefono.getText();
        String sexo = cmbSexo.getSelectedItem().toString();

        if (id.isBlank()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un Id.");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Datos obtenidos: \n ID: " + id +
                "\n Nombres: " + nombres +
                "\n Edad: " + edad +
                "\n Telefono: " + telefono +
                "\n Sexo: " + sexo);

        Object[] newRow = {id, nombres, edad, telefono, sexo};

        // Ejercicio (c): inserta si el Id no existe, o actualiza si ya existe
        insertarOActualizarPersona(newRow);

        btnLimpiar();
    }

    private void btnLimpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtEdad.setText("");
        txtTelefono.setText("");
        cmbSexo.setSelectedIndex(0);
        txtId.requestFocus();
    }

    private void tblObtenerFila(java.awt.event.MouseEvent e) {
        int fila = tblDatos.rowAtPoint(e.getPoint());
        int columna = tblDatos.columnAtPoint(e.getPoint());

        if ((fila > -1) && (columna > -1)) {
            txtId.setText(modelo.getValueAt(fila, 0).toString());
            txtNombre.setText(modelo.getValueAt(fila, 1).toString());
            txtEdad.setText(modelo.getValueAt(fila, 2).toString());
            txtTelefono.setText(modelo.getValueAt(fila, 3).toString());
            cmbSexo.setSelectedItem(modelo.getValueAt(fila, 4).toString());
        }
    }

    // ------------------------------------------------------------
    // Ejercicios complementarios (Parte IV de la guía)
    // ------------------------------------------------------------

    // a) Carga los registros del CSV (si existe) hacia la tabla tblDatos.
    //    Solo se invoca desde el constructor.
    private void cargarDatosDesdeCSV() {
        GestorPersonasCSV.cargarDesdeCSV(modelo, RUTA_CSV);
    }

    // b) Recorre todas las filas de tblDatos y las escribe en el CSV.
    private void guardarDatosEnCSV() {
        GestorPersonasCSV.guardarEnCSV(modelo, RUTA_CSV);
    }

    // c) Inserta o actualiza según si el Id ya existe en la tabla.
    private void insertarOActualizarPersona(Object[] datosPersona) {
        GestorPersonasCSV.insertarOActualizar(modelo, datosPersona);
    }
}
