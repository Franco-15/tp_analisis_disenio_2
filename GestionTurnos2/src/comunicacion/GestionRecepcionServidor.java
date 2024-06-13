package comunicacion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import archivos.EGrupoAfinidad;
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
import gestionTurnos.ColaRegistrados;
import gestionTurnos.ListaACola;
import gestionTurnos.Metricas;
import gestionTurnos.Turnos;
import main.Configuracion;

public class GestionRecepcionServidor {

	private IColaEspera colaEspera;
	private Turnos turnos;
	private Metricas metricas;
	private ColaRegistrados colaClientesRegistrados;
	private Configuracion configuracion;

	public GestionRecepcionServidor() {
		this.colaEspera = ColaEspera.getInstance();
		this.turnos = Turnos.getInstance();
		this.metricas = Metricas.getInstance();
		this.configuracion = Configuracion.getInstance();
		this.colaClientesRegistrados=ColaRegistrados.getInstance();

	}

	public TRespuesta agregarClienteAColaEspera(Cliente cliente) {
		TElementoColaEspera elementoCola = new TElementoColaEspera(cliente);
		Metricas metricas = Metricas.getInstance();
		TRespuesta response = Respuestas.ErrorDeSistema();
		boolean registrado = false;
		System.out.println(configuracion.getEstrategiaLlamada());
		if (this.colaEspera.existe(elementoCola)) {
			response = Respuestas.DocumentoYaRegistrado();
		} else {
			boolean result;
			for (TElementoColaEspera elem : this.colaClientesRegistrados.getColaRegistrados()) {
				if (elem.getCliente().getDni().equals(cliente.getDni())) {
					registrado = true;
					elementoCola.getCliente().setGrupoAfinidad(elem.getCliente().getGrupoAfinidad());
					elementoCola.getCliente().setFechaNacimiento(elem.getCliente().getFechaNacimiento());
					break;
				}
			}

			if (registrado) {
				result = this.colaEspera.agregar(elementoCola);
				this.colaEspera.setColaEspera(Configuracion.getInstance().getEstrategiaLlamada()
						.ordenarClientes(this.colaEspera.getColaEspera())); 
				
			} else {
				elementoCola.getCliente().setGrupoAfinidad("gold");
				elementoCola.getCliente().setFechaNacimiento(LocalDate.now().toString());
				result = this.colaEspera.agregar(elementoCola);
			}


			if (result) {
				metricas.actualizarCantidadClientesRegistrados(this.metricas.getcantTotalClientesRegistrados() + 1);
				metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() + 1);
				response = Respuestas.RegistroExitoso();
				Logs logs = Logs.getInstance();
				logs.agregarLog(new Log(elementoCola.getCliente().getDni(), "Cliente Registrado",
						LocalDateTime.now().toString()));
				IArchivoLogs archivo = configuracion.getFactoryArchivos().crearArchivoLogs();
				archivo.escribirLogs(logs.obtenerLista());
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

	public void cargarClientesRegistrados() {
		List<Cliente> listaClientesRegistrados = configuracion.getFactoryArchivos().crearArchivoClientes()
				.leerClientes();
		ListaACola listaACola = new ListaACola();
		this.colaClientesRegistrados.setColaRegistrados(listaACola.obtenerColaClientes(listaClientesRegistrados));

	}

}
