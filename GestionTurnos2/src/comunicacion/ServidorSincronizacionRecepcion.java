package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorSincronizacionRecepcion {
	private int puerto;
	private GestionRecepcionServidor gestionMensajesRecibidos;
	private static ServidorSincronizacionRecepcion instance = null;

	private ServidorSincronizacionRecepcion(int puerto) {
		this.puerto = puerto;
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorSincronizacionRecepcion getInstance() {
		if (instance == null)
			instance = new ServidorSincronizacionRecepcion(18);
		return instance;
	}

	public void iniciar() {
		try {
			ServerSocket servidorSocket = new ServerSocket(puerto);
			System.out.println("Servidor de Sincronización de Envío iniciado en el puerto " + puerto);

			while (true) {
				Socket clienteSocket = servidorSocket.accept();
				System.out.println("Cliente conectado desde " + clienteSocket.getInetAddress().getHostName());

				// Para recibir el mensaje del cliente
				BufferedReader entrada = new BufferedReader(new InputStreamReader(clienteSocket.getInputStream()));
				String mensaje = entrada.readLine();
				if (mensaje != null)
					this.gestionMensajesRecibidos.agregarClienteAColaEspera(mensaje);
				// Imprimir el mensaje recibido
				System.out.println("Mensaje recibido del cliente: " + mensaje);

				// Cerrar el flujo de entrada y el socket del cliente
				entrada.close();
				clienteSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
