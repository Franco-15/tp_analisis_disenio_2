package sincronizacion;

import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import modelo.colaEspera.IColaEspera;
import monitor.Monitor;
import monitor.Servidor;

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
		
		
		while (true) {

			System.out.println("Sincronizando servidores...");
			boolean servidorPrimarioIsActive = monitor.getServidorPrimarioGestionTurnos().getEstado();
			boolean servidorSecundarioIsActive = monitor.getServidorSecundarioGestionTurnos().getEstado();
			Servidor ultimoServidorActivo = monitor.getUltimoServidorActivo();
			Servidor servidorASincronizar = null;
			int puertoGetCola;
			int puertoSincronizacion;

			if (servidorPrimarioIsActive && servidorSecundarioIsActive) {

				if (ultimoServidorActivo == monitor.getServidorPrimarioGestionTurnos()) {
					puertoGetCola = monitor.getPuertoSincronizacionPrimario();
					puertoSincronizacion = monitor.getPuertoSincronizacionSecundario();
					servidorASincronizar = monitor.getServidorSecundarioGestionTurnos();
				} else {
					puertoGetCola = monitor.getPuertoSincronizacionSecundario();
					puertoSincronizacion = monitor.getPuertoSincronizacionPrimario();
					servidorASincronizar = monitor.getServidorPrimarioGestionTurnos();
				}

				IColaEspera colaEspera = null;
				try {
					Socket socketGetCola;
					
					System.out.println("Obteniendo cola de servidor: "+ ultimoServidorActivo.getNombre());
					socketGetCola = new Socket(ultimoServidorActivo.getDireccionIP(), puertoGetCola);

					ObjectOutputStream outputGetDatos = new ObjectOutputStream(socketGetCola.getOutputStream());
					ObjectInputStream inputGetDatos = new ObjectInputStream(socketGetCola.getInputStream());

					// Enviar solicitud de publicación
					outputGetDatos.writeObject("getDatos");

					// Recibir el número de puerto del servidor destino
					colaEspera = (IColaEspera) inputGetDatos.readObject();
					System.out.println(colaEspera);

					// Cerrar conexión con el servidor monitor
					inputGetDatos.close();
					outputGetDatos.close();
					socketGetCola.close();
				} catch (IOException | ClassNotFoundException e) {
					System.out.println(e.getMessage());
				}

				if (colaEspera != null) {
					// Conectar al servidor destino para publicar el mensaje
					Socket socketSincronizacion;
					try {
						socketSincronizacion = new Socket(servidorASincronizar.getDireccionIP(), puertoSincronizacion);
						
						ObjectOutputStream outputSincronizacion = new ObjectOutputStream(
								socketSincronizacion.getOutputStream());
						ObjectInputStream inputSincronizacion = new ObjectInputStream(
								socketSincronizacion.getInputStream());

						// Publicar el mensaje
						outputSincronizacion.writeObject(colaEspera);
						String response = (String) inputSincronizacion.readObject();
						System.out.println(response);

						// Cerrar conexión con el servidor destino
						inputSincronizacion.close();
						outputSincronizacion.close();
						socketSincronizacion.close();
					} catch (IOException | ClassNotFoundException e) {
						System.out.println(e.getMessage());
					}
				}
			}
			else if(!servidorSecundarioIsActive) {}
			else
				System.out.println("Los servidores no estan disponibles para realizar la sincronizacion");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			}
		}

	}
}
