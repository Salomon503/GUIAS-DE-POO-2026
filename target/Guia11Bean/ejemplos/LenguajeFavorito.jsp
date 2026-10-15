<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lenguaje favorito</title>
    <meta charset="utf-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
</head>
<body>
<div class="container py-4">
    <div class="card border-primary mb-4">
        <div class="card-header text-bg-primary">Uso de JavaBean</div>
        <div class="card-body">
            <h1>P&aacute;gina de prueba del uso de beans</h1>
            <p>Se env&iacute;a el formulario al servicio cuyo fichero es <mark>Beans.jsp</mark></p>
        </div>
    </div>
    <form action="Beans.jsp" method="POST">
        <div class="mb-3">
            <label for="nombre" class="form-label fw-bold">Por favor, introduce tu nombre:</label>
            <input type="text" class="form-control" name="nombre" id="nombre" placeholder="Ingresa tu nombre" required>
        </div>
        <div class="mb-3">
            <label for="lenguaje" class="form-label fw-bold">¿Cuál es tu lenguaje de programación favorito?</label>
            <select class="form-select" name="lenguaje" id="lenguaje">
                <option value="Java">Java</option>
                <option value="C++">C++</option>
                <option value="Perl">Perl</option>
            </select>
        </div>
        <button type="submit" class="btn btn-info">Enviar</button>
        <a href="../index.jsp" class="btn btn-link">&larr; Inicio</a>
    </form>
</div>
</body>
</html>