package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import comunes.Direccionamiento;
import comunes.EServidores;

public class Comunicacion {
	private int puerto;
	private String ip;

	public Comunicacion(int puerto, String ip) {
		this.puerto = puerto;
		this.ip = ip;
	}

	
	private Direccionamiento getServidorActivo() {

		try {
			// Conectar al servidor monitor
			Socket socketMonitor;
			socketMonitor = new Socket(this.ip, this.puerto);

			ObjectInputStream inputMonitor;
			inputMonitor = new ObjectInputStream(socketMonitor.getInputStream());

			ObjectOutputStream outputMonitor = new ObjectOutputStream(socketMonitor.getOutputStream());

			// Enviar solicitud de publicación
			outputMonitor.writeObject(EServidores.MONITOREO);

			// Recibir el número de puerto del servidor destino
			Direccionamiento parametrosConexion;

			parametrosConexion = (Direccionamiento) inputMonitor.readObject();

			// Cerrar conexión con el servidor monitor
			inputMonitor.close();
			outputMonitor.close();
			socketMonitor.close();

			return parametrosConexion;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error obteniendo los parametros de conexion");
			return null;
		}
	}
	
	// Función para enviar el objeto HashMap al servidor y recibir una respuesta
	public Object publicar() {
		
		try {
			Map<String, Object> respuesta = null;
			Direccionamiento parametrosConexion = this.getServidorActivo();

			if (parametrosConexion != null) {
				// Paso 1: Conectar al servidor en el puerto 12345
				System.out.println(
						"Servidor a conectarse: " + parametrosConexion.getIp() + ":" + parametrosConexion.getPuerto());
				Socket socket = new Socket(parametrosConexion.getIp(), parametrosConexion.getPuerto());
				// Paso 2: Crear flujos de entrada y salida para comunicarse con el servidor
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

				// Paso 3: Crear el objeto Map con las métricas
				Map<String, Object> metricasMap = new HashMap<>();

				// paso 4: mandar al server
				output.writeObject(metricasMap);

				respuesta = (Map<String, Object>) input.readObject();

				input.close();
				output.close();
				socket.close();

			}
			return respuesta;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return null;// Error al publicar
		}

	}

}
