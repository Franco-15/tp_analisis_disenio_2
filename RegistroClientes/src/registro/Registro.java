package registro;

import comunes.Cliente;
import comunes.TRespuesta;
import comunicacion.Comunicacion;

public class Registro {
	private Comunicacion comunicacion;
	private Cliente cliente;
	
	public Registro() {
		this.comunicacion = Comunicacion.getInstance();
	}

	public TRespuesta registrarCliente(String dni) {
		this.cliente = new Cliente(dni,"","");
		
		return comunicacion.publicar(this.cliente);
	}
}
