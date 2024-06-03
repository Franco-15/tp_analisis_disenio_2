package comunicacion;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import archivos.IArchivoLogs;
import archivos.Log;
import archivos.Logs;
import comunes.Cliente;
import comunes.DatosSincronizacion;
import comunes.IColaEspera;
import comunes.MensajeAtencionCliente;
import comunes.MensajeComunicacion;
import comunes.Respuestas;
import comunes.TElementoColaEspera;
import comunes.TRespuesta;
import gestionTurnos.ColaEspera;
import gestionTurnos.ListaACola;
import gestionTurnos.Metricas;
import gestionTurnos.Turnos;
import main.Configuracion;

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
	
	public void cargarClientes() {
		Logs logs = Logs.getInstance();
		Configuracion configuracion = Configuracion.getInstance();
		
		IArchivoLogs archivo = configuracion.getFactoryArchivos().crearArchivoLogs();
		List<Cliente> listaClientes = configuracion.getFactoryArchivos().crearArchivoClientes().leerClientes();
		ListaACola listaACola = new ListaACola();
		Queue<TElementoColaEspera> colaClientes = listaACola.obtenerColaClientes(listaClientes);
		
		if(configuracion.getEstrategiaLlamada() != null)
			colaClientes = configuracion.getEstrategiaLlamada().ordenarClientes(colaClientes);
		
		for(TElementoColaEspera elemento : colaClientes) {
			this.colaEspera.agregar(elemento);
			logs.agregarLog(new Log(elemento.getCliente().getDni(), "Cliente Registrado", LocalDateTime.now().toString()));
			archivo.escribirLogs(logs.obtenerLista());
			metricas.actualizarCantidadClientesRegistrados(this.metricas.getcantTotalClientesRegistrados() + 1);
			metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() + 1);
		}
	}
}
