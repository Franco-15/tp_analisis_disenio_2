package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import vistas.VistaLogs;

public class ServidorMonitorGestionTurnos implements Runnable {

	private static ServidorMonitorGestionTurnos instance = null;
	private int port;

	private ServidorMonitorGestionTurnos(int port) {

		this.port = port;
	}

	public static ServidorMonitorGestionTurnos getInstance() {
		if (instance == null)
			instance = new ServidorMonitorGestionTurnos(13);

		return instance;
	}

	@Override
	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try {
			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.port);
				vista.agregarElemento("Servidor monitor de gestion de turnos escuchando en puerto " + this.port);

				Socket clientSocket = serverSocket.accept();
				vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName() + ":" + clientSocket.getLocalPort());

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
				
				String mensaje = input.readLine();

				if(mensaje != null) {
					vista.agregarElemento("El servidor gestion de turnos se encuentra activo");
					output.println("active");
				}

				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			vista.agregarElemento(e.getMessage());
		}
	}
}
