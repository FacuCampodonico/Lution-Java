<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="entities.TipoCancha" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Procesos Tipo de Cancha</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% TipoCancha tipoCancha = (TipoCancha) request.getAttribute("tipoCancha"); %>

<form action="TipoCanchaProcesar" method="post">

    <input type="hidden" name="idTipoCancha" value="<%= (tipoCancha != null) ? tipoCancha.getIdTipoCancha() : "" %>">

    <label class="form-label">Nombre:</label>
    <input type="text" class="form-control" name="nombre" required value="<%= (tipoCancha != null) ? tipoCancha.getNombre() : "" %>">

    <label class="form-label">Descripción:</label>
    <input type="text" class="form-control" name="descripcion" value="<%= (tipoCancha != null && tipoCancha.getDescripcion() != null) ? tipoCancha.getDescripcion() : "" %>">

    <label class="form-label">Cantidad máxima de jugadores:</label>
    <input type="number" min="1" class="form-control" name="cantMaxJugadores" required value="<%= (tipoCancha != null) ? "" + tipoCancha.getCantMaxJugadores() : "" %>">

    <label class="form-label">Precio por turno:</label>
    <input type="number" min="0" step="0.01" class="form-control" name="precio" required value="<%= (tipoCancha != null) ? "" + tipoCancha.getPrecio() : "" %>">

    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="tipoCancha" class="btn btn-secondary">Cancelar</a>
</form>

</body>
</html>
