<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lution - Iniciar sesión</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<div class="container mt-4">

<% String error = (String) request.getAttribute("error"); %>

<h2>Lution - Iniciar sesión</h2>

<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<form action="LoginProcesar" method="post">

    <label class="form-label">DNI:</label>
    <input type="text" class="form-control" name="dni" required>

    <label class="form-label">Contraseña:</label>
    <input type="password" class="form-control" name="password" required>

    <button type="submit" class="btn btn-primary">Ingresar</button>
    <a href="disponibilidad" class="btn btn-secondary">Volver</a>
</form>

</div>
</body>
</html>
