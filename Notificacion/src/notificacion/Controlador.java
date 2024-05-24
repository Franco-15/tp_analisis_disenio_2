package notificacion;

import java.util.ArrayList;

import comunes.TElementoNotificacion;
import vistas.VistaNotificacion;

public class Controlador {

	private VistaNotificacion vista;

	public Controlador() {;
		this.vista = new VistaNotificacion();
	}

	public synchronized void agregarCliente(TElementoNotificacion notificacion) {
		this.actualizar_pantalla(notificacion);
	}

	public void actualizar_pantalla(TElementoNotificacion notificacion) {
		vista.actualizarPantalla(notificacion);
	}
}
