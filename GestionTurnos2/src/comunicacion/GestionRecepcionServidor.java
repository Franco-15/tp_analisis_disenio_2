package comunicacion;

import java.util.Map;

import modelo.colaEspera.ColaEspera;
import modelo.colaEspera.IColaEspera;
import modelo.colaEspera.TElementoColaEspera;
import modelo.metricas.Metricas;
import modelo.turnos.Turnos;

public class GestionRecepcionServidor {
	
	private IColaEspera colaEspera;
	private Turnos turnos;
	private Metricas metricas;
	
	public GestionRecepcionServidor() {
		this.colaEspera = ColaEspera.getInstance();
		this.turnos = Turnos.getInstance();
		this.metricas = Metricas.getInstance();
	}
	
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
	
	public IColaEspera getCola(){
		return this.colaEspera;
	}

	public void setColaEspera(IColaEspera nuevaColaEspera) {
		this.colaEspera.setColaEspera(nuevaColaEspera.getColaEspera());
	}
}
