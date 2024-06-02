package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import comunes.Cliente;
import comunes.Direccionamiento;
import comunes.EServidores;
import comunes.Respuestas;
import comunes.TRespuesta;

public class Comunicacion {
	private static Comunicacion instance = null;
	private String ipMonitor;
	private int portMonitor;

	private Comunicacion(String ipMonitor, int portMonitor) {
		this.ipMonitor = ipMonitor;
		this.portMonitor = portMonitor;
	}
	
	public static Comunicacion getInstance() {
		if(instance == null)
			instance = new Comunicacion("localhost", 9);
		
		return instance;
	}

	public TRespuesta publicar(Cliente cliente) {
		TRespuesta respuesta = Respuestas.ErrorDeSistema();

		try {
			
			Socket socketMonitor = new Socket(this.ipMonitor, this.portMonitor);
			ObjectInputStream inputMonitor = new ObjectInputStream(socketMonitor.getInputStream());
			ObjectOutputStream outputMonitor = new ObjectOutputStream(socketMonitor.getOutputStream());

			// Enviar solicitud de publicación
			outputMonitor.writeObject(EServidores.REGISTRO);

			// Recibir el número de puerto del servidor destino
			Direccionamiento parametrosConexion;
			parametrosConexion = (Direccionamiento) inputMonitor.readObject();

			// Cerrar conexión con el servidor monitor
			inputMonitor.close();
			outputMonitor.close();
			socketMonitor.close();

			// Conectar al servidor destino para publicar el mensaje
			if (parametrosConexion != null) {
				System.out.println(
						"Servidor a conectarse: " + parametrosConexion.getIp() + ":" + parametrosConexion.getPuerto());
				Socket socket = new Socket(parametrosConexion.getIp(), parametrosConexion.getPuerto());
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

				// Publicar el mensaje
				output.writeObject(cliente);
				respuesta = (TRespuesta) input.readObject();

				// Cerrar conexión con el servidor destino
				input.close();
				output.close();
				socket.close();
			}
			return respuesta;
		} catch (IOException | ClassNotFoundException e) {
			System.out.println(e.getMessage());
			return Respuestas.ErrorDeSistema();
		}
	}
}
