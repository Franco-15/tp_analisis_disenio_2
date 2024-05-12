package sincronizacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import comunes.DatosSincronizacion;
import monitor.Monitor;
import monitor.Servidor;
import vistas.VistaLogs;

public class Sincronizacion implements Runnable {

	private static Sincronizacion instance = null;
	private Monitor monitor;
	


	private Sincronizacion() {
		this.monitor = Monitor.getInstance();

	}

	public static Sincronizacion getInstance() {
		if (instance == null)
			instance = new Sincronizacion();

		return instance;
	}

	@Override
	public void run() {
		
		VistaLogs vista = VistaLogs.getInstance();
		while (true) {

			vista.agregarElemento("Sincronizando servidores...");
			boolean servidorPrimarioIsActive = monitor.getServidorPrimarioGestionTurnos().getEstado();
			boolean servidorSecundarioIsActive = monitor.getServidorSecundarioGestionTurnos().getEstado();
			Servidor ultimoServidorActivo = monitor.getUltimoServidorActivo();
			Servidor servidorASincronizar = null;
			int puertoGetDatos;
			int puertoSincronizacion;

			if (servidorPrimarioIsActive && servidorSecundarioIsActive) {

				if (ultimoServidorActivo == monitor.getServidorPrimarioGestionTurnos()) {
					puertoGetDatos = monitor.getPuertoSincronizacionPrimario();
					puertoSincronizacion = monitor.getPuertoSincronizacionSecundario();
					servidorASincronizar = monitor.getServidorSecundarioGestionTurnos();
				} else {
					puertoGetDatos = monitor.getPuertoSincronizacionSecundario();
					puertoSincronizacion = monitor.getPuertoSincronizacionPrimario();
					servidorASincronizar = monitor.getServidorPrimarioGestionTurnos();
				}

				DatosSincronizacion datosSincronizacion = null;
				try {
					Socket socketGetDatos;
					
					vista.agregarElemento("Obteniendo cola de servidor: "+ ultimoServidorActivo.getNombre());
					socketGetDatos = new Socket(ultimoServidorActivo.getDireccionIP(), puertoGetDatos);

					ObjectOutputStream outputGetDatos = new ObjectOutputStream(socketGetDatos.getOutputStream());
					ObjectInputStream inputGetDatos = new ObjectInputStream(socketGetDatos.getInputStream());

					// Enviar solicitud de publicación
					outputGetDatos.writeObject("getDatos");

					// Recibir el número de puerto del servidor destino
					datosSincronizacion = (DatosSincronizacion) inputGetDatos.readObject();

					// Cerrar conexión con el servidor monitor
					inputGetDatos.close();
					outputGetDatos.close();
					socketGetDatos.close();
				} catch (IOException | ClassNotFoundException e) {
					vista.agregarElemento(e.getMessage());
				}

				if (datosSincronizacion != null) {
					// Conectar al servidor destino para publicar el mensaje
					Socket socketSincronizacion;
					try {
						socketSincronizacion = new Socket(servidorASincronizar.getDireccionIP(), puertoSincronizacion);
						
						ObjectOutputStream outputSincronizacion = new ObjectOutputStream(
								socketSincronizacion.getOutputStream());
						ObjectInputStream inputSincronizacion = new ObjectInputStream(
								socketSincronizacion.getInputStream());

						// Publicar el mensaje
						outputSincronizacion.writeObject(datosSincronizacion);
						String response = (String) inputSincronizacion.readObject();
						vista.agregarElemento(response);

						// Cerrar conexión con el servidor destino
						inputSincronizacion.close();
						outputSincronizacion.close();
						socketSincronizacion.close();
					} catch (IOException | ClassNotFoundException e) {
						vista.agregarElemento(e.getMessage());
					}
				}
			}
			else if(!servidorSecundarioIsActive) {}
			else
				vista.agregarElemento("Los servidores no estan disponibles para realizar la sincronizacion");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				vista.agregarElemento(e.getMessage());
			}
		}

	}
}
