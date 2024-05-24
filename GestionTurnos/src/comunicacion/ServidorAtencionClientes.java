package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.MensajeAtencionCliente;
import comunes.MensajeComunicacion;
import vistas.VistaLogs;

public class ServidorAtencionClientes implements Runnable {

	private static ServidorAtencionClientes instance = null;

	private int port;
	private GestionRecepcionServidor gestionMensajesRecibidos;

	private ServidorAtencionClientes(int port) {
		this.port = port;
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorAtencionClientes getInstance() {
		if (instance == null)
			instance = new ServidorAtencionClientes(2);

		return instance;
	}

	@Override
	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try (ServerSocket serverSocket = new ServerSocket(this.port)) {
			vista.agregarElemento("Servidor de atencion al cliente escuchando en puerto " + this.port);

			while (true) {
				try (Socket clientSocket = serverSocket.accept()) {
					vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName() + ":"
							+ clientSocket.getLocalPort());
					System.out.println("Cliente conectado");

					try (ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
							ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

						MensajeComunicacion mensaje = (MensajeComunicacion) input.readObject();
						if (mensaje != null) {
							MensajeAtencionCliente mensajeAtencion = (MensajeAtencionCliente) mensaje.getMensaje();

							if (mensajeAtencion.getCliente() == null) {
								MensajeAtencionCliente result = gestionMensajesRecibidos
										.atenderCliente(mensajeAtencion);
								mensaje.setMensaje(result);
								output.writeObject(mensaje);
							} else {
								MensajeComunicacion result = gestionMensajesRecibidos.resultadoAtencion(mensajeAtencion);
								mensaje.setMensaje(result);
								output.writeObject(mensaje);
							}

						}

					} catch (ClassNotFoundException e) {
						System.out.println("Error de clase no encontrada: " + e.getMessage());
						vista.agregarElemento("Error de clase no encontrada: " + e.getMessage());
					}
				} catch (IOException e) {
					System.out.println("Error de E/S con el cliente: " + e.getMessage());
					vista.agregarElemento("Error de E/S con el cliente: " + e.getMessage());
				}

			}
		} catch (IOException e) {
			System.out.println("Error de E/S del servidor: " + e.getMessage());
			vista.agregarElemento("Error de E/S del servidor: " + e.getMessage());
		}
	}
}