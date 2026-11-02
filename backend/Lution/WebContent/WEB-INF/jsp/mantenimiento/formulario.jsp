<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Cancha" %>
<%@ page import="entities.Mantenimiento" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Procesos Mantenimiento</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% Mantenimiento mantenimiento = (Mantenimiento) request.getAttribute("mantenimiento"); %>
<% LinkedList<Cancha> listaCanchas = (LinkedList<Cancha>) request.getAttribute("listaCanchas"); %>

<form action="MantenimientoProcesar" method="post">

    <input type="hidden" name="idMantenimiento" value="<%= (mantenimiento != null) ? mantenimiento.getIdMantenimiento() : "" %>">

    <label class="form-label">Cancha:</label>
    <select class="form-control" name="idCancha" required>
        <% for (Cancha c : listaCanchas) { %>
            <option value="<%= c.getIdCancha() %>" <%= (mantenimiento != null && mantenimiento.getCancha().getIdCancha() == c.getIdCancha()) ? "selected" : "" %>><%= c.getEtiqueta() %></option>
        <% } %>
    </select>

    <label class="form-label">Fecha:</label>
    <input type="date" class="form-control" name="fecha" required value="<%= (mantenimiento != null) ? "" + mantenimiento.getFecha() : "" %>">

    <label class="form-label">Hora de inicio:</label>
    <input type="number" min="0" max="23" class="form-control" name="horaInicio" required value="<%= (mantenimiento != null) ? "" + mantenimiento.getHoraInicio() : "" %>">

    <label class="form-label">Hora de fin:</label>
    <input type="number" min="1" max="24" class="form-control" name="horaFin" required value="<%= (mantenimiento != null) ? "" + mantenimiento.getHoraFin() : "" %>">

    <label class="form-label">Descripción:</label>
    <input type="text" class="form-control" name="descripcion" required value="<%= (mantenimiento != null) ? mantenimiento.getDescripcion() : "" %>">

    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="mantenimiento" class="btn btn-secondary">Cancelar</a>
</form>

</body>
</html>
