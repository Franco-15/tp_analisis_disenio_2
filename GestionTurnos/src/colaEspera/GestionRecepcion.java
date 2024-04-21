package colaEspera;

import metricas.Metricas;

public class GestionRecepcion {
	
	private IColaEspera colaEspera = ColaEspera.getInstance();
	
	public GestionRecepcion() {}
	
	public String agregarClienteAColaEspera(String mensaje) {
		TElementoColaEspera elementoCola = new TElementoColaEspera(mensaje);
		Metricas metricas = Metricas.getInstance();
		String response = "2";
		
		if(this.colaEspera.existe(elementoCola)) {
			response = "1";
		}
		else {
			boolean result = this.colaEspera.agregar(elementoCola);
			if(result) {
				metricas.actualizarCantidadClientesRegistrados(1);
				metricas.actualizarClienteEnEspera(1);
				response = "0";
			}
		}
		return response;
	}
}
