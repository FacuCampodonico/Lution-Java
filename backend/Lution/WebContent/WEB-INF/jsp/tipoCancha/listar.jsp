<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.TipoCancha" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Tipos de cancha</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<TipoCancha> lista = (LinkedList<TipoCancha>) request.getAttribute("listaTiposCancha"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<a href="tipoCancha?action=new" class="btn btn-primary">Nuevo Tipo de Cancha</a>
<table class="table table-striped">
<tr>
    <th>ID</th>
    <th>Nombre</th>
    <th>Descripción</th>
    <th>Jugadores</th>
    <th>Precio</th>
    <th>Acciones</th>
</tr>
<% for (TipoCancha t : lista) { %>
    <tr>
        <td><%= t.getIdTipoCancha() %></td>
        <td><%= t.getNombre() %></td>
        <td><%= (t.getDescripcion() != null) ? t.getDescripcion() : "-" %></td>
        <td><%= t.getCantMaxJugadores() %></td>
        <td><%= t.getPrecio() %></td>
        <td>
            <a href="tipoCancha?action=edit&idTipoCancha=<%= t.getIdTipoCancha() %>" class="btn btn-warning btn-sm">Editar</a>

            <form action="TipoCanchaProcesar" method="post" style="display:inline"
                  onsubmit="return confirm('¿Estás seguro que querés eliminar al tipo de cancha &quot;<%= t.getNombre() %>&quot;?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="idTipoCancha" value="<%= t.getIdTipoCancha() %>">
                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
            </form>
        </td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
