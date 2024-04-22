package comunicacion;

import java.util.Map;

import colaEspera.ColaEspera;
import colaEspera.IColaEspera;
import colaEspera.TElementoColaEspera;
import metricas.Metricas;
import turnos.Turnos;

public class GestionRecepcionServidor {
	
	private IColaEspera colaEspera = ColaEspera.getInstance();
	private Turnos turnos = Turnos.getInstance();
	private Metricas metricas = Metricas.getInstance();
	
	public GestionRecepcionServidor() {}
	
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
				metricas.actualizarCantidadClientesRegistrados(this.metricas.getcantTotalClientesRegistrados() + 1);
				metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() + 1);
				response = "0";
			}
		}
		return response;
	}
	
	public String atenderCliente(String numeroBox) {
		return turnos.atenderCliente(Integer.parseInt(numeroBox));
	}
	
	public Map<String, Object> actualizarMetricas(){
		return metricas.obtenerMetricas();
	}
}
