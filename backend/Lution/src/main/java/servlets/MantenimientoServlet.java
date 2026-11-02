package servlets;

import entities.Mantenimiento;
import logic.MantenimientoService;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mantenimiento")
public class MantenimientoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private MantenimientoService service = new MantenimientoService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String error = request.getParameter("error");

		try {

			if (action == null) {

				LinkedList<Mantenimiento> lista = service.listar();
				request.setAttribute("listaMantenimientos", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/mantenimiento/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				request.setAttribute("listaCanchas", service.listarCanchas());
				request.getRequestDispatcher("/WEB-INF/jsp/mantenimiento/formulario.jsp").forward(request, response);

			} else if (action.equals("edit")) {

				int idMantenimiento = Integer.parseInt(request.getParameter("idMantenimiento"));
				Mantenimiento mantenimiento = service.buscarPorId(idMantenimiento);
				request.setAttribute("mantenimiento", mantenimiento);
				request.setAttribute("listaCanchas", service.listarCanchas());
				request.getRequestDispatcher("/WEB-INF/jsp/mantenimiento/formulario.jsp").forward(request, response);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaMantenimientos", new LinkedList<Mantenimiento>());
			request.getRequestDispatcher("/WEB-INF/jsp/mantenimiento/listar.jsp").forward(request, response);
		}
	}
}
