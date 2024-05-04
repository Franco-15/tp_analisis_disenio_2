package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MonitorGestionTurnos implements Runnable {
	private String primaryServerHost;
	private int primaryServerPort;
	private String secondaryServerHost;
	private int secondaryServerPort;

	public MonitorGestionTurnos(String primaryServerHost, int primaryServerPort, String secondaryServerHost,
			int secondaryServerPort) {
		this.primaryServerHost = primaryServerHost;
		this.primaryServerPort = primaryServerPort;
		this.secondaryServerHost = secondaryServerHost;
		this.secondaryServerPort = secondaryServerPort;
	}

	public void run() {
		try {
			boolean primaryActive = true; // Iniciar con el primario activo

			while (true) {
				Thread.sleep(5000); // Espera 5 segundos antes de verificar nuevamente

				// Verificar el estado del servidor primario
				boolean newPrimaryActive = checkServer(primaryServerHost, primaryServerPort);

				if (!newPrimaryActive && primaryActive) {
					// Si el servidor primario estaba activo y ahora está inactivo,
					// activar el servidor secundario
					System.out.println("El servidor primario está inactivo. Activando el secundario...");

					desactivarServidor(primaryServerHost, primaryServerPort); // Desactivar primario
					activarServidor(secondaryServerHost, secondaryServerPort); // Activar secundario

					primaryActive = false; // Actualizar estado
				} else if (newPrimaryActive && !primaryActive) {
					// Si el servidor primario estaba inactivo y ahora está activo,
					// desactivar el servidor secundario
					System.out.println("El servidor primario está activo. Desactivando el secundario...");

					desactivarServidor(secondaryServerHost, secondaryServerPort); // Desactivar secundario
					activarServidor(primaryServerHost, primaryServerPort); // Activar primario

					primaryActive = true; // Actualizar estado
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean checkServer(String serverHost, int serverPort) {
		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

			// Enviar un mensaje al servidor para verificar su estado
			out.writeObject("check");
			// Esperar la respuesta del servidor
			String response = (String) in.readObject();

			return "active".equals(response); // El servidor está activo si responde "active"
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false; // El servidor está inactivo si hay un error al conectar
		}
	}

	private void activarServidor(String serverHost, int serverPort) {
		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

			// Enviar un mensaje al servidor para activarlo
			out.writeObject("activate");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void desactivarServidor(String serverHost, int serverPort) {
		try (Socket socket = new Socket(serverHost, serverPort);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

			// Enviar un mensaje al servidor para desactivarlo
			out.writeObject("deactivate");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPortPrimaryServer() {
		return this.primaryServerPort;
	}
}
