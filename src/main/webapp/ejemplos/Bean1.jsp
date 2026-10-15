<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String nom_cli = "Rafael";
    String ape_cli = "Torres";
    String dir_cli = "My_House";
%>
<jsp:useBean id="cli_bean" class="com.example.guia11bean.ClienteBean"/>
<jsp:setProperty name="cli_bean" property="nomb_cli" value="<%=nom_cli%>"/>
<jsp:setProperty name="cli_bean" property="ape_cli" value="<%=ape_cli%>"/>
<jsp:setProperty name="cli_bean" property="dir_cli" value="<%=dir_cli%>"/>
<!DOCTYPE html>
<html>
<head>
    <title>Bean1</title>
    <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container py-4">
    <div class="card border-primary">
        <div class="card-header text-bg-primary">Datos personales</div>
        <div class="card-body">
            <h3>Nombre: <jsp:getProperty name="cli_bean" property="nomb_cli"/></h3>
            <h3>Apellido: <jsp:getProperty name="cli_bean" property="ape_cli"/></h3>
            <h3>Direcci&oacute;n: <jsp:getProperty name="cli_bean" property="dir_cli"/></h3>
        </div>
    </div>
    <a href="../index.jsp" class="btn btn-link mt-3">&larr; Volver al inicio</a>
</div>
</body>
</html>