package servlets;

import entities.Cancha;
import logic.CanchaService;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cancha")
public class CanchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CanchaService service = new CanchaService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String error = request.getParameter("error");

		try {

			if (action == null) {

				LinkedList<Cancha> lista = service.listar();
				request.setAttribute("listaCanchas", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/cancha/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				request.setAttribute("listaTiposCancha", service.listarTiposCancha());
				request.setAttribute("listaHorarios", service.listarHorarios());
				request.getRequestDispatcher("/WEB-INF/jsp/cancha/formulario.jsp").forward(request, response);

			} else if (action.equals("edit")) {

				int idCancha = Integer.parseInt(request.getParameter("idCancha"));
				Cancha cancha = service.buscarPorId(idCancha);
				request.setAttribute("cancha", cancha);
				request.setAttribute("listaTiposCancha", service.listarTiposCancha());
				request.setAttribute("listaHorarios", service.listarHorarios());
				request.getRequestDispatcher("/WEB-INF/jsp/cancha/formulario.jsp").forward(request, response);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaCanchas", new LinkedList<Cancha>());
			request.getRequestDispatcher("/WEB-INF/jsp/cancha/listar.jsp").forward(request, response);
		}
	}
}
