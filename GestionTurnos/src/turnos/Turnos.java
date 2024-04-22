package turnos;

import java.util.Random;

import colaEspera.ColaClientesLlamados;
import colaEspera.ColaEspera;
import colaEspera.IColaEspera;
import colaEspera.TElementoColaEspera;
import comunicacion.PublicacionNotificacion;
import metricas.Metricas;

public class Turnos {
	private static Turnos instance = null;
	private IColaEspera colaEspera = ColaEspera.getInstance();
	private IColaEspera colaClientesYaLlamados = ColaClientesLlamados.getInstance();
	private Metricas metricas = Metricas.getInstance();
	private PublicacionNotificacion publicacionNotificacion = PublicacionNotificacion.getInstance();
	
	private Turnos() {
	}

	public static Turnos getInstance() {
		if (instance == null)
			return new Turnos();

		return instance;
	}

	public synchronized String atenderCliente(int box) {
		String response = "-1"; // Inicializar la respuesta como "-1" por defecto

		// Verificar si hay clientes en colaClientesYaLlamados para atender primero
		response = atenderClientesCola(colaClientesYaLlamados, true, box);

		// Si ninguno de los clientes en colaClientesYaLlamados respondió, revisar
		// colaEspera
		if (response.equals("-1") && colaEspera.tamaño() > 0) {
			response = atenderClientesCola(colaEspera, false, box);
		}

		// Si ninguno de los clientes en colaClientesYaLlamados o colaEspera respondió,
		// volver a revisar colaClientesYaLlamados
		if (response.equals("-1") && colaClientesYaLlamados.tamaño() > 0) {
			response = atenderClientesCola(colaClientesYaLlamados, true, box);
		}

		return response;
	}

	private String atenderClientesCola(IColaEspera cola, boolean clienteYaLlamado, int box) {
		String response = "-1";

		boolean clienteResponde = false;
		TElementoColaEspera clienteLlamado;

		while (cola.tamaño() > 0 && !clienteResponde) {
			clienteLlamado = cola.sacar();
			System.out.println("Clientes: " + clienteLlamado.getDni());
			// Notificar al cliente por pantalla
			publicacionNotificacion.publicar(new TElementoNotificacion(clienteLlamado.getDni(), String.valueOf(box)));
			
			// Simular respuesta del cliente al llamado
			if (this.clienteLlamadoResponde(clienteYaLlamado)) {
				clienteResponde = true;
				this.metricas.actualizarClientesAtendidos(this.metricas.getClientesAtendidos() + 1);
				this.metricas.actualizarClienteEnEspera(this.metricas.getClientesEnEspera() - 1);
				this.metricas.actualizarTMaxEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTMinEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTiempoTotalEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTiempoPromedioEspera();
				response = clienteLlamado.getDni();
			}
			// Si el cliente no responde al llamado
			else {
				// Si ya fue llamado una vez, se asume que el cliente se retiro del
				// establecimiento
				if (clienteYaLlamado)
					this.metricas.actualizarClientesNoAtendidos(this.metricas.getClientesNoAtendidos() + 1);
				// Si es la primera vez que se llama al cliente, se lo agrega a la cola para que
				// vuelva a ser llamado
				else
					colaClientesYaLlamados.agregar(clienteLlamado);
			}
		}

		return response;
	}

	private boolean clienteLlamadoResponde(boolean yaFueLlamadoUnaVez) {
		Random r = new Random();

		double respuestaCliente = r.nextDouble();

		if (yaFueLlamadoUnaVez)
			return respuestaCliente < 0.95;
		else
			return respuestaCliente < 0.80;
	}
}
