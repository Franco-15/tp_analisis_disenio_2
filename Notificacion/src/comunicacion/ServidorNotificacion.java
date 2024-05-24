package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.TElementoNotificacion;
import notificacion.Controlador;

public class ServidorNotificacion {
	private ServerSocket serverSocket;
	private int puerto;
	private Controlador controlador;

	public ServidorNotificacion(int puerto) {
		this.puerto = puerto;
		this.controlador = new Controlador();
	}

	// Método para iniciar el servidor
	public void iniciarServidor() {
		try {

			serverSocket = new ServerSocket(this.puerto);
			System.out.println("Servidor Notificacion iniciado. Esperando conexiones...");

			while (true) {
				// Esperar a que el cliente se conecte
				Socket socket = serverSocket.accept();
				
				// Crear flujos de entrada y salida para comunicarse con el cliente
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				// ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

				// Imprimir mensaje indicando que se recibió el objeto
				TElementoNotificacion notificacion = (TElementoNotificacion) input.readObject();
				System.out.println("Objeto notificacion: " + notificacion.getDni() +" - "+ notificacion.getBox());

				// aca tengo que tener el objeto leido y pasarle los parametros o tal cual el
				// objeto en el new objeto_notificacion

				controlador.agregarCliente(notificacion);

			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			// Cerrar el ServerSocket cuando se detiene el servidor
			try {
				if (serverSocket != null && !serverSocket.isClosed()) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
