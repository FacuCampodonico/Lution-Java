package servlets;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ServletUtil {

	public static String encodar(String mensaje) {
		if (mensaje == null) {
			mensaje = "Ocurrio un error inesperado.";
		}

		try {
			return URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			return "Ocurrio+un+error+inesperado.";
		}
	}
}
