package servlets;

import entities.TipoCancha;
import logic.TipoCanchaService;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/tipoCancha")
public class TipoCanchaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TipoCanchaService service = new TipoCanchaService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String error = request.getParameter("error");

		try {

			if (action == null) {

				LinkedList<TipoCancha> lista = service.listar();
				request.setAttribute("listaTiposCancha", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/tipoCancha/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				request.getRequestDispatcher("/WEB-INF/jsp/tipoCancha/formulario.jsp").forward(request, response);

			} else if (action.equals("edit")) {

				int idTipoCancha = Integer.parseInt(request.getParameter("idTipoCancha"));
				TipoCancha tipoCancha = service.buscarPorId(idTipoCancha);
				request.setAttribute("tipoCancha", tipoCancha);
				request.getRequestDispatcher("/WEB-INF/jsp/tipoCancha/formulario.jsp").forward(request, response);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaTiposCancha", new LinkedList<TipoCancha>());
			request.getRequestDispatcher("/WEB-INF/jsp/tipoCancha/listar.jsp").forward(request, response);
		}
	}
}
