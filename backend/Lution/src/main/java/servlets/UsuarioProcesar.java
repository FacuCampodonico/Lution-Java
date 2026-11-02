package servlets;

import logic.UsuarioService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UsuarioProcesar")
public class UsuarioProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private UsuarioService service = new UsuarioService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = request.getParameter("accion");

		try {

			if (accion != null && accion.equals("eliminar")) {

				int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
				service.eliminar(idUsuario);

			} else {

				String id = request.getParameter("idUsuario");
				String dni = request.getParameter("dni");
				String nombre = request.getParameter("nombre");
				String apellido = request.getParameter("apellido");
				String password = request.getParameter("password");
				String rol = request.getParameter("rol");

				service.guardar(id, dni, nombre, apellido, password, rol);
			}

			response.sendRedirect("usuario");

		} catch (RuntimeException e) {

			response.sendRedirect("usuario?error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
