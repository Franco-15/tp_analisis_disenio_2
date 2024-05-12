package modelo.atencion;

import comunes.MensajeAtencionCliente;
import comunicacion.Comunicacion;
import modelo.box.Box;

public class Atencion {
	private Comunicacion comunicacion;
	
	public Atencion(int numero) {
		this.comunicacion = new Comunicacion("localhost",numero+6000);
	}
	
	public MensajeAtencionCliente llamarCliente() {
		MensajeAtencionCliente cliente = comunicacion.obtenerCliente();
		return cliente;
	}
	
	public void respuesta_cliente(MensajeAtencionCliente cliente) {
		this.comunicacion.respuesta_cliente(cliente);
	}
}
