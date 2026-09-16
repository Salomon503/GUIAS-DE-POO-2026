package com.example.guia9;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "GenerarSession", urlPatterns = {"/GenerarSession"})
public class GenerarSession extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession sessionActual = request.getSession(true);
        final String operacion = request.getParameter("operacion");

        if ("salir".equals(operacion)) {
            sessionActual.setAttribute("USER", null);
            sessionActual.setAttribute("NAME", null);
            // principal.jsp detecta que ya no hay usuario y redirige a login.jsp
            response.sendRedirect("principal.jsp");
        } else if ("logueo".equals(operacion)) {

            final String usuario = request.getParameter("usuario");
            final String password = request.getParameter("password");

            // Validacion minima de entrada (dato completo)
            if (usuario == null || usuario.isBlank() || password == null || password.isBlank()) {
                response.sendRedirect("login.jsp?error=" + encode("Debe ingresar usuario y contrasena."));
                return;
            }

            try {
                Conexion con = new Conexion();
                // PreparedStatement: los valores del usuario nunca se concatenan al SQL
                final String sql = "SELECT count(usuario), nombre_persona FROM persona "
                        + "WHERE usuario = ? AND contrasenia = ?";
                try (PreparedStatement ps = con.getConexion().prepareStatement(sql)) {
                    ps.setString(1, usuario);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) == 1) { // solo una coincidencia es permitida
                            sessionActual.setAttribute("USER", usuario);
                            sessionActual.setAttribute("NAME", rs.getString(2));
                            response.sendRedirect("principal.jsp");
                        } else {
                            response.sendRedirect("login.jsp?error="
                                    + encode("Usuario o contrasena incorrectos."));
                        }
                    }
                }
                con.cerrarConexion();
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("login.jsp?error=" + encode("Error de conexion con la base de datos."));
            }
        }
    }

    private String encode(final String texto) {
        return java.net.URLEncoder.encode(texto, java.nio.charset.StandardCharsets.UTF_8);
    }
}
