package logic;

import data.DataReserva;
import entities.Cancha;
import entities.Horario;
import entities.Reserva;
import entities.Usuario;
import java.time.LocalDate;
import java.util.LinkedList;

public class ReservaService {

	private DataReserva dataReserva = new DataReserva();
	private CanchaService canchaService = new CanchaService();
	private UsuarioService usuarioService = new UsuarioService();

	public LinkedList<Reserva> listar() {
		return dataReserva.getAll();
	}

	public LinkedList<Usuario> listarClientes() {
		return usuarioService.listarClientes();
	}

	public LinkedList<Cancha> listarCanchas() {
		return canchaService.listar();
	}

	public Reserva reservar(int idUsuario, int idCancha, LocalDate fecha, int horaInicio) {

		Usuario cliente = usuarioService.buscarPorId(idUsuario);
		if (cliente == null) {
			throw new IllegalArgumentException("El cliente seleccionado no existe.");
		}
		Cancha cancha = canchaService.buscarPorId(idCancha);
		if (cancha == null) {
			throw new IllegalArgumentException("La cancha seleccionada no existe.");
		}
		if (fecha == null) {
			throw new IllegalArgumentException("La fecha es obligatoria.");
		}

		int horaFin = horaInicio + cancha.getHorario().getIntervaloHoras();
		String motivo = motivoNoDisponible(cancha, fecha, horaInicio, horaFin, dataReserva.getByFecha(fecha));
		if (motivo != null) {
			throw new IllegalArgumentException(motivo);
		}

		return dataReserva.setReserva(new Reserva(cliente, cancha, fecha, horaInicio, horaFin));
	}

	private String motivoNoDisponible(Cancha cancha, LocalDate fecha, int horaInicio, int horaFin, LinkedList<Reserva> reservasDelDia) {
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
		return null;
	}

	private boolean seSolapan(int iniA, int finA, int iniB, int finB) {
		return iniA < finB && iniB < finA;
	}
}
