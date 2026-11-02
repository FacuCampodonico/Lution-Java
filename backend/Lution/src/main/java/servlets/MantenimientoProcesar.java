package servlets;

import logic.MantenimientoService;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MantenimientoProcesar")
public class MantenimientoProcesar extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private MantenimientoService service = new MantenimientoService();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String accion = request.getParameter("accion");

		try {

			if (accion != null && accion.equals("eliminar")) {

				int idMantenimiento = Integer.parseInt(request.getParameter("idMantenimiento"));
				service.eliminar(idMantenimiento);

			} else {

				String id = request.getParameter("idMantenimiento");
				int idCancha = Integer.parseInt(request.getParameter("idCancha"));
				LocalDate fecha = LocalDate.parse(request.getParameter("fecha"));
				int horaInicio = Integer.parseInt(request.getParameter("horaInicio"));
				int horaFin = Integer.parseInt(request.getParameter("horaFin"));
				String descripcion = request.getParameter("descripcion");

				service.guardar(id, idCancha, fecha, horaInicio, horaFin, descripcion);
			}

			response.sendRedirect("mantenimiento");

		} catch (RuntimeException e) {

			response.sendRedirect("mantenimiento?error=" + ServletUtil.encodar(e.getMessage()));
		}
	}
}
