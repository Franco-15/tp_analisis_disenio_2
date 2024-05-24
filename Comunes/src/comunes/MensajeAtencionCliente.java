package comunes;

import java.io.Serializable;

public class MensajeAtencionCliente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Box box = null;
	private TElementoColaEspera cliente = null;
	private boolean yaFueLlamado = false;
	private boolean esAtendido = false;
	
	public MensajeAtencionCliente() {}

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public TElementoColaEspera getCliente() {
		return cliente;
	}

	public void setCliente(TElementoColaEspera cliente) {
		this.cliente = cliente;
	}

	public boolean isYaFueLlamado() {
		return yaFueLlamado;
	}

	public void setYaFueLlamado(boolean yaFueLlamado) {
		this.yaFueLlamado = yaFueLlamado;
	}

	public boolean isEsAtendido() {
		return esAtendido;
	}

	public void setEsAtendido(boolean esAtendido) {
		this.esAtendido = esAtendido;
	}
	
	
}
