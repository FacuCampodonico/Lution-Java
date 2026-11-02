package servlets;

import logic.TipoCanchaService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TipoCanchaProcesar")
public class TipoCanchaProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private TipoCanchaService service = new TipoCanchaService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = request.getParameter("accion");

		try {

			if (accion != null && accion.equals("eliminar")) {

				int idTipoCancha = Integer.parseInt(request.getParameter("idTipoCancha"));
				service.eliminar(idTipoCancha);

			} else {

				String id = request.getParameter("idTipoCancha");
				String nombre = request.getParameter("nombre");
				String descripcion = request.getParameter("descripcion");
				int cantMaxJugadores = Integer.parseInt(request.getParameter("cantMaxJugadores"));
				Double precio = Double.parseDouble(request.getParameter("precio"));

				service.guardar(id, nombre, descripcion, cantMaxJugadores, precio);
			}

			response.sendRedirect("tipoCancha");

		} catch (RuntimeException e) {

			response.sendRedirect("tipoCancha?error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
