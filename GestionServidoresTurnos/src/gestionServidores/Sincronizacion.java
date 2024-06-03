package gestionServidores;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import comunes.DatosSincronizacion;
import vistas.VistaLogs;

public class Sincronizacion implements Runnable {

	private Servidores servidores;

	public Sincronizacion(Servidores servidores) {
		this.servidores = servidores;
	}

	@Override
	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		while (true) {
			vista.agregarElemento("Sincronizando servidores...");
			if (servidores.getServidoresActivos().size() > 1) {
				Servidor servidorPrimario = servidores.getServidorPrimario();
				if (servidorPrimario != null && servidorPrimario.getEstado() == EstadoServidor.Activo) {
					DatosSincronizacion datosSincronizacion = this.getDatosSincronizacion(servidorPrimario);
					if (datosSincronizacion != null)
						for (Servidor servidor : servidores.getServidoresActivos()) {
							if (servidor != servidorPrimario)
								if (sincronizar(servidor, datosSincronizacion))
									vista.agregarElemento(
											"Servidor: " + servidor.getNombre() + " sincronizado exitosamente");
								else
									vista.agregarElemento("Error al sincronizar el servidor: " + servidor.getNombre());
						}
					else
						vista.agregarElemento("Error al obtener los datos de sincronizacion");
				} else
					vista.agregarElemento("Servidor primario nulo o inactivo");
			} else
				vista.agregarElemento("No hay suficientes servidores activos para sincronizar");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				vista.agregarElemento(e.getLocalizedMessage());
			}
		}
	}

	private DatosSincronizacion getDatosSincronizacion(Servidor servidor) {
		VistaLogs vista = VistaLogs.getInstance();
		DatosSincronizacion datosSincronizacion = null;
		try {
			Socket socket;

			socket = new Socket(servidor.getDireccionIP(), servidor.getPuertoSincronizacion());

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

			output.writeObject("getDatos");

			datosSincronizacion = (DatosSincronizacion) input.readObject();

			input.close();
			output.close();
			socket.close();
		} catch (IOException | ClassNotFoundException e) {
			vista.agregarElemento(e.getLocalizedMessage());
		}

		return datosSincronizacion;
	}

	private boolean sincronizar(Servidor servidor, DatosSincronizacion datosSincronizacion) {
		VistaLogs vista = VistaLogs.getInstance();
		Socket socketSincronizacion;
		try {
			socketSincronizacion = new Socket(servidor.getDireccionIP(), servidor.getPuertoSincronizacion());

			ObjectOutputStream outputSincronizacion = new ObjectOutputStream(socketSincronizacion.getOutputStream());
			ObjectInputStream inputSincronizacion = new ObjectInputStream(socketSincronizacion.getInputStream());

			outputSincronizacion.writeObject(datosSincronizacion);

			// Cerrar conexión con el servidor destino
			inputSincronizacion.close();
			outputSincronizacion.close();
			socketSincronizacion.close();

			return true;
		} catch (IOException e) {
			vista.agregarElemento(e.getLocalizedMessage());
			return false;
		}
	}

}
