package logic;

import data.DataMantenimiento;
import entities.Cancha;
import entities.Mantenimiento;
import java.time.LocalDate;
import java.util.LinkedList;

public class MantenimientoService {

	private DataMantenimiento dataMantenimiento = new DataMantenimiento();
	private CanchaService canchaService = new CanchaService();

	public LinkedList<Mantenimiento> listar() {
		return dataMantenimiento.getAll();
	}

	public Mantenimiento buscarPorId(int idMantenimiento) {
		return dataMantenimiento.getById(idMantenimiento);
	}

	public LinkedList<Cancha> listarCanchas() {
		return canchaService.listar();
	}

	public LinkedList<Mantenimiento> listarPorFecha(LocalDate fecha) {
		return dataMantenimiento.getByFecha(fecha);
	}

	public void guardar(String id, int idCancha, LocalDate fecha, int horaInicio, int horaFin, String descripcion) {

		Cancha cancha = canchaService.buscarPorId(idCancha);
		if (cancha == null) {
			throw new IllegalArgumentException("La cancha seleccionada no existe.");
		}
		if (fecha == null) {
			throw new IllegalArgumentException("La fecha es obligatoria.");
		}
		if (horaInicio < 0 || horaInicio > 23) {
			throw new IllegalArgumentException("La hora de inicio debe estar entre 0 y 23.");
		}
		if (horaFin <= horaInicio || horaFin > 24) {
			throw new IllegalArgumentException("La hora de fin debe ser mayor a la de inicio y como máximo 24.");
		}
		if (descripcion == null || descripcion.trim().isEmpty()) {
			throw new IllegalArgumentException("La descripción es obligatoria.");
		}

		if (id == null || id.isEmpty()) {

			Mantenimiento nuevo = new Mantenimiento(cancha, fecha, horaInicio, horaFin, descripcion);
			dataMantenimiento.setMantenimiento(nuevo);

		} else {

			int idMan = Integer.parseInt(id);
			Mantenimiento existente = dataMantenimiento.getById(idMan);
			if (existente == null) {
				throw new IllegalArgumentException("El mantenimiento solicitado no existe.");
			}
			existente.setCancha(cancha);
			existente.setFecha(fecha);
			existente.setHoraInicio(horaInicio);
			existente.setHoraFin(horaFin);
			existente.setDescripcion(descripcion);
			dataMantenimiento.updateById(existente);
		}
	}

	public void eliminar(int idMantenimiento) {
		Mantenimiento mantenimiento = dataMantenimiento.getById(idMantenimiento);
		if (mantenimiento == null) {
			throw new IllegalArgumentException("El mantenimiento solicitado no existe.");
		}
		dataMantenimiento.deleteById(mantenimiento);
	}
}
