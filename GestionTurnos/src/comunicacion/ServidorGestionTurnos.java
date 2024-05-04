package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServidorGestionTurnos implements Runnable {

	private static ServidorGestionTurnos instance = null;
	private int port;
	private boolean active = true;
	private GestionRecepcionServidor gestionMensajesRecibidos;

	private ServidorGestionTurnos(int primaryPort, int secondaryPort) {

		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorGestionTurnos getInstance() {
		if (instance == null)
			instance = new ServidorGestionTurnos(2000, 2001); // Puerto primario: 2000, Puerto secundario: 2001

		return instance;
	}

	public synchronized void setActive(boolean active) {
		this.active = active;
	}

	public synchronized boolean isActive() {
		return active;
	}

	public synchronized int getPort() {
		return port;
	}

	@Override
	public void run() {
		try {
			while (isActive()) {
				ServerSocket serverSocket = new ServerSocket(getPort());
				System.out.println("Servidor Atencion Cliente escuchando en puerto " + getPort());

				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				String mensaje;
				while ((mensaje = input.readLine()) != null) {
					System.out.println("Numero de Box: " + mensaje);
					String result = gestionMensajesRecibidos.atenderCliente(mensaje);
					output.println(result);
				}

				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
			System.out.println("Servidor Atencion Cliente detenido.");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	// Devuelve la dirección IP del servidor. Esta IP será enviada al proyecto
	// registro cuando el monitor detecte que el servidor primario ha fallado.
	public String obtenerDireccionIP() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para verificar si el servidor está activo o no
	public synchronized boolean isServerActive() {
		return isActive();
	}
}
