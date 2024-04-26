package registro;

import comunicacion.PublicacionRegistro;

public class Registro {//extends Observable??
//	private ICliente cliente;
	private PublicacionRegistro publicacion = PublicacionRegistro.getInstance();

	public Registro() {
	}

	public String registrarCliente(String dni) {

//		cliente = new Cliente(dni);
		return publicacion.publicar(dni);
	}
}
