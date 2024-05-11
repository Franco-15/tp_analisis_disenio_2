package comunicacion;

import java.util.Map;

import comunes.DatosSincronizacion;
import comunes.IColaEspera;
import comunes.TElementoColaEspera;
import modelo.colaEspera.ColaEspera;
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

		if (this.colaEspera.existe(elementoCola)) {
			response = "1";
		} else {
			boolean result = this.colaEspera.agregar(elementoCola);
			if (result) {
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

	public Map<String, Object> actualizarMetricas() {
		return metricas.obtenerMetricas();
	}

	public DatosSincronizacion getDatosSincronizacion() {
		DatosSincronizacion datos = new DatosSincronizacion();
		datos.setColaEspera(colaEspera);
		datos.setMetricas(metricas.obtenerMetricas());

		return datos;
	}

	public void setDatosSincronizacion(DatosSincronizacion nuevosDatosSincronizacion) {
		this.colaEspera.setColaEspera(nuevosDatosSincronizacion.getColaEspera().getColaEspera());
		this.metricas.setMetricas(nuevosDatosSincronizacion.getMetricas());
	}
}
