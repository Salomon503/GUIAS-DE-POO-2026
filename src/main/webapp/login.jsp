<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.guia9.Conexion" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>login</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container bg-info text-dark" >
<img style="width: 70%" src="">
<div id="login">
    <h1 class="text-center text-white pt-5">Universidad Don Bosco</h1>

    <%-- Mensajes de error de login o de registro (seccion IV: validaciones) --%>
    <% String errorLogin = request.getParameter("error");
       String errorRegistro = request.getParameter("errorRegistro"); %>
    <% if (errorLogin != null) { %>
    <div class="alert alert-danger text-center col-md-6 mx-auto"><%= errorLogin %></div>
    <% } %>
    <% if (errorRegistro != null) { %>
    <div class="alert alert-danger text-center col-md-6 mx-auto"><%= errorRegistro %></div>
    <% } %>

    <div class="container bg-dark text-info p-3 col-md-6 rounded-3 ">
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-8">
                <div id="login-box" class="col-md-24">
                    <form id="login-form" class="form" action="GenerarSession"
                          method="post">
                        <h3 class="text-center text-info">Login</h3>
                        <div class="form-group">
                            <label for="username" class="text-info">Usuario:
                            </label><br>
                            <input type="text" name="usuario" id="username"
                                   class="form-control">
                        </div>
                        <div class="form-group">
                            <label for="password"
                                   class="text-info">Password:</label><br>
                            <input type="password" name="password" id="contrasena"
                                   class="form-control">
                        </div>
                        <div class="form-group mt-3 ">
                            <input type="submit" name="operacion"
                                   class="btn btn-info btn-md" value="logueo">
                            <input type="hidden" name="operacion" value="logueo">
                        </div>
                        <br>
                        <div id="register-link" class="text-right">
                            <a style="cursor: pointer;" data-bs-toggle="modal"
                               data-bs-target="#exampleModal"
                               class="text-info">Registrese aquí</a>
                        </div>
                    </form>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModal" tabindex="-1"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">
                                        Registro personas</h5>
                                    <button type="button" class="btn-close"
                                            data-bs-dismiss="modal"
                                            aria-label="Close">X</button>
                                </div>
                                <div class="modal-body">
                                    <form id="save-form" class="form"
                                          action="RegistrarPersona" method="post">
                                        <h3 class="text-center text-info">
                                            Registro</h3>

                                        <div class="form-group">
                                            <label for="nombre" class="text-info">
                                                Nombre persona:</label><br>
                                            <input type="text" name="nombre"
                                                   id="nombre" class="form-control" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="edad" class="text-info">
                                                Edad persona:</label><br>
                                            <input type="number" name="edad" min="1" max="120"
                                                   id="edad" class="form-control" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="sexo" class="text-info">
                                                Sexo persona:</label><br>
                                            <select class="form-control"
                                                    name="sexo" id="sexo">
                                                <option value="Masculino">
                                                    Masculino</option>
                                                <option value="Femenino">
                                                    Femenino</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label for="ocupacion" class="text-info">
                                                Ocupacion persona:</label><br>
                                            <%-- Combo dinamico: se alimenta de la tabla
                                                 "ocupaciones" en la bdd, por lo que un
                                                 tipo nuevo (ej. "Jefe de Sistemas") agregado
                                                 desde mantenimiento.jsp aparece aqui sin
                                                 tocar codigo. --%>
                                            <select class="form-control"
                                                    name="ocupacion" id="ocupacion">
                                                <%
                                                    try {
                                                        Conexion conJsp = new Conexion();
                                                        String sqlOcup =
                                                            "SELECT id_ocupacion, ocupacion "
                                                            + "FROM ocupaciones ORDER BY ocupacion";
                                                        PreparedStatement psOcup =
                                                            conJsp.getConexion().prepareStatement(sqlOcup);
                                                        ResultSet rsOcup = psOcup.executeQuery();
                                                        while (rsOcup.next()) {
                                                %>
                                                <option value="<%= rsOcup.getInt(1) %>">
                                                    <%= rsOcup.getString(2) %>
                                                </option>
                                                <%
                                                        }
                                                        rsOcup.close();
                                                        psOcup.close();
                                                        conJsp.cerrarConexion();
                                                    } catch (Exception ex) {
                                                %>
                                                <option value="">No se pudieron cargar las ocupaciones</option>
                                                <% } %>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label for="fecha" class="text-info">
                                                Fecha nacimiento persona:</label><br>
                                            <input type="date" name="fecha"
                                                   id="fecha" class="form-control" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="usuario"
                                                   class="text-info">Usuario:</label><br>
                                            <input type="text" name="usuario"
                                                   id="usuario" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="password"
                                                   class="text-info">Password:</label><br>
                                            <input type="password" name="password"
                                                   id="password" class="form-control" required>
                                        </div>
                                        <div class="form-group">
                                        </div>
                                        <br>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary"
                                            data-bs-dismiss="modal">Close</button>
                                    <input type="submit" name="submit"
                                           class="btn btn-info btn-md"
                                           form="save-form" value="Enviar">
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<% if (errorRegistro != null) { %>
<script>
    // Si el error viene del formulario de registro, reabrimos el modal
    window.addEventListener('DOMContentLoaded', function () {
        var modal = new bootstrap.Modal(document.getElementById('exampleModal'));
        modal.show();
    });
</script>
<% } %>
</body>
</html>
