package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import comunes.Direccionamiento;
import vistas.VistaLogs;

public class Monitor implements Runnable {
	private static Monitor instance = null;
	private Servidor servidorPrimarioGestionTurnos;
	private Servidor servidorSecundarioGestionTurnos;

	private int checkPrimaryServerPort;
	private int checkSecondaryServerPort;
	private Servidor servidorActivo;
	private Servidor ultimoServidorActivo;

	private int puertoSincronizacionPrimario;
	private int puertoSincronizacionSecundario;

	private VistaLogs vista;

	private Monitor() {
		this.servidorPrimarioGestionTurnos = new Servidor("gestionTurnos1", "localhost", 1, 3, 2);
		this.servidorSecundarioGestionTurnos = new Servidor("gestionTurnos2", "localhost", 7, 5, 8);
		this.checkPrimaryServerPort = 16;
		this.checkSecondaryServerPort = 18;
		this.servidorActivo = this.servidorPrimarioGestionTurnos;
		this.ultimoServidorActivo = this.servidorPrimarioGestionTurnos;
		this.puertoSincronizacionPrimario = 15;
		this.puertoSincronizacionSecundario = 13;
		this.vista = VistaLogs.getInstance();
	}

	public static Monitor getInstance() {
		if (instance == null)
			instance = new Monitor();

		return instance;
	}

	public void run() {
		try {
			while (true) {

				vista.agregarElemento("Analizando estado de los servidores...");

				servidorPrimarioGestionTurnos
						.setEstado(isServerActive(servidorPrimarioGestionTurnos, this.checkPrimaryServerPort));
				servidorSecundarioGestionTurnos
						.setEstado(isServerActive(servidorSecundarioGestionTurnos, this.checkSecondaryServerPort));

				if (servidorPrimarioGestionTurnos.getEstado()) {
					vista.agregarElemento("El servidor primario está activo");
					this.servidorPrimarioGestionTurnos.setUltimaVezActivo(LocalDateTime.now());
					this.servidorActivo = servidorPrimarioGestionTurnos;
					this.ultimoServidorActivo = servidorPrimarioGestionTurnos;
				} else if (servidorSecundarioGestionTurnos.getEstado()) {
					int puertoAux = this.checkPrimaryServerPort;
					int puertoSincroAux = this.puertoSincronizacionPrimario;
					Servidor servidorAux;
					if (servidorActivo.getNombre().equals("gestionTurnos1"))
						servidorAux = new Servidor("gestionTurnos1", "localhost", 1, 3, 2);
					else
						servidorAux = new Servidor("gestionTurnos2", "localhost", 7, 20, 21);
					this.servidorSecundarioGestionTurnos.setUltimaVezActivo(LocalDateTime.now());
					this.servidorActivo = this.servidorSecundarioGestionTurnos;
					this.ultimoServidorActivo = this.servidorSecundarioGestionTurnos;
					this.puertoSincronizacionPrimario = this.puertoSincronizacionSecundario;

					this.servidorPrimarioGestionTurnos = this.servidorSecundarioGestionTurnos;
					this.checkPrimaryServerPort = this.checkSecondaryServerPort;
					this.servidorSecundarioGestionTurnos = servidorAux;
					this.checkSecondaryServerPort = puertoAux;
					this.puertoSincronizacionSecundario = puertoSincroAux;
					vista.agregarElemento("El servidor secundario está activo");
				} else {
					vista.agregarElemento("No existe ningún servidor activo");
					this.servidorActivo = null;
					if (servidorPrimarioGestionTurnos.getUltimaVezActivo()
							.isAfter(servidorSecundarioGestionTurnos.getUltimaVezActivo()))
						this.ultimoServidorActivo = servidorPrimarioGestionTurnos;
					else
						this.ultimoServidorActivo = servidorSecundarioGestionTurnos;
				}

				Thread.sleep(5000);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Servidor getUltimoServidorActivo() {
		return ultimoServidorActivo;
	}

	public Servidor getServidorPrimarioGestionTurnos() {
		return servidorPrimarioGestionTurnos;
	}

	public Servidor getServidorSecundarioGestionTurnos() {
		return servidorSecundarioGestionTurnos;
	}

	public Servidor getServidorActivo() {
		return servidorActivo;
	}

	private boolean isServerActive(Servidor servidor, int serverPort) {
		int retryCount = 0;
		int maxRetries = 3;
		boolean isConnected = false;
		Socket socket = null;

		while (retryCount < maxRetries && !isConnected) {
			try {
				socket = new Socket(servidor.getDireccionIP(), serverPort);
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

				// Enviar un mensaje al servidor para verificar su estado
				output.println("check");
				// Esperar la respuesta del servidor
				String response = (String) input.readLine();

				isConnected = "active".equals(response); // El servidor está activo si responde "active"

				socket.close();
				output.close();
				input.close();
			} catch (IOException e) {
				// Manejar la excepción de conexión
				e.getMessage();
			}

			if (!isConnected) {
				retryCount++;
				vista.agregarElemento("Reintentando conexión a servidor " + servidor.getDireccionIP() + ":" + serverPort
						+ " (" + retryCount + "/" + maxRetries + ")");
				try {
					Thread.sleep(1000); // Esperar antes de intentar nuevamente
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}

		return isConnected;
	}

	public synchronized Direccionamiento getPuertoDeServidorActivo(int puertoDireccionamiento, Direccionamiento parametrosConexion) {
		if (this.servidorActivo != null) {
			parametrosConexion.setIp(this.servidorActivo.getDireccionIP());
			if (puertoDireccionamiento == 9)
				parametrosConexion.setPuerto(this.servidorActivo.getPuertoRegistro());
			else if (puertoDireccionamiento == 11)
				parametrosConexion.setPuerto(this.servidorActivo.getPuertoMonitoreo());
			else if (puertoDireccionamiento == 10)
				parametrosConexion.setPuerto(this.servidorActivo.getPuertoAtencionCliente());
		}else
			parametrosConexion = null;

		return parametrosConexion;
	}

	public int getPuertoSincronizacionPrimario() {
		return puertoSincronizacionPrimario;
	}

	public int getPuertoSincronizacionSecundario() {
		return puertoSincronizacionSecundario;
	}

}
