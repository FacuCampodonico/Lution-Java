package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import entities.Cancha;
import entities.Mantenimiento;

public class DataMantenimiento {

	public LinkedList<Mantenimiento> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Mantenimiento> mantenimientos = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT m.idMantenimiento, m.fecha, m.horaInicio, m.horaFin, m.descripcion, c.idCancha, c.numero "
					+ "FROM mantenimiento m "
					+ "JOIN cancha c ON c.idCancha = m.idCancha "
					+ "ORDER BY m.fecha DESC, m.horaInicio ");

			if (rs != null) {
				while (rs.next()) {
					Cancha cancha = new Cancha();
					cancha.setIdCancha(rs.getInt("idCancha"));
					cancha.setNumero(rs.getInt("numero"));

					Mantenimiento mantenimiento = new Mantenimiento();
					mantenimiento.setIdMantenimiento(rs.getInt("idMantenimiento"));
					mantenimiento.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
					mantenimiento.setHoraInicio(rs.getInt("horaInicio"));
					mantenimiento.setHoraFin(rs.getInt("horaFin"));
					mantenimiento.setDescripcion(rs.getString("descripcion"));
					mantenimiento.setCancha(cancha);

					mantenimientos.add(mantenimiento);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de mantenimientos.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mantenimientos;
	}

	public Mantenimiento getById(int idMantenimiento) {
		Mantenimiento mantenimiento = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT m.idMantenimiento, m.fecha, m.horaInicio, m.horaFin, m.descripcion, c.idCancha, c.numero "
					+ "FROM mantenimiento m "
					+ "JOIN cancha c ON c.idCancha = m.idCancha "
					+ "WHERE m.idMantenimiento = ? ");
			stmt.setInt(1, idMantenimiento);
			rs = stmt.executeQuery();

			if (rs != null && rs.next()) {
				Cancha cancha = new Cancha();
				cancha.setIdCancha(rs.getInt("idCancha"));
				cancha.setNumero(rs.getInt("numero"));

				mantenimiento = new Mantenimiento();
				mantenimiento.setIdMantenimiento(rs.getInt("idMantenimiento"));
				mantenimiento.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
				mantenimiento.setHoraInicio(rs.getInt("horaInicio"));
				mantenimiento.setHoraFin(rs.getInt("horaFin"));
				mantenimiento.setDescripcion(rs.getString("descripcion"));
				mantenimiento.setCancha(cancha);
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el mantenimiento solicitado.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mantenimiento;
	}

	public LinkedList<Mantenimiento> getByFecha(LocalDate fecha) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Mantenimiento> mantenimientos = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idMantenimiento, idCancha, horaInicio, horaFin "
					+ "FROM mantenimiento WHERE fecha = ? ");
			stmt.setDate(1, Date.valueOf(fecha));
			rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Cancha cancha = new Cancha();
					cancha.setIdCancha(rs.getInt("idCancha"));

					Mantenimiento mantenimiento = new Mantenimiento();
					mantenimiento.setIdMantenimiento(rs.getInt("idMantenimiento"));
					mantenimiento.setFecha(fecha);
					mantenimiento.setHoraInicio(rs.getInt("horaInicio"));
					mantenimiento.setHoraFin(rs.getInt("horaFin"));
					mantenimiento.setCancha(cancha);

					mantenimientos.add(mantenimiento);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de mantenimientos del día.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mantenimientos;
	}

	public Mantenimiento setMantenimiento(Mantenimiento mantenimiento) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO mantenimiento (idCancha, fecha, horaInicio, horaFin, descripcion) "
					+ "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, mantenimiento.getCancha().getIdCancha());
			stmt.setDate(2, Date.valueOf(mantenimiento.getFecha()));
			stmt.setInt(3, mantenimiento.getHoraInicio());
			stmt.setInt(4, mantenimiento.getHoraFin());
			stmt.setString(5, mantenimiento.getDescripcion());

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int nuevoId = rs.getInt(1);
				mantenimiento.setIdMantenimiento(nuevoId);
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo guardar el mantenimiento.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return mantenimiento;
	}

	public void updateById(Mantenimiento mantenimiento) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"UPDATE mantenimiento SET idCancha = ?, fecha = ?, horaInicio = ?, horaFin = ?, descripcion = ? "
					+ "WHERE idMantenimiento = ?");
			stmt.setInt(1, mantenimiento.getCancha().getIdCancha());
			stmt.setDate(2, Date.valueOf(mantenimiento.getFecha()));
			stmt.setInt(3, mantenimiento.getHoraInicio());
			stmt.setInt(4, mantenimiento.getHoraFin());
			stmt.setString(5, mantenimiento.getDescripcion());
			stmt.setInt(6, mantenimiento.getIdMantenimiento());

			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo actualizar el mantenimiento.", e);

		} finally {
			try {
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteById(Mantenimiento mantenimiento) {
		PreparedStatement stmt = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement("DELETE FROM mantenimiento WHERE idMantenimiento = ?");
			stmt.setInt(1, mantenimiento.getIdMantenimiento());
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo eliminar el mantenimiento.", e);

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
