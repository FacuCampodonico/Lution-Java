package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.Cancha;
import entities.Horario;
import entities.TipoCancha;

public class DataCancha {

	public LinkedList<Cancha> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Cancha> canchas = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT c.idCancha, c.numero, t.idTipoCancha, t.nombre, t.descripcion, t.cantMaxJugadores, t.precio, "
					+ "h.idHorario, h.horaInicio, h.horaFin, h.intervaloHoras "
					+ "FROM cancha c "
					+ "JOIN tipo_cancha t ON t.idTipoCancha = c.idTipoCancha "
					+ "JOIN horario h ON h.idHorario = c.idHorario "
					+ "ORDER BY t.nombre, c.numero ");

			if (rs != null) {
				while (rs.next()) {
					TipoCancha tipoCancha = new TipoCancha();
					tipoCancha.setIdTipoCancha(rs.getInt("idTipoCancha"));
					tipoCancha.setNombre(rs.getString("nombre"));
					tipoCancha.setDescripcion(rs.getString("descripcion"));
					tipoCancha.setCantMaxJugadores(rs.getInt("cantMaxJugadores"));
					tipoCancha.setPrecio(rs.getDouble("precio"));

					Horario horario = new Horario();
					horario.setIdHorario(rs.getInt("idHorario"));
					horario.setHoraInicio(rs.getInt("horaInicio"));
					horario.setHoraFin(rs.getInt("horaFin"));
					horario.setIntervaloHoras(rs.getInt("intervaloHoras"));

					Cancha cancha = new Cancha();
					cancha.setIdCancha(rs.getInt("idCancha"));
					cancha.setNumero(rs.getInt("numero"));
					cancha.setTipoCancha(tipoCancha);
					cancha.setHorario(horario);

					canchas.add(cancha);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de canchas.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return canchas;
	}

	public Cancha getById(int idCancha) {
		Cancha cancha = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT c.idCancha, c.numero, t.idTipoCancha, t.nombre, t.descripcion, t.cantMaxJugadores, t.precio, "
					+ "h.idHorario, h.horaInicio, h.horaFin, h.intervaloHoras "
					+ "FROM cancha c "
					+ "JOIN tipo_cancha t ON t.idTipoCancha = c.idTipoCancha "
					+ "JOIN horario h ON h.idHorario = c.idHorario "
					+ "WHERE c.idCancha = ? ");
			stmt.setInt(1, idCancha);
			rs = stmt.executeQuery();

			if (rs != null && rs.next()) {
				TipoCancha tipoCancha = new TipoCancha();
				tipoCancha.setIdTipoCancha(rs.getInt("idTipoCancha"));
				tipoCancha.setNombre(rs.getString("nombre"));
				tipoCancha.setDescripcion(rs.getString("descripcion"));
				tipoCancha.setCantMaxJugadores(rs.getInt("cantMaxJugadores"));
				tipoCancha.setPrecio(rs.getDouble("precio"));

				Horario horario = new Horario();
				horario.setIdHorario(rs.getInt("idHorario"));
				horario.setHoraInicio(rs.getInt("horaInicio"));
				horario.setHoraFin(rs.getInt("horaFin"));
				horario.setIntervaloHoras(rs.getInt("intervaloHoras"));

				cancha = new Cancha();
				cancha.setIdCancha(rs.getInt("idCancha"));
				cancha.setNumero(rs.getInt("numero"));
				cancha.setTipoCancha(tipoCancha);
				cancha.setHorario(horario);
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener la cancha solicitada.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cancha;
	}

	public Cancha setCancha(Cancha cancha) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO cancha (numero, idTipoCancha, idHorario) "
					+ "VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, cancha.getNumero());
			stmt.setInt(2, cancha.getTipoCancha().getIdTipoCancha());
			stmt.setInt(3, cancha.getHorario().getIdHorario());

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int nuevoId = rs.getInt(1);
				cancha.setIdCancha(nuevoId);
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("Ya existe una cancha con ese número.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo guardar la cancha.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cancha;
	}

	public void updateById(Cancha cancha) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE cancha SET numero = ?, idTipoCancha = ?, idHorario = ? "
					+ "WHERE idCancha = ?");
			stmt.setInt(1, cancha.getNumero());
			stmt.setInt(2, cancha.getTipoCancha().getIdTipoCancha());
			stmt.setInt(3, cancha.getHorario().getIdHorario());
			stmt.setInt(4, cancha.getIdCancha());

			stmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("Ya existe una cancha con ese número.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo actualizar la cancha.", e);

		} finally {
			try {
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteById(Cancha cancha) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM cancha WHERE idCancha = ?");
			stmt.setInt(1, cancha.getIdCancha());
			stmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("No se puede eliminar la cancha porque tiene reservas.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo eliminar la cancha.", e);

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
