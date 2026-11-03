package servlets;

import entities.Reserva;
import entities.Usuario;
import logic.ReservaService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reserva")
public class ReservaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ReservaService service = new ReservaService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String error = request.getParameter("error");

		try {

			if (action == null) {

				Usuario usuarioSesion = (Usuario) request.getSession().getAttribute("usuario");
				String dni = request.getParameter("dni");
				LinkedList<Reserva> lista;
				if (usuarioSesion != null && "Administrador".equals(usuarioSesion.getRol())) {
					lista = service.listar();
				} else if (dni != null && !dni.trim().isEmpty()) {
					lista = service.listarPorDni(dni.trim());
					request.setAttribute("dniFiltro", dni.trim());
				} else {
					lista = new LinkedList<>();
				}
				request.setAttribute("listaReservas", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/reserva/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				String fechaParam = request.getParameter("fecha");

				try {

					int idCancha = Integer.parseInt(request.getParameter("idCancha"));
					int horaInicio = Integer.parseInt(request.getParameter("horaInicio"));
					Reserva reserva = service.prepararReserva(idCancha, LocalDate.parse(fechaParam), horaInicio);
					request.setAttribute("reserva", reserva);
					if (error != null) {
						request.setAttribute("error", error);
					}
					request.getRequestDispatcher("/WEB-INF/jsp/reserva/formulario.jsp").forward(request, response);

				} catch (RuntimeException e) {

					response.sendRedirect("disponibilidad?fecha=" + fechaParam + "&error=" + ServletUtil.encodar(e.getMessage()));
				}
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaReservas", new LinkedList<Reserva>());
			request.getRequestDispatcher("/WEB-INF/jsp/reserva/listar.jsp").forward(request, response);
		}
	}
}
