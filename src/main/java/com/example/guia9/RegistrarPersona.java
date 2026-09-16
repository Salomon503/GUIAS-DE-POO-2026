package com.example.guia9;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet de registro de personas.
 *
 * Cambios para la seccion IV (ANALISIS DE RESULTADOS):
 *  - Valida que todos los datos vengan completos y con formato correcto
 *    (nombre, edad, sexo, ocupacion, fecha de nacimiento, usuario, password).
 *  - Verifica que la ocupacion elegida exista realmente en la bdd (asi el
 *    formulario puede alimentarse dinamicamente, incluyendo el nuevo tipo
 *    "Jefe de Sistemas" u otros que se agreguen por mantenimiento).
 *  - Verifica que el usuario no este duplicado antes de insertar.
 *  - Usa PreparedStatement en vez de concatenar Strings (evita inyeccion SQL).
 *  - Si algo falla, regresa al login.jsp con un mensaje de error en vez de
 *    romper la aplicacion.
 */
@WebServlet(name = "RegistrarPersona", urlPatterns = {"/RegistrarPersona"})
public class RegistrarPersona extends HttpServlet {

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

        response.setContentType("text/html;charset=UTF-8");

        final String nombre = trim(request.getParameter("nombre"));
        final String edadStr = trim(request.getParameter("edad"));
        final String sexo = trim(request.getParameter("sexo"));
        final String fechaStr = trim(request.getParameter("fecha"));
        final String ocupacionStr = trim(request.getParameter("ocupacion"));
        final String usuario = trim(request.getParameter("usuario"));
        final String password = trim(request.getParameter("password"));

        final List<String> errores = new ArrayList<>();

