package logic;

import data.DataHorario;
import entities.Horario;
import java.util.LinkedList;

public class HorarioService {

	private DataHorario dataHorario = new DataHorario();

	public LinkedList<Horario> listar() {
		return dataHorario.getAll();
	}

	public Horario buscarPorId(int idHorario) {
		return dataHorario.getById(idHorario);
	}

	public void guardar(String id, int horaInicio, int horaFin, int intervaloHoras) {

		if (horaInicio < 0 || horaInicio > 23) {
			throw new IllegalArgumentException("La hora de inicio debe estar entre 0 y 23.");
		}
		if (horaFin <= horaInicio || horaFin > 24) {
			throw new IllegalArgumentException("La hora de fin debe ser mayor a la de inicio y como máximo 24.");
		}
		if (intervaloHoras < 1) {
			throw new IllegalArgumentException("El intervalo debe ser de al menos 1 hora.");
		}
		if ((horaFin - horaInicio) % intervaloHoras != 0) {
			throw new IllegalArgumentException("El intervalo debe dividir exactamente la franja horaria.");
		}

		if (id == null || id.isEmpty()) {

			Horario nuevo = new Horario(horaInicio, horaFin, intervaloHoras);
			dataHorario.setHorario(nuevo);

		} else {

			int idHor = Integer.parseInt(id);
			Horario existente = dataHorario.getById(idHor);
			if (existente == null) {
				throw new IllegalArgumentException("El horario solicitado no existe.");
			}
			existente.setHoraInicio(horaInicio);
			existente.setHoraFin(horaFin);
			existente.setIntervaloHoras(intervaloHoras);
			dataHorario.updateById(existente);
		}
	}

	public void eliminar(int idHorario) {
		Horario horario = dataHorario.getById(idHorario);
		if (horario == null) {
			throw new IllegalArgumentException("El horario solicitado no existe.");
		}
		dataHorario.deleteById(horario);
	}
}
