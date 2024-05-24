package comunes;

import java.io.Serializable;

public class TRespuesta implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int estado;
	private String mensaje;
	private String titulo;
	
	public TRespuesta(int informationMessage, String mensaje, String titulo) {
		super();
		this.estado = informationMessage;
		this.mensaje = mensaje;
		this.titulo = titulo;
	}

	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
