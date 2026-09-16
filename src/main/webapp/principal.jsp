<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    HttpSession session_actual = request.getSession(false);
    String usuario = (session_actual == null) ? null : (String) session_actual.getAttribute("USER");
    String nombres = (session_actual == null) ? null : (String) session_actual.getAttribute("NAME");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<html lang="es">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Bienvenido <%=nombres%></title>
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container bg-info text-dark">
<h1 class="text-center">Bienvenido a POO404</h1>
<h2 class="text-center">
    Bienvenido: (<%=usuario%>) <%=nombres%>
</h2>

<div class="text-center mb-3">
    <a href="mantenimiento.jsp" class="btn btn-dark">
        Mantenimiento de tipos de usuario
    </a>
</div>

<form method="post" action="GenerarSession">
    <input type="hidden" value="salir" name="operacion">
    <input style="margin-left: 45%;" type="submit"
           value="Cerrar sesi&oacute;n" class="btn btn-success">
</form>
</body>
</html>
