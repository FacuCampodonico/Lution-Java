package servlets;

import entities.Reserva;
import entities.Usuario;
import logic.ReservaService;
import java.io.IOException;
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
				LinkedList<Reserva> lista;
				if ("Administrador".equals(usuarioSesion.getRol())) {
					lista = service.listar();
				} else {
					lista = service.listarPorCliente(usuarioSesion.getIdUsuario());
				}
				request.setAttribute("listaReservas", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/reserva/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				request.setAttribute("listaCanchas", service.listarCanchas());
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/reserva/formulario.jsp").forward(request, response);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaReservas", new LinkedList<Reserva>());
			request.getRequestDispatcher("/WEB-INF/jsp/reserva/listar.jsp").forward(request, response);
		}
	}
}
