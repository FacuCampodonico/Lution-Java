<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="entities.Horario" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Procesos Horario</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% Horario horario = (Horario) request.getAttribute("horario"); %>

<form action="HorarioProcesar" method="post">

    <input type="hidden" name="idHorario" value="<%= (horario != null) ? horario.getIdHorario() : "" %>">

    <label class="form-label">Hora de inicio:</label>
    <input type="number" min="0" max="23" class="form-control" name="horaInicio" required value="<%= (horario != null) ? "" + horario.getHoraInicio() : "" %>">

    <label class="form-label">Hora de fin:</label>
    <input type="number" min="1" max="24" class="form-control" name="horaFin" required value="<%= (horario != null) ? "" + horario.getHoraFin() : "" %>">

    <label class="form-label">Intervalo (horas por turno):</label>
    <input type="number" min="1" class="form-control" name="intervaloHoras" required value="<%= (horario != null) ? "" + horario.getIntervaloHoras() : "" %>">

    <button type="submit" class="btn btn-primary">Guardar</button>
    <a href="horario" class="btn btn-secondary">Cancelar</a>
</form>

</body>
</html>
