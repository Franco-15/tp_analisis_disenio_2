package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import comunes.Direccionamiento;
import comunes.EServidores;
import comunes.MensajeComunicacion;

public class Comunicacion{
	private static Comunicacion instance = null;
	private String ip;
	private int port;

	private Comunicacion(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public static Comunicacion getInstance() {
		if (instance == null)
			instance = new Comunicacion("localhost", 10);

		return instance;
	}

	private Direccionamiento getServidorActivo() {

		try {
			// Conectar al servidor monitor
			Socket socketMonitor;
			socketMonitor = new Socket(this.ip, this.port);

			ObjectInputStream inputMonitor;
			inputMonitor = new ObjectInputStream(socketMonitor.getInputStream());

			ObjectOutputStream outputMonitor = new ObjectOutputStream(socketMonitor.getOutputStream());

			// Enviar solicitud de publicación
			outputMonitor.writeObject(EServidores.ATENCION_CLIENTE);

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

	public MensajeComunicacion publicar(MensajeComunicacion mensaje) throws IOException, ClassNotFoundException {
		MensajeComunicacion response = null;
		Direccionamiento parametrosConexion = this.getServidorActivo();

		if (parametrosConexion != null) {
			System.out.println(
					"Servidor a conectarse: " + parametrosConexion.getIp() + ":" + parametrosConexion.getPuerto());
			Socket socket = new Socket(parametrosConexion.getIp(), parametrosConexion.getPuerto());

			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			
			if (mensaje != null) {
				output.writeObject(mensaje);
				response = (MensajeComunicacion) input.readObject();
			}
			else
				System.out.println("El mensaje a enviar es nulo");

			output.close();
			input.close();
		}else
			System.out.println("Error al obtener los parámetros de comunicacion con el servidor gestion turnos");
		
		return response;
		
	}
}
