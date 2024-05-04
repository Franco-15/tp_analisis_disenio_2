package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MonitorRegistro implements Runnable {
	private int puertoMonitor;
	private MonitorGestionTurnos servidorActivo; // Nombre cambiable la verdad, no me convence

	public MonitorRegistro(int puertoMonitor) {
		this.puertoMonitor = puertoMonitor;
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(puertoMonitor);
			System.out.println("Monitor de servidores escuchando en puerto " + puertoMonitor);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				String mensaje = input.readLine();
				if (mensaje.equals("publicar")) {
					// Responder con el número de puerto para publicar
					output.println(servidorActivo.getPortPrimaryServer()); // Le envio a subsistema Registro donde debe
																			// publicar al cliente 
				}

				input.close();
				output.close();
				clientSocket.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