        // ---- Nombre: obligatorio, solo letras/espacios/acentos ----
        if (nombre == null || nombre.isEmpty()) {
            errores.add("El nombre es obligatorio.");
        } else if (!nombre.matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]{2,100}")) {
            errores.add("El nombre solo debe contener letras y espacios (2 a 100 caracteres).");
        }

        // ---- Edad: obligatoria, numerica, rango razonable ----
        int edad = -1;
        if (edadStr == null || edadStr.isEmpty()) {
            errores.add("La edad es obligatoria.");
        } else {
            try {
                edad = Integer.parseInt(edadStr);
                if (edad < 1 || edad > 120) {
                    errores.add("La edad debe estar entre 1 y 120 anios.");
                }
            } catch (NumberFormatException e) {
                errores.add("La edad debe ser un numero entero.");
            }
        }

        // ---- Sexo: obligatorio, solo valores permitidos ----
        if (sexo == null || sexo.isEmpty()) {
            errores.add("El sexo es obligatorio.");
        } else if (!sexo.equals("Masculino") && !sexo.equals("Femenino")) {
            errores.add("El sexo indicado no es valido.");
        }

        // ---- Fecha de nacimiento: obligatoria, formato valido, no futura ----
        LocalDate fecha = null;
        if (fechaStr == null || fechaStr.isEmpty()) {
            errores.add("La fecha de nacimiento es obligatoria.");
        } else {
            try {
                fecha = LocalDate.parse(fechaStr); // formato yyyy-MM-dd (input type=date)
                if (fecha.isAfter(LocalDate.now())) {
                    errores.add("La fecha de nacimiento no puede ser futura.");
                }
            } catch (DateTimeParseException e) {
                errores.add("La fecha de nacimiento no tiene un formato valido.");
            }
        }

        // ---- Ocupacion: obligatoria, numerica, debe existir en la bdd ----
        int idOcupacion = -1;
        if (ocupacionStr == null || ocupacionStr.isEmpty()) {
            errores.add("Debe seleccionar una ocupacion.");
        } else {
            try {
                idOcupacion = Integer.parseInt(ocupacionStr);
            } catch (NumberFormatException e) {
                errores.add("La ocupacion seleccionada no es valida.");
            }
        }

        // ---- Usuario: obligatorio, alfanumerico ----
        if (usuario == null || usuario.isEmpty()) {
            errores.add("El usuario es obligatorio.");
        } else if (!usuario.matches("[A-Za-z0-9._-]{4,30}")) {
            errores.add("El usuario debe tener entre 4 y 30 caracteres (letras, numeros, punto, guion).");
        }

        // ---- Password: obligatorio, minimo de seguridad ----
        if (password == null || password.isEmpty()) {
            errores.add("La contrasena es obligatoria.");
        } else if (password.length() < 6) {
            errores.add("La contrasena debe tener al menos 6 caracteres.");
        }

        // Si hay errores de formato, no se toca la bdd
        if (!errores.isEmpty()) {
            redirigirConError(response, String.join(" ", errores));
            return;
        }

        Conexion con = null;
        try {
            con = new Conexion();

            // Verificar que la ocupacion exista realmente (soporta nuevos tipos
            // agregados desde el mantenimiento, p.ej. "Jefe de Sistemas")
            String nombreOcupacion = null;
            final String sqlOcup = "SELECT ocupacion FROM ocupaciones WHERE id_ocupacion = ?";
            try (PreparedStatement psOcup = con.getConexion().prepareStatement(sqlOcup)) {
                psOcup.setInt(1, idOcupacion);
                try (ResultSet rsOcup = psOcup.executeQuery()) {
                    if (rsOcup.next()) {
                        nombreOcupacion = rsOcup.getString(1);
                    }
                }
            }
            if (nombreOcupacion == null) {
                redirigirConError(response, "La ocupacion seleccionada no existe.");
                return;
            }

            // Verificar que el usuario no exista ya (dato unico)
            final String sqlExiste = "SELECT COUNT(*) FROM persona WHERE usuario = ?";
            try (PreparedStatement psExiste = con.getConexion().prepareStatement(sqlExiste)) {
                psExiste.setString(1, usuario);
                try (ResultSet rsExiste = psExiste.executeQuery()) {
                    rsExiste.next();
                    if (rsExiste.getInt(1) > 0) {
                        redirigirConError(response, "Ese nombre de usuario ya esta en uso.");
                        return;
                    }
                }
            }

            // Insertar usando PreparedStatement (sin concatenar valores del usuario)
            final String sqlInsert = "INSERT INTO persona "
                    + "(nombre_persona, edad_persona, sexo_persona, id_ocupacion, fecha_nac, usuario, contrasenia) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psInsert = con.getConexion().prepareStatement(sqlInsert)) {
                psInsert.setString(1, nombre);
                psInsert.setInt(2, edad);
                psInsert.setString(3, sexo);
                psInsert.setInt(4, idOcupacion);
                psInsert.setString(5, fechaStr);
                psInsert.setString(6, usuario);
                psInsert.setString(7, password);
                psInsert.executeUpdate();
            }

            con.cerrarConexion();

            // Pagina de confirmacion
            try (PrintWriter out = response.getWriter()) {
                out.println("<html>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>Registro " + escapeHtml(usuario) + "</title>");
                out.println("<link rel='stylesheet' "
                        + "href='https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css'>");
                out.println("</head>");
                out.println("<body class='bg-info text-dark'>");
                out.println("<h1 class='text-center'>Usted ha registrado un usuario exitosamente</h1>");
                out.println("<section class='container text-center'>");
                out.println("<h3>Nombre: </h3>" + escapeHtml(nombre));
                out.println("<h3>Edad: </h3>" + edad);
                out.println("<h3>Sexo: </h3>" + escapeHtml(sexo));
                out.println("<h3>Ocupaci&oacute;n: </h3>" + escapeHtml(nombreOcupacion));
                out.println("<h3>Fecha de nacimiento: </h3>" + fecha);
                out.println("<br><a class='btn btn-light' href='login.jsp'>Volver</a>");
                out.println("</section>");
                out.println("</body>");
                out.println("</html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            redirigirConError(response, "Error al guardar los datos en la base de datos.");
        }
    }

    private void redirigirConError(final HttpServletResponse response, final String mensaje) throws IOException {
        final String encoded = java.net.URLEncoder.encode(mensaje, StandardCharsets.UTF_8);
        response.sendRedirect("login.jsp?errorRegistro=" + encoded);
    }

    private String trim(final String valor) {
        return valor == null ? null : valor.trim();
    }

    private String escapeHtml(final String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
