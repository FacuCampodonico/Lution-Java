package servlets;

import logic.CanchaService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CanchaProcesar")
public class CanchaProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private CanchaService service = new CanchaService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = request.getParameter("accion");

		try {

			if (accion != null && accion.equals("eliminar")) {

				int idCancha = Integer.parseInt(request.getParameter("idCancha"));
				service.eliminar(idCancha);

			} else {

				String id = request.getParameter("idCancha");
				int numero = Integer.parseInt(request.getParameter("numero"));
				int idTipoCancha = Integer.parseInt(request.getParameter("idTipoCancha"));
				int idHorario = Integer.parseInt(request.getParameter("idHorario"));

				service.guardar(id, numero, idTipoCancha, idHorario);
			}

			response.sendRedirect("cancha");

		} catch (RuntimeException e) {

			response.sendRedirect("cancha?error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
