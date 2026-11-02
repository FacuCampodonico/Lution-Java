<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Reserva" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Reservas</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Reserva> lista = (LinkedList<Reserva>) request.getAttribute("listaReservas"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<a href="reserva?action=new" class="btn btn-primary">Nueva Reserva</a>
<table class="table table-striped">
<tr>
    <th>N°</th>
    <th>Fecha</th>
    <th>Turno</th>
    <th>Cancha</th>
    <th>Tipo</th>
    <th>Cliente</th>
    <th>Precio</th>
</tr>
<% if (lista.isEmpty()) { %>
    <tr>
        <td colspan="7">No hay reservas.</td>
    </tr>
<% } %>
<% for (Reserva r : lista) { %>
    <tr>
        <td><%= r.getIdReserva() %></td>
        <td><%= r.getFecha() %></td>
        <td><%= r.getHoraInicio() + ":00 - " + r.getHoraFin() + ":00" %></td>
        <td><%= "N° " + r.getCancha().getNumero() %></td>
        <td><%= r.getCancha().getTipoCancha().getNombre() %></td>
        <td><%= r.getCliente().getApellido() + ", " + r.getCliente().getNombre() %></td>
        <td><%= r.getCancha().getTipoCancha().getPrecio() %></td>
    </tr>
<% } %>
</table>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
