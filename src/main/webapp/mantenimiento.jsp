<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.example.guia9.Conexion" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    HttpSession session_actual = request.getSession(false);
    String usuario = (session_actual == null) ? null : (String) session_actual.getAttribute("USER");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    String mensaje = request.getParameter("mensaje");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mantenimiento de tipos de usuario</title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body class="container bg-info text-dark">
<h1 class="text-center mt-3">Mantenimiento de tipos de usuario</h1>
<p class="text-center">
    Aqui se agregan nuevos tipos de usuario (ocupaciones), por ejemplo
    <strong>"Jefe de Sistemas"</strong>. En cuanto se agregan, quedan disponibles
    de inmediato en el formulario de registro de <a href="login.jsp">login.jsp</a>.
</p>

<% if (mensaje != null) { %>
<div class="alert alert-success col-md-6 mx-auto text-center"><%= mensaje %></div>
<% } %>
<% if (error != null) { %>
<div class="alert alert-danger col-md-6 mx-auto text-center"><%= error %></div>
<% } %>

<div class="container bg-dark text-info p-3 col-md-6 rounded-3 mb-4">
    <form action="AgregarOcupacion" method="post">
        <div class="form-group">
            <label for="nombreOcupacion">Nombre del nuevo tipo de usuario:</label><br>
            <input type="text" name="nombreOcupacion" id="nombreOcupacion"
                   class="form-control" required>
        </div>
        <div class="form-group mt-3">
            <input type="submit" class="btn btn-info btn-md" value="Agregar">
        </div>
    </form>
</div>

<h3 class="text-center">Tipos de usuario existentes</h3>
<table class="table table-dark table-striped col-md-6 mx-auto">
    <thead>
    <tr><th>ID</th><th>Ocupacion</th></tr>
    </thead>
    <tbody>
    <%
        try {
            Conexion con = new Conexion();
            String sql = "SELECT id_ocupacion, ocupacion FROM ocupaciones ORDER BY ocupacion";
            PreparedStatement ps = con.getConexion().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
    %>
    <tr>
        <td><%= rs.getInt(1) %></td>
        <td><%= rs.getString(2) %></td>
    </tr>
    <%
            }
            rs.close();
            ps.close();
            con.cerrarConexion();
        } catch (Exception e) {
    %>
    <tr><td colspan="2">No se pudo cargar el listado.</td></tr>
    <% } %>
    </tbody>
</table>

<div class="text-center mb-4">
    <a href="principal.jsp" class="btn btn-light">Volver</a>
</div>
</body>
</html>
