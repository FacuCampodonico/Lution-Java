<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Reserva" %>
<%@ page import="entities.TipoCancha" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Horarios disponibles</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Reserva> lista = (LinkedList<Reserva>) request.getAttribute("listaTurnos"); %>
<% LinkedList<TipoCancha> listaTiposCancha = (LinkedList<TipoCancha>) request.getAttribute("listaTiposCancha"); %>
<% String fechaFiltro = (String) request.getAttribute("fechaFiltro"); %>
<% Integer idTipoCanchaFiltro = (Integer) request.getAttribute("idTipoCanchaFiltro"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<form action="disponibilidad" method="get" class="row g-2 mb-3">
    <div class="col-auto">
        <label class="form-label">Fecha:</label>
        <input type="date" class="form-control" name="fecha" value="<%= fechaFiltro %>">
    </div>
    <div class="col-auto">
        <label class="form-label">Deporte:</label>
        <select class="form-control" name="idTipoCancha">
            <option value="">Todos los deportes</option>
            <% for (TipoCancha t : listaTiposCancha) { %>
                <option value="<%= t.getIdTipoCancha() %>" <%= (idTipoCanchaFiltro != null && idTipoCanchaFiltro == t.getIdTipoCancha()) ? "selected" : "" %>><%= t.getNombre() %></option>
            <% } %>
        </select>
    </div>
    <div class="col-auto align-self-end">
        <button type="submit" class="btn btn-primary">Buscar</button>
    </div>
</form>

<table class="table table-striped">
<tr>
    <th>Fecha</th>
    <th>Turno</th>
    <th>Cancha</th>
    <th>Deporte</th>
    <th>Jugadores</th>
    <th>Precio</th>
    <th>Acciones</th>
</tr>
<% if (lista.isEmpty()) { %>
    <tr>
        <td colspan="7">No hay turnos disponibles para los filtros elegidos.</td>
    </tr>
<% } %>
<% for (Reserva r : lista) { %>
    <tr>
        <td><%= r.getFecha() %></td>
        <td><%= r.getHoraInicio() + ":00 - " + r.getHoraFin() + ":00" %></td>
        <td><%= "N° " + r.getCancha().getNumero() %></td>
        <td><%= r.getCancha().getTipoCancha().getNombre() %></td>
        <td><%= r.getCancha().getTipoCancha().getCantMaxJugadores() %></td>
        <td><%= r.getCancha().getTipoCancha().getPrecio() %></td>
        <td>
            <a href="reserva?action=new&idCancha=<%= r.getCancha().getIdCancha() %>&fecha=<%= r.getFecha() %>&horaInicio=<%= r.getHoraInicio() %>" class="btn btn-primary btn-sm">Reservar</a>
        </td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
