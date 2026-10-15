<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="cientifico" scope="request" class="com.example.guia11bean.PersonitaBean">
    <jsp:setProperty name="cientifico" property="*"/>
</jsp:useBean>
<!DOCTYPE html>
<html>
<head>
    <title>jsp:useBean</title>
    <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container py-4">
    <div class="card border-primary">
        <div class="card-header text-bg-primary">Uso de jsp:useBean coordinado con parámetros de la petición</div>
        <div class="card-body">
            <p>El científico es: <mark><jsp:getProperty name="cientifico" property="nombre"/></mark>.<br>
               Su edad es: <mark><jsp:getProperty name="cientifico" property="edad"/></mark> años.</p>
            <p>A continuación usamos <code>getProperty</code> con los métodos
               <code>getTipo()</code> y <code>getJoven()</code>, que no tienen un campo propio en el Bean:</p>
            <ul>
                <li>Tipo: <mark><jsp:getProperty name="cientifico" property="tipo"/></mark></li>
                <li>¿Joven?: <mark><jsp:getProperty name="cientifico" property="joven"/></mark></li>
            </ul>
        </div>
    </div>
    <a href="EnviarPersona.jsp" class="btn btn-link mt-3">&larr; Volver</a>
</div>
</body>
</html>