package comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import turnos.TElementoNotificacion;

public class PublicacionNotificacion {
	
	private static PublicacionNotificacion instance = null;

	private String ip;
	private int port;

	private PublicacionNotificacion(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public static PublicacionNotificacion getInstance() {
		if (instance == null)
			instance = new PublicacionNotificacion("localhost", 4);

		return instance;
	}

	public void publicar(TElementoNotificacion mensaje) {
		try {
			Socket socket = new Socket(this.ip, this.port);
			
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

			if (mensaje != null) {
				String[] datos = new String[]{mensaje.getDni(), mensaje.getBox()};
				output.writeObject(datos);
			}

			output.close();
			socket.close();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
