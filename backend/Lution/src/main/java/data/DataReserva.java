package data;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;
import entities.Cancha;
import entities.Reserva;
import entities.TipoCancha;
import entities.Usuario;

public class DataReserva {

	public LinkedList<Reserva> getAll() {
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Reserva> reservas = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().createStatement();
			rs = stmt.executeQuery(
					"SELECT r.idReserva, r.fecha, r.horaInicio, r.horaFin, u.idUsuario, u.nombre, u.apellido, "
					+ "c.idCancha, c.numero, t.idTipoCancha, t.nombre AS nombreTipo, t.precio "
					+ "FROM reserva r "
					+ "JOIN usuario u ON u.idUsuario = r.idUsuario "
					+ "JOIN cancha c ON c.idCancha = r.idCancha "
					+ "JOIN tipo_cancha t ON t.idTipoCancha = c.idTipoCancha "
					+ "ORDER BY r.fecha DESC, r.horaInicio ");

			if (rs != null) {
				while (rs.next()) {
					Usuario cliente = new Usuario();
					cliente.setIdUsuario(rs.getInt("idUsuario"));
					cliente.setNombre(rs.getString("nombre"));
					cliente.setApellido(rs.getString("apellido"));

					TipoCancha tipoCancha = new TipoCancha();
					tipoCancha.setIdTipoCancha(rs.getInt("idTipoCancha"));
					tipoCancha.setNombre(rs.getString("nombreTipo"));
					tipoCancha.setPrecio(rs.getDouble("precio"));

					Cancha cancha = new Cancha();
					cancha.setIdCancha(rs.getInt("idCancha"));
					cancha.setNumero(rs.getInt("numero"));
					cancha.setTipoCancha(tipoCancha);

					Reserva reserva = new Reserva();
					reserva.setIdReserva(rs.getInt("idReserva"));
					reserva.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
					reserva.setHoraInicio(rs.getInt("horaInicio"));
					reserva.setHoraFin(rs.getInt("horaFin"));
					reserva.setCliente(cliente);
					reserva.setCancha(cancha);

					reservas.add(reserva);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de reservas.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return reservas;
	}

	public LinkedList<Reserva> getByFecha(LocalDate fecha) {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Reserva> reservas = new LinkedList<>();

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"SELECT idReserva, idCancha, horaInicio, horaFin "
					+ "FROM reserva WHERE fecha = ? ");
			stmt.setDate(1, Date.valueOf(fecha));
			rs = stmt.executeQuery();

			if (rs != null) {
				while (rs.next()) {
					Cancha cancha = new Cancha();
					cancha.setIdCancha(rs.getInt("idCancha"));

					Reserva reserva = new Reserva();
					reserva.setIdReserva(rs.getInt("idReserva"));
					reserva.setFecha(fecha);
					reserva.setHoraInicio(rs.getInt("horaInicio"));
					reserva.setHoraFin(rs.getInt("horaFin"));
					reserva.setCancha(cancha);

					reservas.add(reserva);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo obtener el listado de reservas del día.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return reservas;
	}

	public Reserva setReserva(Reserva reserva) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = DbConnector.getInstancia().getConn().prepareStatement(
					"INSERT INTO reserva (idUsuario, idCancha, fecha, horaInicio, horaFin) "
					+ "VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, reserva.getCliente().getIdUsuario());
			stmt.setInt(2, reserva.getCancha().getIdCancha());
			stmt.setDate(3, Date.valueOf(reserva.getFecha()));
			stmt.setInt(4, reserva.getHoraInicio());
			stmt.setInt(5, reserva.getHoraFin());

			stmt.executeUpdate();

			rs = stmt.getGeneratedKeys();
			if (rs != null && rs.next()) {
				int nuevoId = rs.getInt(1);
				reserva.setIdReserva(nuevoId);
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			throw new IllegalArgumentException("La cancha ya está reservada en ese horario.", e);

		} catch (SQLException e) {
			throw new RuntimeException("No se pudo guardar la reserva.", e);

		} finally {
			try {
				if (rs != null) { rs.close(); }
				if (stmt != null) { stmt.close(); }
				DbConnector.getInstancia().releaseConn();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return reserva;
	}

}
