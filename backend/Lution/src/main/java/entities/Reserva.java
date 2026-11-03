package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {
	private int idReserva;
	private Usuario cliente;
	private Cancha cancha;
	private LocalDate fecha;
	private int horaInicio;
	private int horaFin;
	private String estado;

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public Usuario getCliente() {
		return cliente;
	}

	public void setCliente(Usuario cliente) {
		this.cliente = cliente;
	}

	public Cancha getCancha() {
		return cancha;
	}

	public void setCancha(Cancha cancha) {
		this.cancha = cancha;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public int getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	public int getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(int horaFin) {
		this.horaFin = horaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isComenzada() {
		return this.getFecha().isBefore(LocalDate.now())
				|| (this.getFecha().equals(LocalDate.now()) && this.getHoraInicio() <= LocalTime.now().getHour());
	}

	public boolean isFechaAlcanzada() {
		return !this.getFecha().isAfter(LocalDate.now());
	}

	public String getEstadoEtiqueta() {
		if ("CANCELADA".equals(this.getEstado())) {
			return "Cancelada";
		} else if ("ASISTIO".equals(this.getEstado())) {
			return "Asistió";
		} else if ("NO_ASISTIO".equals(this.getEstado())) {
			return "No asistió";
		}
		return "Confirmada";
	}

	public Reserva() {
	}

	public Reserva(Usuario cliente, Cancha cancha, LocalDate fecha, int horaInicio, int horaFin) {
		this.cliente = cliente;
		this.cancha = cancha;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.estado = "CONFIRMADA";
	}
}
