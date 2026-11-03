package logic;

import data.DataReserva;
import entities.Cancha;
import entities.Horario;
import entities.Mantenimiento;
import entities.Reserva;
import entities.TipoCancha;
import entities.Usuario;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class ReservaService {

	private DataReserva dataReserva = new DataReserva();
	private CanchaService canchaService = new CanchaService();
	private UsuarioService usuarioService = new UsuarioService();
	private MantenimientoService mantenimientoService = new MantenimientoService();
	private TipoCanchaService tipoCanchaService = new TipoCanchaService();

	public LinkedList<Reserva> listar() {
		return dataReserva.getAll();
	}

	public LinkedList<Reserva> listarPorDni(String dni) {
		Usuario cliente = usuarioService.buscarPorDni(dni);
		if (cliente == null) {
			return new LinkedList<>();
		}
		return dataReserva.getByUsuario(cliente.getIdUsuario());
	}

	public LinkedList<TipoCancha> listarTiposCancha() {
		return tipoCanchaService.listar();
	}

	public Reserva prepararReserva(int idCancha, LocalDate fecha, int horaInicio) {

		Cancha cancha = canchaService.buscarPorId(idCancha);
		if (cancha == null) {
			throw new IllegalArgumentException("La cancha seleccionada no existe.");
		}
		if (fecha == null) {
			throw new IllegalArgumentException("La fecha es obligatoria.");
		}

		int horaFin = horaInicio + cancha.getHorario().getIntervaloHoras();
		String motivo = motivoNoDisponible(cancha, fecha, horaInicio, horaFin, dataReserva.getByFecha(fecha), mantenimientoService.listarPorFecha(fecha));
		if (motivo != null) {
			throw new IllegalArgumentException(motivo);
		}

		return new Reserva(null, cancha, fecha, horaInicio, horaFin);
	}

	public Reserva reservar(String dni, String nombre, String apellido, int idCancha, LocalDate fecha, int horaInicio) {

		Reserva reserva = this.prepararReserva(idCancha, fecha, horaInicio);
		Usuario cliente = usuarioService.buscarOCrearCliente(dni, nombre, apellido);
		reserva.setCliente(cliente);

		return dataReserva.setReserva(reserva);
	}

	public void cancelarReserva(int idReserva) {

		Reserva reserva = dataReserva.getById(idReserva);
		if (reserva == null) {
			throw new IllegalArgumentException("La reserva no existe.");
		}
		if (!"CONFIRMADA".equals(reserva.getEstado())) {
			throw new IllegalArgumentException("Solo se pueden cancelar reservas confirmadas.");
		}
		if (reserva.isComenzada()) {
			throw new IllegalArgumentException("No se puede cancelar una reserva cuyo turno ya comenzó.");
		}

		dataReserva.cancelar(idReserva);
	}

	public void cancelarReservaCliente(int idReserva, String dni) {

		Reserva reserva = dataReserva.getById(idReserva);
		if (reserva == null) {
			throw new IllegalArgumentException("La reserva no existe.");
		}
		if (dni == null || !dni.trim().equals(reserva.getCliente().getDni())) {
			throw new IllegalArgumentException("La reserva no pertenece al DNI ingresado.");
		}

		this.cancelarReserva(idReserva);
	}

	public void registrarAsistencia(int idReserva, boolean asistio) {

		Reserva reserva = dataReserva.getById(idReserva);
		if (reserva == null) {
			throw new IllegalArgumentException("La reserva no existe.");
		}
		if (!"CONFIRMADA".equals(reserva.getEstado())) {
			throw new IllegalArgumentException("Solo se puede registrar la asistencia de reservas confirmadas.");
		}
		if (!reserva.isFechaAlcanzada()) {
			throw new IllegalArgumentException("No se puede registrar la asistencia antes del día de la reserva.");
		}

		dataReserva.registrarAsistencia(idReserva, asistio ? "ASISTIO" : "NO_ASISTIO");
	}

	public LinkedList<Reserva> buscarDisponibles(LocalDate fecha, Integer idTipoCancha) {

		LinkedList<Reserva> turnos = new LinkedList<>();
		LinkedList<Reserva> reservasDelDia = dataReserva.getByFecha(fecha);
		LinkedList<Mantenimiento> mantenimientosDelDia = mantenimientoService.listarPorFecha(fecha);

		for (Cancha cancha : canchaService.listar(idTipoCancha)) {
			Horario h = cancha.getHorario();
			for (int ini = h.getHoraInicio(); ini + h.getIntervaloHoras() <= h.getHoraFin(); ini += h.getIntervaloHoras()) {
				int fin = ini + h.getIntervaloHoras();
				if (motivoNoDisponible(cancha, fecha, ini, fin, reservasDelDia, mantenimientosDelDia) == null) {
					turnos.add(new Reserva(null, cancha, fecha, ini, fin));
				}
			}
		}

		return turnos;
	}

	private String motivoNoDisponible(Cancha cancha, LocalDate fecha, int horaInicio, int horaFin, LinkedList<Reserva> reservasDelDia, LinkedList<Mantenimiento> mantenimientosDelDia) {
		Horario h = cancha.getHorario();

		if (fecha.isBefore(LocalDate.now())) {
			return "No se puede reservar en una fecha pasada.";
		}
		if (horaInicio < h.getHoraInicio() || horaFin > h.getHoraFin() || (horaInicio - h.getHoraInicio()) % h.getIntervaloHoras() != 0) {
			return "El horario elegido no corresponde a un turno de la cancha.";
		}
		for (Reserva r : reservasDelDia) {
			if (r.getCancha().getIdCancha() == cancha.getIdCancha() && seSolapan(horaInicio, horaFin, r.getHoraInicio(), r.getHoraFin())) {
				return "La cancha ya está reservada en ese horario.";
			}
		}
		for (Mantenimiento m : mantenimientosDelDia) {
			if (m.getCancha().getIdCancha() == cancha.getIdCancha() && seSolapan(horaInicio, horaFin, m.getHoraInicio(), m.getHoraFin())) {
				return "La cancha está en mantenimiento en ese horario.";
			}
		}
		if (fecha.equals(LocalDate.now()) && horaInicio <= LocalTime.now().getHour()) {
			return "El turno ya comenzó.";
		}
		return null;
	}

	private boolean seSolapan(int iniA, int finA, int iniB, int finB) {
		return iniA < finB && iniB < finA;
	}
}
