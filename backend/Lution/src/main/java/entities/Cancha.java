package entities;

public class Cancha {
	private int idCancha;
	private int numero;
	private TipoCancha tipoCancha;
	private Horario horario;

	public int getIdCancha() {
		return idCancha;
	}

	public void setIdCancha(int idCancha) {
		this.idCancha = idCancha;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public TipoCancha getTipoCancha() {
		return tipoCancha;
	}

	public void setTipoCancha(TipoCancha tipoCancha) {
		this.tipoCancha = tipoCancha;
	}

	public Horario getHorario() {
		return horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public String getEtiqueta() {
		return "Cancha N° " + this.getNumero() + " - " + this.getTipoCancha().getNombre();
	}

	public Cancha() {
	}

	public Cancha(int numero, TipoCancha tipoCancha, Horario horario) {
		this.numero = numero;
		this.tipoCancha = tipoCancha;
		this.horario = horario;
	}
}
