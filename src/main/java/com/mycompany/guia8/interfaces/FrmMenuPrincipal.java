package com.mycompany.guia8.interfaces;

import java.awt.*;
import javax.swing.*;

public class FrmMenuPrincipal extends JFrame {

    public FrmMenuPrincipal() {
        setTitle("Sistema Academico - Ejercicios Complementarios Guia 8");
        setMinimumSize(new Dimension(420, 260));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titulo = new JLabel("Mantenimientos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton btnAlumno = new JButton("Mantenimiento de Alumnos");
        JButton btnMateria = new JButton("Mantenimiento de Materias");
        JButton btnAlumnoMateria = new JButton("Materias Inscritas por Alumno");

        btnAlumno.addActionListener(e -> new FrmAlumno().setVisible(true));
        btnMateria.addActionListener(e -> new FrmMateria().setVisible(true));
        btnAlumnoMateria.addActionListener(e -> new FrmAlumnoMateria().setVisible(true));

        JPanel contenedor = new JPanel(new GridLayout(4, 1, 10, 15));
        contenedor.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        contenedor.add(titulo);
        contenedor.add(btnAlumno);
        contenedor.add(btnMateria);
        contenedor.add(btnAlumnoMateria);
        setContentPane(contenedor);

        setLocationRelativeTo(null);
    }
}

