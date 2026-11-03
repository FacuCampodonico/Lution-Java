package servlets;

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
