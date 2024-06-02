package archivos;

public class Log {
	private String numeroDocumento;
	private String evento;
	private String fechaEvento;

	public Log(String numeroDocumento, String evento, String fechaEvento) {
		this.numeroDocumento = numeroDocumento;
		this.evento = evento;
		this.fechaEvento = fechaEvento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}

	public String getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(String fechaEvento) {
		this.fechaEvento = fechaEvento;
	}
}
