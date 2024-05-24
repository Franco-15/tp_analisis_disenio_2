package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.Cliente;
import comunes.TRespuesta;
import vistas.VistaLogs;

public class ServidorColaEspera implements Runnable {
	private static ServidorColaEspera instance = null;

	private int port;
	private GestionRecepcionServidor gestionMensajesRecibidos;

	private ServidorColaEspera(int port) {
		this.port = port;
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorColaEspera getInstance() {
		if (instance == null)
			instance = new ServidorColaEspera(1);

		return instance;
	}

	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try (ServerSocket serverSocket = new ServerSocket(this.port)) {
			vista.agregarElemento("Servidor de cola de espera escuchando en puerto " + this.port);

			while (true) {
				try (Socket clientSocket = serverSocket.accept()) {
					vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName() + ":"
							+ clientSocket.getLocalPort());
					System.out.println("Cliente conectado");

					try (ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
							ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

						Cliente cliente = (Cliente) input.readObject();
						System.out.println(cliente.getDni());

//						 Si el cliente no es nulo, procesa la solicitud.
						if (cliente != null) {
							// Suponiendo que 'cliente' es un String que contiene el documento del cliente
							vista.agregarElemento("Cliente con documento: " + cliente.getDni());
							TRespuesta result = gestionMensajesRecibidos.agregarClienteAColaEspera(cliente);
							output.writeObject(result);
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
