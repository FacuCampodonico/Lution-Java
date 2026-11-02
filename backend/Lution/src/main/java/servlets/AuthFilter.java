package servlets;

import entities.Usuario;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthFilter implements Filter {

	private static final List<String> RUTAS_ADMIN = Arrays.asList(
			"/usuario", "/UsuarioProcesar",
			"/tipoCancha", "/TipoCanchaProcesar",
			"/horario", "/HorarioProcesar",
			"/cancha", "/CanchaProcesar",
			"/mantenimiento", "/MantenimientoProcesar");

	private static final List<String> RUTAS_CLIENTE = Arrays.asList("/ReservaProcesar");

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String ruta = request.getServletPath();

		if (ruta.equals("/login") || ruta.equals("/LoginProcesar")) {
			chain.doFilter(request, response);
			return;
		}

		HttpSession session = request.getSession(false);
		Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

		if (usuario == null) {
			response.sendRedirect(request.getContextPath() + "/login");

		} else if (RUTAS_ADMIN.contains(ruta) && !"Administrador".equals(usuario.getRol())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);

		} else if (RUTAS_CLIENTE.contains(ruta) && !"Cliente".equals(usuario.getRol())) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);

		} else {
			chain.doFilter(request, response);
		}
	}
}
