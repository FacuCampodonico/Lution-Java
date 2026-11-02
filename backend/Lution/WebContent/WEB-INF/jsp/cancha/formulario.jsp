<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Cancha" %>
<%@ page import="entities.Horario" %>
<%@ page import="entities.TipoCancha" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Procesos Cancha</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% Cancha cancha = (Cancha) request.getAttribute("cancha"); %>
<% LinkedList<TipoCancha> listaTiposCancha = (LinkedList<TipoCancha>) request.getAttribute("listaTiposCancha"); %>
<% LinkedList<Horario> listaHorarios = (LinkedList<Horario>) request.getAttribute("listaHorarios"); %>

<form action="CanchaProcesar" method="post">

    <input type="hidden" name="idCancha" value="<%= (cancha != null) ? cancha.getIdCancha() : "" %>">

    <label class="form-label">Número:</label>
    <input type="number" min="1" class="form-control" name="numero" required value="<%= (cancha != null) ? "" + cancha.getNumero() : "" %>">

    <label class="form-label">Tipo de cancha:</label>
    <select class="form-control" name="idTipoCancha" required>
        <% for (TipoCancha t : listaTiposCancha) { %>
            <option value="<%= t.getIdTipoCancha() %>" <%= (cancha != null && cancha.getTipoCancha().getIdTipoCancha() == t.getIdTipoCancha()) ? "selected" : "" %>><%= t.getNombre() %></option>
        <% } %>
    </select>

    <label class="form-label">Horario:</label>
    <select class="form-control" name="idHorario" required>
        <% for (Horario h : listaHorarios) { %>
            <option value="<%= h.getIdHorario() %>" <%= (cancha != null && cancha.getHorario().getIdHorario() == h.getIdHorario()) ? "selected" : "" %>><%= h.getEtiqueta() %></option>
        <% } %>
    </select>

    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="cancha" class="btn btn-secondary">Cancelar</a>
</form>

</body>
</html>
