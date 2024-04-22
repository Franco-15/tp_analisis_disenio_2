package atencion;

import box.Box;

public class Atencion {
	private Comunicacion comunicacion;
	
	public Atencion() {}
	
	public String llamarCliente(Box box) {
		comunicacion = new Comunicacion("localhost", 2);
		String cliente = comunicacion.obtenerCliente(String.valueOf(box.getNumero()));
		
		return cliente;
	}
}
