<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="entities.Reserva" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirmar Reserva</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<div class="container mt-4">

<% Reserva reserva = (Reserva) request.getAttribute("reserva"); %>
<% String error = (String) request.getAttribute("error"); %>

<h2>Confirmar Reserva</h2>

<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<table class="table">
    <tr>
        <th>Cancha</th>
        <td><%= "N° " + reserva.getCancha().getNumero() %></td>
    </tr>
    <tr>
        <th>Deporte</th>
        <td><%= reserva.getCancha().getTipoCancha().getNombre() %></td>
    </tr>
    <tr>
        <th>Fecha</th>
        <td><%= reserva.getFecha() %></td>
    </tr>
    <tr>
        <th>Turno</th>
        <td><%= reserva.getHoraInicio() + ":00 - " + reserva.getHoraFin() + ":00" %></td>
    </tr>
    <tr>
        <th>Precio</th>
        <td><%= reserva.getCancha().getTipoCancha().getPrecio() %></td>
    </tr>
</table>

<form action="ReservaProcesar" method="post">

    <input type="hidden" name="idCancha" value="<%= reserva.getCancha().getIdCancha() %>">
    <input type="hidden" name="fecha" value="<%= reserva.getFecha() %>">
    <input type="hidden" name="horaInicio" value="<%= reserva.getHoraInicio() %>">

    <label class="form-label">Nombre:</label>
    <input type="text" class="form-control" name="nombre" required>

    <label class="form-label">Apellido:</label>
    <input type="text" class="form-control" name="apellido" required>

    <label class="form-label">DNI:</label>
    <input type="text" class="form-control mb-3" name="dni" required>

    <button type="submit" class="btn btn-success">Confirmar reserva</button>
    <a href="disponibilidad?fecha=<%= reserva.getFecha() %>" class="btn btn-secondary">Volver</a>
</form>

</div>
</body>
</html>
