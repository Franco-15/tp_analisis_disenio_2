package gestionTurnos;

import java.time.LocalDateTime;

import archivos.IArchivoLogs;
import archivos.Log;
import archivos.Logs;
import comunes.IColaEspera;
import comunes.MensajeAtencionCliente;
import comunes.TElementoColaEspera;
import comunes.TElementoNotificacion;
import comunicacion.PublicacionNotificacion;
import main.Configuracion;

public class Turnos {
	private static Turnos instance = null;
	private IColaEspera colaEspera = ColaEspera.getInstance();
	private IColaEspera colaClientesYaLlamados = ColaClientesLlamados.getInstance();
	private Metricas metricas = Metricas.getInstance();
	private PublicacionNotificacion publicacionNotificacion = PublicacionNotificacion.getInstance();
	private int contLlamados = 0;

	private Turnos() {
	}

	public static Turnos getInstance() {
		if (instance == null)
			return new Turnos();

		return instance;
	}

	public synchronized MensajeAtencionCliente atenderCliente(MensajeAtencionCliente mensaje) {
		Logs logs = Logs.getInstance();
		Configuracion configuracion = Configuracion.getInstance();
		
		IArchivoLogs archivo = configuracion.getFactoryArchivos().crearArchivoLogs();
		TElementoColaEspera clienteLlamado;

		if (colaEspera.tamaño() > 0 && this.contLlamados < 4) {
			this.contLlamados += 1;
			clienteLlamado = colaEspera.sacar();
			logs.agregarLog(new Log(clienteLlamado.getCliente().getDni(), "Cliente llamado", LocalDateTime.now().toString()));
			archivo.escribirLogs(logs.obtenerLista());
			mensaje.setYaFueLlamado(false);
		} else if (colaClientesYaLlamados.tamaño() > 0) {
			this.contLlamados = 0;
			clienteLlamado = colaClientesYaLlamados.sacar();
			mensaje.setYaFueLlamado(true);
		}  
		else if(colaEspera.tamaño() > 0){
			this.contLlamados-=1;
			clienteLlamado = colaEspera.sacar();
			logs.agregarLog(new Log(clienteLlamado.getCliente().getDni(), "Cliente llamado", LocalDateTime.now().toString()));
			archivo.escribirLogs(logs.obtenerLista());
			mensaje.setYaFueLlamado(false);
		}
		else {
			System.out.println("No hay clientes para ser atendidos");
			return mensaje;
		}

		System.out.println("Cliente llamado: " + clienteLlamado.getCliente().getDni());

		// Notificar al cliente por pantalla
		publicacionNotificacion.publicar(
				new TElementoNotificacion(clienteLlamado.getCliente().getDni(), mensaje.getBox().getNumero()));

		mensaje.setCliente(clienteLlamado);

		return mensaje;
	}

	public synchronized String resultadosAtencion(MensajeAtencionCliente mensaje) {
		String result;
		
		if (mensaje.isEsAtendido()) {
			this.metricas.actualizarClientesAtendidos(this.metricas.getClientesAtendidos() + 1);
			this.metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() - 1);
			this.metricas.actualizarTMaxEspera(mensaje.getCliente().getFechaHoraLlegada());
			this.metricas.actualizarTMinEspera(mensaje.getCliente().getFechaHoraLlegada());
			this.metricas.actualizarTiempoTotalEspera(mensaje.getCliente().getFechaHoraLlegada());
			this.metricas.actualizarTiempoPromedioEspera();
			
			result = "Cliente atendido exitosamente!";
		} else {
			if (mensaje.isYaFueLlamado()) {
				this.metricas.actualizarClientesNoAtendidos(this.metricas.getClientesNoAtendidos() + 1);
				this.metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() - 1);
				result = "El cliente se retiró del establecimiento sin ser atendido";
			}
			else {
				colaClientesYaLlamados.agregar(mensaje.getCliente());
				this.contLlamados=2;
				result = "El cliente fue agregado a la cola para volver a ser llamado";
				
			}
		}
		
		return result;
	}
}
