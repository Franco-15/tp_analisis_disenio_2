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
	private int puertoSincronizacionPrimario;
	private int puertoSincronizacionSecundario;

	private Sincronizacion() {
		this.monitor = Monitor.getInstance();
		this.puertoSincronizacionPrimario = 16;
		this.puertoSincronizacionSecundario = 18;
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
					puertoGetCola = puertoSincronizacionPrimario;
					puertoSincronizacion = puertoSincronizacionSecundario;
					servidorASincronizar = monitor.getServidorSecundarioGestionTurnos();
				} else {
					puertoGetCola = puertoSincronizacionSecundario;
					puertoSincronizacion = puertoSincronizacionPrimario;
					servidorASincronizar = monitor.getServidorPrimarioGestionTurnos();
				}

				IColaEspera colaEspera = null;
				try {
					Socket socketGetCola;
					
					System.out.println("Obteniendo cola de servidor: "+ ultimoServidorActivo.getNombre());
					socketGetCola = new Socket(ultimoServidorActivo.getDireccionIP(), puertoGetCola);

					ObjectOutputStream outputGetCola = new ObjectOutputStream(socketGetCola.getOutputStream());
					ObjectInputStream inputGetCola = new ObjectInputStream(socketGetCola.getInputStream());

					// Enviar solicitud de publicación
					outputGetCola.writeObject("getCola");

					// Recibir el número de puerto del servidor destino
					colaEspera = (IColaEspera) inputGetCola.readObject();
					System.out.println(colaEspera);

					// Cerrar conexión con el servidor monitor
					inputGetCola.close();
					outputGetCola.close();
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
