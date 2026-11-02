<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Cancha" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Nueva Reserva</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Cancha> listaCanchas = (LinkedList<Cancha>) request.getAttribute("listaCanchas"); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<form action="ReservaProcesar" method="post">

    <label class="form-label">Cancha:</label>
    <select class="form-control" name="idCancha" required>
        <% for (Cancha c : listaCanchas) { %>
            <option value="<%= c.getIdCancha() %>"><%= c.getEtiqueta() + " - " + c.getHorario().getEtiqueta() %></option>
        <% } %>
    </select>

    <label class="form-label">Fecha:</label>
    <input type="date" class="form-control" name="fecha" required>

    <label class="form-label">Hora de inicio:</label>
    <input type="number" min="0" max="23" class="form-control" name="horaInicio" required>

    <button type="submit" class="btn btn-primary">Reservar</button>
    <a href="reserva" class="btn btn-secondary">Cancelar</a>
</form>

</body>
</html>
