package com.example.guia9;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet de mantenimiento: permite agregar nuevos tipos de usuario/ocupacion
 * (por ejemplo "Jefe de Sistemas"), sin tener que modificar codigo cada vez
 * que se necesite un tipo nuevo. Los nuevos valores quedan disponibles de
 * inmediato en el combo de "Ocupacion" del formulario de registro
 * (login.jsp), porque ese combo se carga dinamicamente desde la bdd.
 *
 * Requiere sesion iniciada (solo un usuario logueado puede dar mantenimiento).
 */
@WebServlet(name = "AgregarOcupacion", urlPatterns = {"/AgregarOcupacion"})
public class AgregarOcupacion extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final HttpSession sesionActual = request.getSession(false);
        if (sesionActual == null || sesionActual.getAttribute("USER") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        final String nombreOcupacion = request.getParameter("nombreOcupacion") == null
                ? null : request.getParameter("nombreOcupacion").trim();

        if (nombreOcupacion == null || nombreOcupacion.isEmpty()) {
            redirigir(response, "El nombre del nuevo tipo de usuario es obligatorio.", false);
            return;
        }
        if (!nombreOcupacion.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,50}")) {
            redirigir(response, "El nombre solo debe contener letras y espacios (2 a 50 caracteres).", false);
            return;
        }

        try {
            Conexion con = new Conexion();

            // Evitar duplicados
            final String sqlExiste = "SELECT COUNT(*) FROM ocupaciones WHERE ocupacion = ?";
            try (PreparedStatement psExiste = con.getConexion().prepareStatement(sqlExiste)) {
                psExiste.setString(1, nombreOcupacion);
                try (ResultSet rs = psExiste.executeQuery()) {
                    rs.next();
                    if (rs.getInt(1) > 0) {
                        redirigir(response, "Ese tipo de usuario ya existe.", false);
                        con.cerrarConexion();
                        return;
                    }
                }
            }

            final String sqlInsert = "INSERT INTO ocupaciones (ocupacion) VALUES (?)";
            try (PreparedStatement psInsert = con.getConexion().prepareStatement(sqlInsert)) {
                psInsert.setString(1, nombreOcupacion);
                psInsert.executeUpdate();
            }

            con.cerrarConexion();
            redirigir(response, "Tipo de usuario \"" + nombreOcupacion + "\" agregado correctamente.", true);

        } catch (SQLException e) {
            e.printStackTrace();
            redirigir(response, "Error al guardar en la base de datos.", false);
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("mantenimiento.jsp");
    }

    private void redirigir(final HttpServletResponse response, final String mensaje, final boolean ok)
            throws IOException {
        final String encoded = java.net.URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        final String param = ok ? "mensaje" : "error";
        response.sendRedirect("mantenimiento.jsp?" + param + "=" + encoded);
    }
}
