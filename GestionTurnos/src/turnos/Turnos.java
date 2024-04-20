package turnos;

import java.util.Random;

import colaEspera.ColaClientesLlamados;
import colaEspera.ColaEspera;
import colaEspera.IColaEspera;
import colaEspera.TElementoColaEspera;
import metricas.Metricas;

public class Turnos {
	private static Turnos instance = null;
	private IColaEspera colaEspera = ColaEspera.getInstance();
	private IColaEspera colaClientesYaLlamados = ColaClientesLlamados.getInstance();
	private Metricas metricas = Metricas.getInstance();
	
	private Turnos() {}
	
	public static Turnos getInstance() {
		if(instance == null)
			return new Turnos();
		
		return instance;
	}

	public synchronized String atenderCliente(int box) {
		
		TElementoColaEspera clienteLlamado;
		boolean yaFueLlamadoUnaVez;
		String response = "";
		
		//Verificar si hay clientes que ya fueron llamados una vez para llamarlos nuevamente
		if(colaClientesYaLlamados.tamaño() > 0) {
			yaFueLlamadoUnaVez = true;
			clienteLlamado = colaClientesYaLlamados.sacar();
			
			//Notificar al cliente por pantalla
			
			//Simular respuesta del cliente al llamado
			if(this.clienteLlamadoResponde(yaFueLlamadoUnaVez)) {
				this.metricas.actualizarClientesAtendidos(1);
				this.metricas.actualizarClienteEnEspera(-1);
				this.metricas.actualizarTMaxEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTMinEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTiempoTotalEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTiempoPromedioEspera();
				response = clienteLlamado.getDni();
			}
			//Incrementar en 1 los clientes que se fueron del establecimiento sin ser atendidos
			else
				this.metricas.actualizarClientesNoAtendidos(1);
			
		}
		//Verificar si hay clientes que aun no fueron llamados
		else if(colaEspera.tamaño() > 0) {
			yaFueLlamadoUnaVez=false;
			clienteLlamado = colaEspera.sacar();
			//Notificar al cliente por pantalla
			
			//Simular respuesta del cliente al llamado
			if(this.clienteLlamadoResponde(yaFueLlamadoUnaVez)) {
				this.metricas.actualizarClientesAtendidos(1);
				this.metricas.actualizarTMaxEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTMinEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTiempoTotalEspera(clienteLlamado.getFechaHoraLlegada());
				this.metricas.actualizarTiempoPromedioEspera();
				response = clienteLlamado.getDni();
			}
			else
				colaClientesYaLlamados.agregar(clienteLlamado);
		}
		// No hay clientes para atender
		else
			response = "-1";
		
		return response;
	}
	
	private boolean clienteLlamadoResponde(boolean yaFueLlamadoUnaVez) {
		Random r = new Random();
		
		double respuestaCliente = r.nextDouble();
		
		if(yaFueLlamadoUnaVez)
			return respuestaCliente < 0.95;
		else
			return respuestaCliente < 0.80;
	}
}
