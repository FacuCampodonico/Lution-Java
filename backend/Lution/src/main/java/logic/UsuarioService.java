package logic;

import data.DataUsuario;
import entities.Usuario;
import java.util.LinkedList;

public class UsuarioService {

	private DataUsuario dataUsuario = new DataUsuario();

	public LinkedList<Usuario> listarClientes() {
		return dataUsuario.getClientes();
	}

	public Usuario buscarPorId(int idUsuario) {
		return dataUsuario.getById(idUsuario);
	}
}
