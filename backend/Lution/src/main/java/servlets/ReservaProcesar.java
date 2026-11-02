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

		try {

			int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
			int idCancha = Integer.parseInt(request.getParameter("idCancha"));
			LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
			int horaInicio = Integer.parseInt(request.getParameter("horaInicio"));

			service.reservar(idUsuario, idCancha, fecha, horaInicio);

			response.sendRedirect("reserva");

		} catch (RuntimeException e) {

			response.sendRedirect("reserva?action=new&error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
