package modelo.atencion;

import comunicacion.Comunicacion;
import modelo.box.Box;

public class Atencion {
	private Comunicacion comunicacion;
	
	public Atencion(int numero) {
		this.comunicacion = new Comunicacion("localhost",numero+6000);
	}
	
	public String llamarCliente(Box box) {
		String cliente = comunicacion.obtenerCliente("dame cliente");
		System.out.println("aca llego con esto:" + cliente);
		return cliente;
	}
	
	public void respuesta_cliente(Box box,String mensaje) {
		this.comunicacion.respuesta_cliente(box.getNumero(),mensaje);
	}
}
