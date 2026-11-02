package servlets;

import entities.Usuario;
import logic.UsuarioService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginProcesar")
public class LoginProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsuarioService usuarioService = new UsuarioService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {

			String dni = request.getParameter("dni");
			String password = request.getParameter("password");

			Usuario usuario = usuarioService.autenticar(dni, password);
			request.getSession().setAttribute("usuario", usuario);

			response.sendRedirect("Administrador".equals(usuario.getRol()) ? "cancha" : "reserva");

		} catch (RuntimeException e) {

			response.sendRedirect("login?error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
