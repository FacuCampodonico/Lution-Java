<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="entities.Reserva" %>
<%@ page import="entities.Usuario" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= (session.getAttribute("usuario") != null && "Administrador".equals(((Usuario) session.getAttribute("usuario")).getRol())) ? "Reservas" : "Mis reservas" %></title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet">
</head>
<body>
<%@ include file="/WEB-INF/jsp/comun/menu.jspf" %>
<% LinkedList<Reserva> lista = (LinkedList<Reserva>) request.getAttribute("listaReservas"); %>
<% String dniFiltro = (String) request.getAttribute("dniFiltro"); %>
<% boolean esAdmin = usuarioSesion != null && "Administrador".equals(usuarioSesion.getRol()); %>
<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
<% } %>

<% if (!esAdmin) { %>
    <form action="reserva" method="get" class="row g-2 mb-3">
        <div class="col-auto">
            <label class="form-label">DNI:</label>
            <input type="text" class="form-control" name="dni" required value="<%= (dniFiltro != null) ? dniFiltro : "" %>">
        </div>
        <div class="col-auto align-self-end">
            <button type="submit" class="btn btn-primary">Buscar</button>
        </div>
    </form>
<% } %>

<% if (esAdmin || dniFiltro != null) { %>
    <table class="table table-striped">
    <tr>
        <th>N°</th>
        <th>Fecha</th>
        <th>Turno</th>
        <th>Cancha</th>
        <th>Tipo</th>
        <% if (esAdmin) { %>
            <th>Cliente</th>
        <% } %>
        <th>Precio</th>
        <th>Estado</th>
        <th>Acciones</th>
    </tr>
    <% if (lista.isEmpty()) { %>
        <tr>
            <td colspan="<%= esAdmin ? 9 : 8 %>"><%= esAdmin ? "No hay reservas." : "No hay reservas para ese DNI." %></td>
        </tr>
    <% } %>
    <% for (Reserva r : lista) { %>
        <tr>
            <td><%= r.getIdReserva() %></td>
            <td><%= r.getFecha() %></td>
            <td><%= r.getHoraInicio() + ":00 - " + r.getHoraFin() + ":00" %></td>
            <td><%= "N° " + r.getCancha().getNumero() %></td>
            <td><%= r.getCancha().getTipoCancha().getNombre() %></td>
            <% if (esAdmin) { %>
                <td><%= r.getCliente().getApellido() + ", " + r.getCliente().getNombre() %></td>
            <% } %>
            <td><%= r.getCancha().getTipoCancha().getPrecio() %></td>
            <td><%= r.getEstadoEtiqueta() %></td>
            <td>
                <% if ("CONFIRMADA".equals(r.getEstado()) && !r.isComenzada()) { %>
                    <form action="ReservaProcesar" method="post" style="display:inline"
                          onsubmit="return confirm('¿Estás seguro que querés cancelar la reserva N° <%= r.getIdReserva() %>?');">
                        <input type="hidden" name="accion" value="cancelar">
                        <input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                        <% if (!esAdmin) { %>
                            <input type="hidden" name="dni" value="<%= dniFiltro %>">
                        <% } %>
                        <button type="submit" class="btn btn-danger btn-sm">Cancelar</button>
                    </form>
                <% } %>
                <% if (esAdmin && "CONFIRMADA".equals(r.getEstado()) && r.isFechaAlcanzada()) { %>
                    <form action="ReservaProcesar" method="post" style="display:inline">
                        <input type="hidden" name="accion" value="asistencia">
                        <input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                        <input type="hidden" name="asistio" value="true">
                        <button type="submit" class="btn btn-success btn-sm">Asistió</button>
                    </form>

                    <form action="ReservaProcesar" method="post" style="display:inline">
                        <input type="hidden" name="accion" value="asistencia">
                        <input type="hidden" name="idReserva" value="<%= r.getIdReserva() %>">
                        <input type="hidden" name="asistio" value="false">
                        <button type="submit" class="btn btn-secondary btn-sm">No asistió</button>
                    </form>
                <% } %>
            </td>
        </tr>
    <% } %>
    </table>
<% } %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
