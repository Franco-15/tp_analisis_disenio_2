package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

import vistas.VistaLogs;

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
			instance = new ServidorMonitoreo(20);

		return instance;
	}

	@Override
	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try {
			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.puerto);
				vista.agregarElemento("Servidor monitoreo escuchando en puerto " + this.puerto);

				Socket clientSocket = serverSocket.accept();
				vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());

				Map<String, Object> mensaje = (Map<String, Object>) input.readObject();

				if (mensaje != null) {
					vista.agregarElemento("Cliente: " + mensaje);
					Map<String, Object> result = gestionMensajesRecibidos.actualizarMetricas();
					output.writeObject(result);
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
