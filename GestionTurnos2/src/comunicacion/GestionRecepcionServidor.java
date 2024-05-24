package comunicacion;

import java.util.Map;

import comunes.Cliente;
import comunes.DatosSincronizacion;
import comunes.IColaEspera;
import comunes.MensajeAtencionCliente;
import comunes.MensajeComunicacion;
import comunes.Respuestas;
import comunes.TElementoColaEspera;
import comunes.TRespuesta;
import gestionTurnos.ColaEspera;
import gestionTurnos.Metricas;
import gestionTurnos.Turnos;

public class GestionRecepcionServidor {

	private IColaEspera colaEspera;
	private Turnos turnos;
	private Metricas metricas;

	public GestionRecepcionServidor() {
		this.colaEspera = ColaEspera.getInstance();
		this.turnos = Turnos.getInstance();
		this.metricas = Metricas.getInstance();
	}

	public TRespuesta agregarClienteAColaEspera(Cliente cliente) {
		TElementoColaEspera elementoCola = new TElementoColaEspera(cliente);
		Metricas metricas = Metricas.getInstance();
		TRespuesta response = Respuestas.ErrorDeSistema();

		if (this.colaEspera.existe(elementoCola)) {
			response = Respuestas.DocumentoYaRegistrado();
		} else {
			boolean result = this.colaEspera.agregar(elementoCola);
			if (result) {
				metricas.actualizarCantidadClientesRegistrados(this.metricas.getcantTotalClientesRegistrados() + 1);
				metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() + 1);
				response = Respuestas.RegistroExitoso();
			}
		}
		return response;
	}

	public MensajeAtencionCliente atenderCliente(MensajeAtencionCliente mensaje) {
		return turnos.atenderCliente(mensaje);
	}
	
	public MensajeComunicacion resultadoAtencion(MensajeAtencionCliente mensaje) {
		String result = turnos.resultadosAtencion(mensaje);
		
		return new MensajeComunicacion(result);
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
