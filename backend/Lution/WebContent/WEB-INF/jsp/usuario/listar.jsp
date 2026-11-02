<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Usuario" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuarios</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Usuario> lista = (LinkedList<Usuario>) request.getAttribute("listaUsuarios"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<a href="usuario?action=new" class="btn btn-primary">Nuevo Usuario</a>
<table class="table table-striped">
<tr>
    <th>ID</th>
    <th>DNI</th>
    <th>Nombre</th>
    <th>Apellido</th>
    <th>Rol</th>
    <th>Acciones</th>
</tr>
<% for (Usuario u : lista) { %>
    <tr>
        <td><%= u.getIdUsuario() %></td>
        <td><%= u.getDni() %></td>
        <td><%= u.getNombre() %></td>
        <td><%= u.getApellido() %></td>
        <td><%= u.getRol() %></td>
        <td>
            <a href="usuario?action=edit&idUsuario=<%= u.getIdUsuario() %>" class="btn btn-warning btn-sm">Editar</a>

            <form action="UsuarioProcesar" method="post" style="display:inline"
                  onsubmit="return confirm('¿Estás seguro que querés eliminar al usuario &quot;<%= u.getNombre() %> <%= u.getApellido() %>&quot;?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="idUsuario" value="<%= u.getIdUsuario() %>">
                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
            </form>
        </td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
