<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="lenguajeBean" scope="page" class="com.example.guia11bean.LenguajeBean"/>
<jsp:setProperty name="lenguajeBean" property="*"/>
<!DOCTYPE html>
<html>
<head>
    <title>Resultado de prueba del uso de beans</title>
    <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container py-4">
    <div class="card border-primary">
        <div class="card-header text-bg-primary">Resultado</div>
        <div class="card-body">
            <p>Hola: <mark><jsp:getProperty name="lenguajeBean" property="nombre"/></mark>.</p>
            <p>Tu lenguaje favorito es: <mark><jsp:getProperty name="lenguajeBean" property="lenguaje"/></mark></p>
            <p>Mis comentarios acerca del lenguaje son:</p>
            <p class="bg-info-subtle p-2"><jsp:getProperty name="lenguajeBean" property="comentarios"/></p>
        </div>
    </div>
    <a href="LenguajeFavorito.jsp" class="btn btn-link mt-3">&larr; Volver</a>
</div>
</body>
</html>