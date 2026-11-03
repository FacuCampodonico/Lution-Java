package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.Usuario;

public class DataUsuario {

	public LinkedList<Usuario> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Usuario> usuarios = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT idUsuario, dni, nombre, apellido, rol "
					+ "FROM usuario ORDER BY apellido, nombre ");

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
			throw new RuntimeException("No se pudo obtener el listado de usuarios.", e);

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

	public Usuario getByDni(String dni) {
		Usuario usuario = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idUsuario, dni, nombre, apellido, rol "
					+ "FROM usuario WHERE dni = ? ");
			stmt.setString(1, dni);
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

	public Usuario getByDniYPassword(String dni, String password) {
		Usuario usuario = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idUsuario, dni, nombre, apellido, rol "
					+ "FROM usuario WHERE dni = ? AND password = ? AND rol = 'Administrador' ");
			stmt.setString(1, dni);
			stmt.setString(2, password);
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
			throw new RuntimeException("No se pudo validar el usuario.", e);

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

	public Usuario setUsuario(Usuario usuario) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO usuario (dni, nombre, apellido, password, rol) "
					+ "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, usuario.getDni());
			stmt.setString(2, usuario.getNombre());
			stmt.setString(3, usuario.getApellido());
			stmt.setString(4, usuario.getPassword());
			stmt.setString(5, usuario.getRol());

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int nuevoId = rs.getInt(1);
				usuario.setIdUsuario(nuevoId);
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("Ya existe un usuario con ese DNI.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo guardar el usuario.", e);

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

	public void updateById(Usuario usuario) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE usuario SET dni = ?, nombre = ?, apellido = ?, password = ?, rol = ? "
					+ "WHERE idUsuario = ?");
			stmt.setString(1, usuario.getDni());
			stmt.setString(2, usuario.getNombre());
			stmt.setString(3, usuario.getApellido());
			stmt.setString(4, usuario.getPassword());
			stmt.setString(5, usuario.getRol());
			stmt.setInt(6, usuario.getIdUsuario());

			stmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("Ya existe un usuario con ese DNI.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo actualizar el usuario.", e);

		} finally {
			try {
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteById(Usuario usuario) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM usuario WHERE idUsuario = ?");
			stmt.setInt(1, usuario.getIdUsuario());
			stmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("No se puede eliminar el usuario porque tiene reservas.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo eliminar el usuario.", e);

		} finally {
			try {
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
