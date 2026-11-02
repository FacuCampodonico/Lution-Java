package servlets;

import entities.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		String error = request.getParameter("error");

		if (usuario != null) {

			response.sendRedirect("Administrador".equals(usuario.getRol()) ? "cancha" : "reserva");

		} else {

			if (error != null) {
				request.setAttribute("error", error);
			}
			request.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp").forward(request, response);
		}
	}
}
