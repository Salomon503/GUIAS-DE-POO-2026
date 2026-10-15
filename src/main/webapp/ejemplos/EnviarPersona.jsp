<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Personas</title>
    <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container py-4">
    <h3>Personas</h3>
    <form name="persona" action="Persona.jsp" method="POST">
        <div class="mb-3">
            <label for="nombre" class="form-label">Ingrese su nombre:</label>
            <input type="text" class="form-control" name="nombre" id="nombre" placeholder="Nombre" required>
        </div>
        <div class="mb-3">
            <label for="edad" class="form-label">Ingrese su edad:</label>
            <input type="number" class="form-control" name="edad" id="edad" placeholder="Edad" min="1" max="120" required>
        </div>
        <button type="submit" class="btn btn-info">Enviar</button>
        <a href="../index.jsp" class="btn btn-link">&larr; Inicio</a>
    </form>
</div>
</body>
</html>