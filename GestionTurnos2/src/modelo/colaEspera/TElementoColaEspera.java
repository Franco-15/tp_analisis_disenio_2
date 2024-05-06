package modelo.colaEspera;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TElementoColaEspera implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dni;
	private LocalDateTime fechaHoraLlegada;
	
	public TElementoColaEspera(String elementoCola) {
		this.dni = elementoCola;
		this.fechaHoraLlegada = LocalDateTime.now();
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public LocalDateTime getFechaHoraLlegada() {
		return fechaHoraLlegada;
	}

	public void setFechaHoraLlegada(LocalDateTime fechaHoraLlegada) {
		this.fechaHoraLlegada = fechaHoraLlegada;
	}
}
