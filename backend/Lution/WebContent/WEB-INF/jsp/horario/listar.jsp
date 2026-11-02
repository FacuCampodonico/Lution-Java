<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Horario" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Horarios</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Horario> lista = (LinkedList<Horario>) request.getAttribute("listaHorarios"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<a href="horario?action=new" class="btn btn-primary">Nuevo Horario</a>
<table class="table table-striped">
<tr>
    <th>ID</th>
    <th>Inicio</th>
    <th>Fin</th>
    <th>Intervalo (h)</th>
    <th>Acciones</th>
</tr>
<% for (Horario h : lista) { %>
    <tr>
        <td><%= h.getIdHorario() %></td>
        <td><%= h.getHoraInicio() %>:00</td>
        <td><%= h.getHoraFin() %>:00</td>
        <td><%= h.getIntervaloHoras() %></td>
        <td>
            <a href="horario?action=edit&idHorario=<%= h.getIdHorario() %>" class="btn btn-warning btn-sm">Editar</a>

            <form action="HorarioProcesar" method="post" style="display:inline"
                  onsubmit="return confirm('¿Estás seguro que querés eliminar al horario &quot;<%= h.getEtiqueta() %>&quot;?');">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="idHorario" value="<%= h.getIdHorario() %>">
                <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
            </form>
        </td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
