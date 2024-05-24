package comunes;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TElementoColaEspera implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private LocalDateTime fechaHoraLlegada;
	
	public TElementoColaEspera(Cliente elementoCola) {
		this.cliente = elementoCola;
		this.fechaHoraLlegada = LocalDateTime.now();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente dni) {
		this.cliente = dni;
	}

	public LocalDateTime getFechaHoraLlegada() {
		return fechaHoraLlegada;
	}

	public void setFechaHoraLlegada(LocalDateTime fechaHoraLlegada) {
		this.fechaHoraLlegada = fechaHoraLlegada;
	}
}
