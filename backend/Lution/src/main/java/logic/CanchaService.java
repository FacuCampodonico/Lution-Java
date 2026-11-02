package logic;

import data.DataCancha;
import entities.Cancha;
import entities.Horario;
import entities.TipoCancha;
import java.util.LinkedList;

public class CanchaService {

	private DataCancha dataCancha = new DataCancha();
	private TipoCanchaService tipoCanchaService = new TipoCanchaService();
	private HorarioService horarioService = new HorarioService();

	public LinkedList<Cancha> listar() {
		return dataCancha.getAll();
	}

	public LinkedList<Cancha> listar(Integer idTipoCancha) {
		return dataCancha.getAll(idTipoCancha);
	}

	public Cancha buscarPorId(int idCancha) {
		return dataCancha.getById(idCancha);
	}

	public LinkedList<TipoCancha> listarTiposCancha() {
		return tipoCanchaService.listar();
	}

	public LinkedList<Horario> listarHorarios() {
		return horarioService.listar();
	}

	public void guardar(String id, int numero, int idTipoCancha, int idHorario) {

		if (numero <= 0) {
			throw new IllegalArgumentException("El número de cancha debe ser mayor a 0.");
		}
		TipoCancha tipoCancha = tipoCanchaService.buscarPorId(idTipoCancha);
		if (tipoCancha == null) {
			throw new IllegalArgumentException("El tipo de cancha seleccionado no existe.");
		}
		Horario horario = horarioService.buscarPorId(idHorario);
		if (horario == null) {
			throw new IllegalArgumentException("El horario seleccionado no existe.");
		}

		if (id == null || id.isEmpty()) {

			Cancha nueva = new Cancha(numero, tipoCancha, horario);
			dataCancha.setCancha(nueva);

		} else {

			int idCan = Integer.parseInt(id);
			Cancha existente = dataCancha.getById(idCan);
			if (existente == null) {
				throw new IllegalArgumentException("La cancha solicitada no existe.");
			}
			existente.setNumero(numero);
			existente.setTipoCancha(tipoCancha);
			existente.setHorario(horario);
			dataCancha.updateById(existente);
		}
	}

	public void eliminar(int idCancha) {
		Cancha cancha = dataCancha.getById(idCancha);
		if (cancha == null) {
			throw new IllegalArgumentException("La cancha solicitada no existe.");
		}
		dataCancha.deleteById(cancha);
	}
}
