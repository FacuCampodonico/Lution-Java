package logic;

import data.DataUsuario;
import entities.Usuario;
import java.util.LinkedList;

public class UsuarioService {

	private DataUsuario dataUsuario = new DataUsuario();

	public LinkedList<Usuario> listar() {
		return dataUsuario.getAll();
	}

	public LinkedList<Usuario> listarClientes() {
		return dataUsuario.getClientes();
	}

	public Usuario buscarPorId(int idUsuario) {
		return dataUsuario.getById(idUsuario);
	}

	public void guardar(String id, String dni, String nombre, String apellido, String password, String rol) {

		if (dni == null || dni.trim().isEmpty()) {
			throw new IllegalArgumentException("El DNI es obligatorio.");
		}
		if (nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre es obligatorio.");
		}
		if (apellido == null || apellido.trim().isEmpty()) {
			throw new IllegalArgumentException("El apellido es obligatorio.");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("La contraseña es obligatoria.");
		}
		if (!"Administrador".equals(rol) && !"Cliente".equals(rol)) {
			throw new IllegalArgumentException("El rol debe ser Administrador o Cliente.");
		}

		if (id == null || id.isEmpty()) {

			Usuario nuevo = new Usuario(dni, nombre, apellido, password, rol);
			dataUsuario.setUsuario(nuevo);

		} else {

			int idUsu = Integer.parseInt(id);
			Usuario existente = dataUsuario.getById(idUsu);
			if (existente == null) {
				throw new IllegalArgumentException("El usuario solicitado no existe.");
			}
			existente.setDni(dni);
			existente.setNombre(nombre);
			existente.setApellido(apellido);
			existente.setPassword(password);
			existente.setRol(rol);
			dataUsuario.updateById(existente);
		}
	}

	public void eliminar(int idUsuario) {
		Usuario usuario = dataUsuario.getById(idUsuario);
		if (usuario == null) {
			throw new IllegalArgumentException("El usuario solicitado no existe.");
		}
		dataUsuario.deleteById(usuario);
	}

	public Usuario autenticar(String dni, String password) {
		Usuario usuario = dataUsuario.getByDniYPassword(dni, password);
		if (usuario == null) {
			throw new IllegalArgumentException("DNI o contraseña incorrectos.");
		}
		return usuario;
	}
}
