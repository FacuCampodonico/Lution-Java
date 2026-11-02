<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Mantenimiento" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mantenimientos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Mantenimiento> lista = (LinkedList<Mantenimiento>) request.getAttribute("listaMantenimientos"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<a href="mantenimiento?action=new" class="btn btn-primary">Nuevo Mantenimiento</a>
<table class="table table-striped">
<tr>
    <th>ID</th>
    <th>Cancha</th>
    <th>Fecha</th>
    <th>Desde</th>
    <th>Hasta</th>
    <th>Descripción</th>
    <th>Acciones</th>
</tr>
<% for (Mantenimiento m : lista) { %>
    <tr>
        <td><%= m.getIdMantenimiento() %></td>
        <td><%= "N° " + m.getCancha().getNumero() %></td>
        <td><%= m.getFecha() %></td>
        <td><%= m.getHoraInicio() %>:00</td>
        <td><%= m.getHoraFin() %>:00</td>
        <td><%= m.getDescripcion() %></td>
        <td>
            <a href="mantenimiento?action=edit&idMantenimiento=<%= m.getIdMantenimiento() %>" class="btn btn-warning btn-sm">Editar</a>

            <form action="MantenimientoProcesar" method="post" style="display:inline"
                  onsubmit="return confirm('¿Estás seguro que querés eliminar al mantenimiento &quot;<%= m.getDescripcion() %>&quot;?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="idMantenimiento" value="<%= m.getIdMantenimiento() %>">
                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
            </form>
        </td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
