package entities;

public class TipoCancha {
	private int idTipoCancha;
	private String nombre;
	private String descripcion;
	private int cantMaxJugadores;
	private Double precio;

	public int getIdTipoCancha() {
		return idTipoCancha;
	}

	public void setIdTipoCancha(int idTipoCancha) {
		this.idTipoCancha = idTipoCancha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public int getCantMaxJugadores() {
		return cantMaxJugadores;
	}
	
	public void setCantMaxJugadores(int cantMaxJugadores) {
		this.cantMaxJugadores = cantMaxJugadores;
	}
	
	public Double getPrecio() {
		return precio;
	}
	
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public TipoCancha() {
	}

	public TipoCancha(String nombre, String descripcion, int cantMaxJugadores, Double precio) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.cantMaxJugadores = cantMaxJugadores;
		this.precio = precio;
	}
}
