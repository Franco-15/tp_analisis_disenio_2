package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class Monitor implements Runnable{
	private static Monitor instance = null;
	private Servidor servidorPrimarioGestionTurnos;
	private Servidor servidorSecundarioGestionTurnos;

	private int checkPrimaryServerPort;
	private int checkSecondaryServerPort;
	private Servidor servidorActivo;
	private Servidor ultimoServidorActivo;
	
	private int puertoSincronizacionPrimario;
	private int puertoSincronizacionSecundario;

	private Monitor() {
		this.servidorPrimarioGestionTurnos = new Servidor("gestionTurnos1", "localhost", 5001, 3, 2);
		this.servidorSecundarioGestionTurnos = new Servidor("gestionTurnos2", "localhost", 7, 20, 21);
		this.checkPrimaryServerPort = 13;
		this.checkSecondaryServerPort = 17;
		this.servidorActivo = this.servidorPrimarioGestionTurnos;
		this.ultimoServidorActivo = this.servidorPrimarioGestionTurnos;
		this.puertoSincronizacionPrimario=16;
		this.puertoSincronizacionSecundario = 18;
	}

	public static Monitor getInstance() {
		if (instance == null)
			instance = new Monitor();

		return instance;
	}

	public void run() {
		try {
			while (true) {

				System.out.println("Analizando estado de los servidores...");

				servidorPrimarioGestionTurnos.setEstado(isServerActive(servidorPrimarioGestionTurnos, this.checkPrimaryServerPort));
				servidorSecundarioGestionTurnos
						.setEstado(isServerActive(servidorSecundarioGestionTurnos, this.checkSecondaryServerPort));
				
				if (servidorPrimarioGestionTurnos.getEstado()) {
					System.out.println("El servidor primario está activo");
					this.servidorPrimarioGestionTurnos.setUltimaVezActivo(LocalDateTime.now());
					this.servidorActivo = servidorPrimarioGestionTurnos;
					this.ultimoServidorActivo = servidorPrimarioGestionTurnos;
				}
				else if (servidorSecundarioGestionTurnos.getEstado()) {
					int puertoAux = this.checkPrimaryServerPort;
					int puertoSincroAux=this.puertoSincronizacionPrimario;
					Servidor servidorAux;
					if(servidorActivo.getNombre().equals("gestionTurnos1"))
						servidorAux = new Servidor("gestionTurnos1", "localhost", 5001, 3, 2);
					else
						servidorAux = new Servidor("gestionTurnos2", "localhost", 7, 20, 21);
					this.servidorSecundarioGestionTurnos.setUltimaVezActivo(LocalDateTime.now());
					this.servidorActivo = this.servidorSecundarioGestionTurnos;
					this.ultimoServidorActivo = this.servidorSecundarioGestionTurnos;
					this.puertoSincronizacionPrimario=this.puertoSincronizacionSecundario;
					
					
					this.servidorPrimarioGestionTurnos = this.servidorSecundarioGestionTurnos;
					this.checkPrimaryServerPort = this.checkSecondaryServerPort;
					this.servidorSecundarioGestionTurnos = servidorAux;
					this.checkSecondaryServerPort = puertoAux;
					this.puertoSincronizacionSecundario=puertoSincroAux;
					System.out.println("El servidor secundario está activo");
				}
				else {
					System.out.println("No existe ningún servidor activo");
					this.servidorActivo = null;
					if(servidorPrimarioGestionTurnos.getUltimaVezActivo().isAfter(servidorSecundarioGestionTurnos.getUltimaVezActivo()))
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
				System.out.println("Reintentando conexión a servidor " + servidor.getDireccionIP() + ":" + serverPort
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

	public synchronized int getActiveServerPort(String mensaje) {
		int port = -1;
		if(this.servidorActivo != null)
			if(mensaje.equals("registro"))
				port = this.servidorActivo.getPuertoRegistro();
			else if(mensaje.equals("monitoreo"))
				port = this.servidorActivo.getPuertoMonitoreo();
			else if(mensaje.equals("atencionCliente"))
				port = this.servidorActivo.getPuertoAtencionCliente();
		
		return port;
	}

	public int getPuertoSincronizacionPrimario() {
		return puertoSincronizacionPrimario;
	}

	public int getPuertoSincronizacionSecundario() {
		return puertoSincronizacionSecundario;
	}


}
