package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorMonitorGestionTurnos implements Runnable {

	private static ServidorMonitorGestionTurnos instance = null;
	private int port;

	private ServidorMonitorGestionTurnos(int port) {

		this.port = port;
	}

	public static ServidorMonitorGestionTurnos getInstance() {
		if (instance == null)
			instance = new ServidorMonitorGestionTurnos(5013);

		return instance;
	}

	@Override
	public void run() {
		try {
			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.port);
				System.out.println("Servidor Monitoreo de Gestion Turnos escuchando en puerto " + this.port);

				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName() + ":" + clientSocket.getLocalPort());

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
				
				String mensaje = input.readLine();

				if(mensaje != null) {
					System.out.println("Servidor Gestion Turnos se encuentra activo");
					output.println("active");
				}

				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
