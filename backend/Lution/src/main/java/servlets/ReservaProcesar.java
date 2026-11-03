package servlets;

import entities.Usuario;
import logic.ReservaService;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ReservaProcesar")
public class ReservaProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ReservaService service = new ReservaService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = request.getParameter("accion");
		Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");
		boolean esAdmin = usuarioSesion != null && "Administrador".equals(usuarioSesion.getRol());

		if (accion != null && accion.equals("cancelar")) {

			String dni = request.getParameter("dni");
			String destino = esAdmin ? "reserva" : "reserva?dni=" + ServletUtil.encodar(dni != null ? dni.trim() : "");

			try {

				int idReserva = Integer.parseInt(request.getParameter("idReserva"));
				if (esAdmin) {
					service.cancelarReserva(idReserva);
				} else {
					service.cancelarReservaCliente(idReserva, dni);
				}

				response.sendRedirect(destino);

			} catch (RuntimeException e) {

				response.sendRedirect(destino + (esAdmin ? "?" : "&") + "error=" + ServletUtil.encodar(e.getMessage()));
			}

		} else if (accion != null && accion.equals("asistencia")) {

			if (!esAdmin) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			try {

				int idReserva = Integer.parseInt(request.getParameter("idReserva"));
				boolean asistio = Boolean.parseBoolean(request.getParameter("asistio"));
				service.registrarAsistencia(idReserva, asistio);

				response.sendRedirect("reserva");

			} catch (RuntimeException e) {

				response.sendRedirect("reserva?error=" + ServletUtil.encodar(e.getMessage()));
			}

		} else {

			String idCanchaParam = request.getParameter("idCancha");
			String fechaParam = request.getParameter("fecha");
			String horaInicioParam = request.getParameter("horaInicio");

			try {

				String dni = request.getParameter("dni");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				int idCancha = Integer.parseInt(idCanchaParam);
				LocalDate fecha = LocalDate.parse(fechaParam);
				int horaInicio = Integer.parseInt(horaInicioParam);

				service.reservar(dni, nombre, apellido, idCancha, fecha, horaInicio);

				response.sendRedirect("reserva?dni=" + ServletUtil.encodar(dni.trim()));

			} catch (RuntimeException e) {

				response.sendRedirect("reserva?action=new&idCancha=" + idCanchaParam + "&fecha=" + fechaParam
						+ "&horaInicio=" + horaInicioParam + "&error=" + ServletUtil.encodar(e.getMessage()));
			}
		}
	}
}
