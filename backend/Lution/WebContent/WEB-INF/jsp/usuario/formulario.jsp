<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="entities.Usuario" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Procesos Usuario</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% Usuario usuario = (Usuario) request.getAttribute("usuario"); %>

<form action="UsuarioProcesar" method="post">

    <input type="hidden" name="idUsuario" value="<%= (usuario != null) ? usuario.getIdUsuario() : "" %>">

    <label class="form-label">DNI:</label>
    <input type="text" class="form-control" name="dni" required value="<%= (usuario != null) ? usuario.getDni() : "" %>">

    <label class="form-label">Nombre:</label>
    <input type="text" class="form-control" name="nombre" required value="<%= (usuario != null) ? usuario.getNombre() : "" %>">

    <label class="form-label">Apellido:</label>
    <input type="text" class="form-control" name="apellido" required value="<%= (usuario != null) ? usuario.getApellido() : "" %>">

    <label class="form-label">Contraseña (solo administradores):</label>
    <input type="password" class="form-control" name="password">

    <label class="form-label">Rol:</label>
    <select class="form-control" name="rol" required>
        <% String[] opciones = {"Administrador", "Cliente"}; %>
        <% for (String op : opciones) { %>
            <option value="<%= op %>" <%= (usuario != null && op.equals(usuario.getRol())) ? "selected" : "" %>><%= op %></option>
        <% } %>
    </select>

    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="usuario" class="btn btn-secondary">Cancelar</a>
</form>

</body>
</html>
