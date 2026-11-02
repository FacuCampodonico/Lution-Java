package entities;

public class Horario {
	private int idHorario;
	private int horaInicio;
	private int horaFin;
	private int intervaloHoras;

	public int getIdHorario() {
		return idHorario;
	}
	
	public void setIdHorario(int idHorario) {
		this.idHorario = idHorario;
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
	
	public int getIntervaloHoras() {
		return intervaloHoras;
	}
	
	public void setIntervaloHoras(int intervaloHoras) {
		this.intervaloHoras = intervaloHoras;
	}

	public String getEtiqueta() {
		return this.getHoraInicio() + ":00 a " + this.getHoraFin() + ":00 (turnos de " + this.getIntervaloHoras() + " h)";
	}

	public Horario() {
	}

	public Horario(int horaInicio, int horaFin, int intervaloHoras) {
		this.horaInicio = horaInicio;
		this.horaFin = horaFin;
		this.intervaloHoras = intervaloHoras;
	}
}
