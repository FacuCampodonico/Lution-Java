package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.Usuario;

public class DataUsuario {

	public LinkedList<Usuario> getClientes() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Usuario> usuarios = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT idUsuario, dni, nombre, apellido, rol "
					+ "FROM usuario WHERE rol = 'Cliente' "
					+ "ORDER BY apellido, nombre ");

			if (rs != null) {
				while (rs.next()) {
					Usuario usuario = new Usuario();
					usuario.setIdUsuario(rs.getInt("idUsuario"));
					usuario.setDni(rs.getString("dni"));
					usuario.setNombre(rs.getString("nombre"));
					usuario.setApellido(rs.getString("apellido"));
					usuario.setRol(rs.getString("rol"));

					usuarios.add(usuario);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de clientes.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return usuarios;
	}

	public Usuario getById(int idUsuario) {
		Usuario usuario = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idUsuario, dni, nombre, apellido, rol "
					+ "FROM usuario WHERE idUsuario = ? ");
			stmt.setInt(1, idUsuario);
			rs = stmt.executeQuery();

			if (rs != null && rs.next()) {
				usuario = new Usuario();
				usuario.setIdUsuario(rs.getInt("idUsuario"));
				usuario.setDni(rs.getString("dni"));
				usuario.setNombre(rs.getString("nombre"));
				usuario.setApellido(rs.getString("apellido"));
				usuario.setRol(rs.getString("rol"));
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el usuario solicitado.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return usuario;
	}

}
