package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Comunicacion {
	private static Comunicacion instance = null;
	private String ipMonitor;
	private int portMonitor;

	private Comunicacion(String ipMonitor, int portMonitor) {
		this.ipMonitor = ipMonitor;
		this.portMonitor = portMonitor;
	}

	public static Comunicacion getInstance() {
		if (instance == null)
			instance = new Comunicacion("localhost", 10); // Definir el puerto del servidor monitor

		return instance;
	}

	public String publicar(String mensaje) {
		String response = "";

		try {
			// Conectar al servidor monitor
			Socket socketMonitor = new Socket(this.ipMonitor, this.portMonitor);
			BufferedReader inputMonitor = new BufferedReader(new InputStreamReader(socketMonitor.getInputStream()));
			PrintWriter outputMonitor = new PrintWriter(socketMonitor.getOutputStream(), true);

			// Enviar solicitud de publicación
			outputMonitor.println("registro");

			// Recibir el número de puerto del servidor destino
			int portDestino = Integer.parseInt(inputMonitor.readLine());
			System.out.println("puerto destino registro: " + portDestino);
			
			// Cerrar conexión con el servidor monitor
			inputMonitor.close();
			outputMonitor.close();
			socketMonitor.close();
			
			if(portDestino != -1) {
				// Conectar al servidor destino para publicar el mensaje
				Socket socketDestino = new Socket("localhost", portDestino);
				BufferedReader inputDestino = new BufferedReader(new InputStreamReader(socketDestino.getInputStream()));
				PrintWriter outputDestino = new PrintWriter(socketDestino.getOutputStream(), true);

				// Publicar el mensaje
				outputDestino.println(mensaje);
				response = inputDestino.readLine();

				// Cerrar conexión con el servidor destino
				inputDestino.close();
				outputDestino.close();
				socketDestino.close();
			} else
				response = "2";

			return response;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return "2"; // Error al publicar
		}
	}
}