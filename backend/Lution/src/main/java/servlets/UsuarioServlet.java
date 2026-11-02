package servlets;

import entities.Usuario;
import logic.UsuarioService;
import java.io.IOException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/usuario")
public class UsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsuarioService service = new UsuarioService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		String error = request.getParameter("error");

		try {

			if (action == null) {

				LinkedList<Usuario> lista = service.listar();
				request.setAttribute("listaUsuarios", lista);
				if (error != null) {
					request.setAttribute("error", error);
				}
				request.getRequestDispatcher("/WEB-INF/jsp/usuario/listar.jsp").forward(request, response);

			} else if (action.equals("new")) {

				request.getRequestDispatcher("/WEB-INF/jsp/usuario/formulario.jsp").forward(request, response);

			} else if (action.equals("edit")) {

				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				Usuario usuario = service.buscarPorId(idUsuario);
				request.setAttribute("usuario", usuario);
				request.getRequestDispatcher("/WEB-INF/jsp/usuario/formulario.jsp").forward(request, response);
			}

		} catch (RuntimeException e) {

			request.setAttribute("error", e.getMessage());
			request.setAttribute("listaUsuarios", new LinkedList<Usuario>());
			request.getRequestDispatcher("/WEB-INF/jsp/usuario/listar.jsp").forward(request, response);
		}
	}
}
