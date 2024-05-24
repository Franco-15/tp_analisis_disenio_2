package atencion;

import java.io.IOException;

import comunes.Box;
import comunes.MensajeAtencionCliente;
import comunes.MensajeComunicacion;
import comunicacion.Comunicacion;

public class Atencion {
	private Comunicacion comunicacion;
	
	public Atencion() {
		this.comunicacion = Comunicacion.getInstance();
	}
	
	public MensajeAtencionCliente llamarCliente(Box box) throws IOException, ClassNotFoundException {
		MensajeAtencionCliente mensajeAtencion = new MensajeAtencionCliente();
		mensajeAtencion.setBox(box);
		MensajeComunicacion mensaje = new MensajeComunicacion(mensajeAtencion);
		MensajeComunicacion respuesta = comunicacion.publicar(mensaje);
		
		MensajeAtencionCliente mensajeRespuesta = null;
		if (respuesta != null) {
			mensajeRespuesta = (MensajeAtencionCliente) respuesta.getMensaje();
		}
		return mensajeRespuesta;
	}

	public void respuestaAtencion(MensajeAtencionCliente mensajeAtencion) throws IOException, ClassNotFoundException {
		MensajeComunicacion mensaje = new MensajeComunicacion(mensajeAtencion);
		MensajeComunicacion respuesta = comunicacion.publicar(mensaje);
		System.out.println(respuesta.getMensaje());
	}

}
