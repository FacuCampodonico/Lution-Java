package servlets;

import logic.HorarioService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HorarioProcesar")
public class HorarioProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private HorarioService service = new HorarioService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = request.getParameter("accion");

		try {

			if (accion != null && accion.equals("eliminar")) {

				int idHorario = Integer.parseInt(request.getParameter("idHorario"));
				service.eliminar(idHorario);

			} else {

				String id = request.getParameter("idHorario");
				int horaInicio = Integer.parseInt(request.getParameter("horaInicio"));
				int horaFin = Integer.parseInt(request.getParameter("horaFin"));
				int intervaloHoras = Integer.parseInt(request.getParameter("intervaloHoras"));

				service.guardar(id, horaInicio, horaFin, intervaloHoras);
			}

			response.sendRedirect("horario");

		} catch (RuntimeException e) {

			response.sendRedirect("horario?error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
