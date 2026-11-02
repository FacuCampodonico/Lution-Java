package logic;

import data.DataTipoCancha;
import entities.TipoCancha;
import java.util.LinkedList;

public class TipoCanchaService {

	private DataTipoCancha dataTipoCancha = new DataTipoCancha();

	public LinkedList<TipoCancha> listar() {
		return dataTipoCancha.getAll();
	}

	public TipoCancha buscarPorId(int idTipoCancha) {
		return dataTipoCancha.getById(idTipoCancha);
	}

	public void guardar(String id, String nombre, String descripcion, int cantMaxJugadores, Double precio) {

		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		if (cantMaxJugadores <= 0) {
			throw new IllegalArgumentException("La cantidad máxima de jugadores debe ser mayor a 0.");
		}
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
		if (descripcion != null && descripcion.trim().isEmpty()) {
			descripcion = null;
		}

		if (id == null || id.isEmpty()) {

			TipoCancha nuevo = new TipoCancha(nombre, descripcion, cantMaxJugadores, precio);
			dataTipoCancha.setTipoCancha(nuevo);

		} else {

			int idTipo = Integer.parseInt(id);
			TipoCancha existente = dataTipoCancha.getById(idTipo);
			if (existente == null) {
				throw new IllegalArgumentException("El tipo de cancha solicitado no existe.");
			}
			existente.setNombre(nombre);
			existente.setDescripcion(descripcion);
			existente.setCantMaxJugadores(cantMaxJugadores);
			existente.setPrecio(precio);
			dataTipoCancha.updateById(existente);
		}
	}

	public void eliminar(int idTipoCancha) {
		TipoCancha tipoCancha = dataTipoCancha.getById(idTipoCancha);
		if (tipoCancha == null) {
			throw new IllegalArgumentException("El tipo de cancha solicitado no existe.");
		}
		dataTipoCancha.deleteById(tipoCancha);
	}
}
