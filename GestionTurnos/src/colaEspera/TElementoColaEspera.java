package colaEspera;

import java.time.LocalDateTime;

public class TElementoColaEspera {
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
