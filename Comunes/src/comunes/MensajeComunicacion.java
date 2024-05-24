package comunes;

import java.io.Serializable;

public class MensajeComunicacion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object mensaje;
	
	public MensajeComunicacion(Object mensaje) {
		this.mensaje = mensaje;
	}

	public Object getMensaje() {
		return mensaje;
	}

	public void setMensaje(Object mensaje) {
		this.mensaje = mensaje;
	}
}
