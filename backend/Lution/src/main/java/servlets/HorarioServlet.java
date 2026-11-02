package servlets;

import entities.Horario;
import logic.HorarioService;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/horario")
public class HorarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HorarioService service = new HorarioService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String error = request.getParameter("error");

		try {

			if (action == null) {

				LinkedList<Horario> lista = service.listar();
				request.setAttribute("listaHorarios", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/horario/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				request.getRequestDispatcher("/WEB-INF/jsp/horario/formulario.jsp").forward(request, response);

			} else if (action.equals("edit")) {

				int idHorario = Integer.parseInt(request.getParameter("idHorario"));
				Horario horario = service.buscarPorId(idHorario);
				request.setAttribute("horario", horario);
				request.getRequestDispatcher("/WEB-INF/jsp/horario/formulario.jsp").forward(request, response);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaHorarios", new LinkedList<Horario>());
			request.getRequestDispatcher("/WEB-INF/jsp/horario/listar.jsp").forward(request, response);
		}
	}
}
