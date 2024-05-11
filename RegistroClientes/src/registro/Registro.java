package registro;

import comunicacion.Comunicacion;

public class Registro {// extends Observable??
//	private ICliente cliente;
	private Comunicacion publicacion;

	public Registro() {
	}

	public String registrarCliente(String dni) {

//		cliente = new Cliente(dni);
		return publicacion.publicar(dni);
	}
}
