package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.DatosSincronizacion;
import vistas.VistaLogs;

public class ServidorSincronizacion implements Runnable {

	private static ServidorSincronizacion instance = null;

	private int puerto;
	private GestionRecepcionServidor gestionMensajesRecibidos;

	private ServidorSincronizacion(int puerto) {
		this.puerto = puerto;
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorSincronizacion getInstance() {
		if (instance == null)
			instance = new ServidorSincronizacion(16);

		return instance;
	}

	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try {
			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.puerto);
				vista.agregarElemento("Servidor sincronizacion escuchando en puerto " + this.puerto);

				Socket clientSocket = serverSocket.accept();
				vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

				Object mensaje = (Object) input.readObject();

				if (mensaje != null) {
					if (mensaje.equals("getDatos")) {
						DatosSincronizacion result = gestionMensajesRecibidos.getDatosSincronizacion();
						output.writeObject(result);
					}else {
						gestionMensajesRecibidos.setDatosSincronizacion((DatosSincronizacion)mensaje);
						output.writeObject("Sincronizacion exitosa!");
					}
				}

				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException | ClassNotFoundException e) {
			vista.agregarElemento(e.getMessage());
		}

	}
}
