package registro;

import java.util.Collections;

import gestion_turnos.ColaEspera;
import gestion_turnos.IColaEspera;
import gestion_turnos.TElementoColaEspera;

public class Registro {
	private ICliente cliente;
	private IColaEspera colaEspera = ColaEspera.getInstance();
	
	public Registro() {}
	
	public void registrarCliente(ICliente cliente) {
		TElementoColaEspera e = new TElementoColaEspera(cliente);
		boolean response = this.colaEspera.agregar(e);
		
		if(response) {
			System.out.println("Cliente registrado correctamente");
		}
		else {
			System.out.println("El cliente no pudo ser registrado");
		}	
	}
	
	public boolean estaRegistrado(String dni) {
		Object elementosCola = this.colaEspera.obtenerElementos();
	}
}
