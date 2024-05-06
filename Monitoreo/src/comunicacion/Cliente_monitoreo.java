package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Cliente_monitoreo {
	private static Socket socket;
	private static ObjectOutputStream output;
	private static ObjectInputStream input;
	private int puerto;
	private String ip;

	public Cliente_monitoreo(int puerto, String ip) {
		this.puerto = puerto;
		this.ip = ip;
	}

	// Función para enviar el objeto HashMap al servidor y recibir una respuesta
	public Object enviarYRecibir() {
		
		Map<String, Object> respuesta = null;
		try {
			// Conectar al servidor monitor
			Socket socketMonitor = new Socket(this.ip, this.puerto);
			BufferedReader inputMonitor = new BufferedReader(new InputStreamReader(socketMonitor.getInputStream()));
			PrintWriter outputMonitor = new PrintWriter(socketMonitor.getOutputStream(), true);

			// Enviar solicitud de publicación
			outputMonitor.println("monitoreo");

			// Recibir el número de puerto del servidor destino
			int portDestino = Integer.parseInt(inputMonitor.readLine());

			// Cerrar conexión con el servidor monitor
			inputMonitor.close();
			outputMonitor.close();
			socketMonitor.close();

			System.out.println("Puerto destino monitoreo " + portDestino);

			if (portDestino != -1) {
				// Paso 1: Conectar al servidor en el puerto 12345
				socket = new Socket("localhost", portDestino);
				// Paso 2: Crear flujos de entrada y salida para comunicarse con el servidor
				output = new ObjectOutputStream(socket.getOutputStream());
				input = new ObjectInputStream(socket.getInputStream());

				// Paso 3: Crear el objeto Map con las métricas
				Map<String, Object> metricasMap = new HashMap<>();

				// paso 4: mandar al server
				output.writeObject(metricasMap);

				// Paso 5: Esperar la respuesta del servidor
				System.out.println("Esperando respuesta del servidor ");

				respuesta = (Map<String, Object>) input.readObject();

				input.close();
				output.close();
				socket.close();

			}
			return respuesta;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return respuesta;// Error al publicar
		}

	}

}
