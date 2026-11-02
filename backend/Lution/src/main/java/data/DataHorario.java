package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.LinkedList;
import entities.Horario;

public class DataHorario {

	public LinkedList<Horario> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Horario> horarios = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT idHorario, horaInicio, horaFin, intervaloHoras "
					+ "FROM horario ORDER BY horaInicio, horaFin ");

			if (rs != null) {
				while (rs.next()) {
					Horario horario = new Horario();
					horario.setIdHorario(rs.getInt("idHorario"));
					horario.setHoraInicio(rs.getInt("horaInicio"));
					horario.setHoraFin(rs.getInt("horaFin"));
					horario.setIntervaloHoras(rs.getInt("intervaloHoras"));

					horarios.add(horario);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de horarios.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return horarios;
	}

	public Horario getById(int idHorario) {
		Horario horario = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idHorario, horaInicio, horaFin, intervaloHoras "
					+ "FROM horario WHERE idHorario = ? ");
			stmt.setInt(1, idHorario);
			rs = stmt.executeQuery();

			if (rs != null && rs.next()) {
				horario = new Horario();
				horario.setIdHorario(rs.getInt("idHorario"));
				horario.setHoraInicio(rs.getInt("horaInicio"));
				horario.setHoraFin(rs.getInt("horaFin"));
				horario.setIntervaloHoras(rs.getInt("intervaloHoras"));
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el horario solicitado.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return horario;
	}

	public Horario setHorario(Horario horario) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO horario (horaInicio, horaFin, intervaloHoras) "
					+ "VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, horario.getHoraInicio());
			stmt.setInt(2, horario.getHoraFin());
			stmt.setInt(3, horario.getIntervaloHoras());

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int nuevoId = rs.getInt(1);
				horario.setIdHorario(nuevoId);
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo guardar el horario.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return horario;
	}

	public void updateById(Horario horario) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE horario SET horaInicio = ?, horaFin = ?, intervaloHoras = ? "
					+ "WHERE idHorario = ?");
			stmt.setInt(1, horario.getHoraInicio());
			stmt.setInt(2, horario.getHoraFin());
			stmt.setInt(3, horario.getIntervaloHoras());
			stmt.setInt(4, horario.getIdHorario());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo actualizar el horario.", e);

		} finally {
			try {
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteById(Horario horario) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM horario WHERE idHorario = ?");
			stmt.setInt(1, horario.getIdHorario());
			stmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("No se puede eliminar el horario porque tiene canchas asociadas.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo eliminar el horario.", e);

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
