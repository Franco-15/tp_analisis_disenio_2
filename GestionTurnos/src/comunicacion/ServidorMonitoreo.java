package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class ServidorMonitoreo implements Runnable {

	private static ServidorMonitoreo instance = null;

	private int puerto;
	private GestionRecepcionServidor gestionMensajesRecibidos;

	private ServidorMonitoreo(int puerto) {
		this.puerto = puerto;
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorMonitoreo getInstance() {
		if (instance == null)
			instance = new ServidorMonitoreo(3);

		return instance;
	}

	@Override
	public void run() {
		try {

			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.puerto);
				System.out.println("Servidor Monitoreo escuchando en puerto" + this.puerto);
				
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

				Map<String, Object> mensaje = (Map<String, Object>) input.readObject();
				
				if (mensaje != null) {
					System.out.println("Cliente: " + mensaje);
					Map<String, Object> result = gestionMensajesRecibidos.actualizarMetricas();
					output.writeObject(result);
				}

				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException | ClassNotFoundException e ) {
			System.out.println(e.getMessage());
		}

	}
}
