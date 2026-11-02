package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.TipoCancha;

public class DataTipoCancha {

	public LinkedList<TipoCancha> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<TipoCancha> tiposCancha = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT idTipoCancha, nombre, descripcion, cantMaxJugadores, precio "
					+ "FROM tipo_cancha ORDER BY nombre ");

			if (rs != null) {
				while (rs.next()) {
					TipoCancha tipoCancha = new TipoCancha();
					tipoCancha.setIdTipoCancha(rs.getInt("idTipoCancha"));
					tipoCancha.setNombre(rs.getString("nombre"));
					tipoCancha.setDescripcion(rs.getString("descripcion"));
					tipoCancha.setCantMaxJugadores(rs.getInt("cantMaxJugadores"));
					tipoCancha.setPrecio(rs.getDouble("precio"));

					tiposCancha.add(tipoCancha);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de tipos de cancha.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tiposCancha;
	}

	public TipoCancha getById(int idTipoCancha) {
		TipoCancha tipoCancha = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idTipoCancha, nombre, descripcion, cantMaxJugadores, precio "
					+ "FROM tipo_cancha WHERE idTipoCancha = ? ");
			stmt.setInt(1, idTipoCancha);
			rs = stmt.executeQuery();

			if (rs != null && rs.next()) {
				tipoCancha = new TipoCancha();
				tipoCancha.setIdTipoCancha(rs.getInt("idTipoCancha"));
				tipoCancha.setNombre(rs.getString("nombre"));
				tipoCancha.setDescripcion(rs.getString("descripcion"));
				tipoCancha.setCantMaxJugadores(rs.getInt("cantMaxJugadores"));
				tipoCancha.setPrecio(rs.getDouble("precio"));
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el tipo de cancha solicitado.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tipoCancha;
	}

	public TipoCancha setTipoCancha(TipoCancha tipoCancha) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO tipo_cancha (nombre, descripcion, cantMaxJugadores, precio) "
					+ "VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, tipoCancha.getNombre());
			stmt.setString(2, tipoCancha.getDescripcion());
			stmt.setInt(3, tipoCancha.getCantMaxJugadores());
			stmt.setDouble(4, tipoCancha.getPrecio());

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int nuevoId = rs.getInt(1);
				tipoCancha.setIdTipoCancha(nuevoId);
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo guardar el tipo de cancha.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tipoCancha;
	}

	public void updateById(TipoCancha tipoCancha) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE tipo_cancha SET nombre = ?, descripcion = ?, cantMaxJugadores = ?, precio = ? "
					+ "WHERE idTipoCancha = ?");
			stmt.setString(1, tipoCancha.getNombre());
			stmt.setString(2, tipoCancha.getDescripcion());
			stmt.setInt(3, tipoCancha.getCantMaxJugadores());
			stmt.setDouble(4, tipoCancha.getPrecio());
			stmt.setInt(5, tipoCancha.getIdTipoCancha());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo actualizar el tipo de cancha.", e);

		} finally {
			try {
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteById(TipoCancha tipoCancha) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM tipo_cancha WHERE idTipoCancha = ?");
			stmt.setInt(1, tipoCancha.getIdTipoCancha());
			stmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("No se puede eliminar el tipo de cancha porque tiene canchas asociadas.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo eliminar el tipo de cancha.", e);

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
