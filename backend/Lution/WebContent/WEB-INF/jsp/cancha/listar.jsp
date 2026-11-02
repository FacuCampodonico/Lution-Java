<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Cancha" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Canchas por tipo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Cancha> lista = (LinkedList<Cancha>) request.getAttribute("listaCanchas"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<a href="cancha?action=new" class="btn btn-primary">Nueva Cancha</a>
<table class="table table-striped">
<tr>
    <th>Tipo</th>
    <th>N°</th>
    <th>Jugadores</th>
    <th>Precio</th>
    <th>Horario</th>
    <th>Acciones</th>
</tr>
<% for (Cancha c : lista) { %>
    <tr>
        <td><%= c.getTipoCancha().getNombre() %></td>
        <td><%= c.getNumero() %></td>
        <td><%= c.getTipoCancha().getCantMaxJugadores() %></td>
        <td><%= c.getTipoCancha().getPrecio() %></td>
        <td><%= c.getHorario().getEtiqueta() %></td>
        <td>
            <a href="cancha?action=edit&idCancha=<%= c.getIdCancha() %>" class="btn btn-warning btn-sm">Editar</a>

            <form action="CanchaProcesar" method="post" style="display:inline"
                  onsubmit="return confirm('¿Estás seguro que querés eliminar la cancha &quot;N° <%= c.getNumero() %>&quot;?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="idCancha" value="<%= c.getIdCancha() %>">
                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
            </form>
        </td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
