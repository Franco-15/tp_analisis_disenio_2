package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Comunicacion {
	private String ip;
	private int port;

	public Comunicacion(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String obtenerCliente(String mensaje) {
		String response = "";

		try {

			// Conectar al servidor monitor
			Socket socketMonitor = new Socket(this.ip, this.port);
			BufferedReader inputMonitor = new BufferedReader(new InputStreamReader(socketMonitor.getInputStream()));
			PrintWriter outputMonitor = new PrintWriter(socketMonitor.getOutputStream(), true);

			// Enviar solicitud de publicación
			outputMonitor.println("atencionCliente");

			// Recibir el número de puerto del servidor destino
			int portDestino = Integer.parseInt(inputMonitor.readLine());

			// Cerrar conexión con el servidor monitor
			inputMonitor.close();
			outputMonitor.close();
			socketMonitor.close();

			System.out.println("Puerto destino monitoreo " + portDestino);

			if (portDestino != -1) {
				Socket socket = new Socket(this.ip, portDestino);

				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

				if (mensaje != null) {
					output.println(mensaje);
					response = input.readLine();
				}

				input.close();
				output.close();
				socket.close();

			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return response;
	}
}
