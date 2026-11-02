package servlets;

import entities.Reserva;
import entities.TipoCancha;
import logic.ReservaService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/disponibilidad")
public class DisponibilidadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ReservaService service = new ReservaService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String fechaParam = request.getParameter("fecha");
		String idTipoParam = request.getParameter("idTipoCancha");
		String error = request.getParameter("error");

		try {

			LocalDate fecha = (fechaParam == null || fechaParam.isEmpty()) ? LocalDate.now() : LocalDate.parse(fechaParam);
			Integer idTipoCancha = (idTipoParam == null || idTipoParam.isEmpty()) ? null : Integer.parseInt(idTipoParam);

			request.setAttribute("listaTurnos", service.buscarDisponibles(fecha, idTipoCancha));
			request.setAttribute("listaTiposCancha", service.listarTiposCancha());
			request.setAttribute("fechaFiltro", fecha.toString());
			request.setAttribute("idTipoCanchaFiltro", idTipoCancha);
			if (error != null) {
				request.setAttribute("error", error);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaTurnos", new LinkedList<Reserva>());
			request.setAttribute("listaTiposCancha", new LinkedList<TipoCancha>());
			request.setAttribute("fechaFiltro", LocalDate.now().toString());
		}

		request.getRequestDispatcher("/WEB-INF/jsp/disponibilidad/listar.jsp").forward(request, response);
	}
}
