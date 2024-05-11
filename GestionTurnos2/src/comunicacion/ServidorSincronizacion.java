package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.DatosSincronizacion;
import comunes.IColaEspera;

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
			instance = new ServidorSincronizacion(18);

		return instance;
	}

	@Override
	public void run() {
		try {

			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.puerto);
				System.out.println("Servidor sincronizacion escuchando en puerto" + this.puerto);

				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

				Object mensaje = (Object) input.readObject();

				if (mensaje != null) {
					if (mensaje.equals("getDatos")) {
						DatosSincronizacion result = gestionMensajesRecibidos.getDatosSincronizacion();
						output.writeObject(result);
					}else {
						System.out.println("Entro aca");
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
			System.out.println(e.getMessage());
		}

	}
}