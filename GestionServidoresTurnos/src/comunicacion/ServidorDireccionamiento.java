package comunicacion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.Direccionamiento;
import monitor.Monitor;
import vistas.VistaLogs;

public class ServidorDireccionamiento implements Runnable {
	private int port;
	private Monitor monitor;
	private ServerSocket serverSocket;

	public ServidorDireccionamiento(int port) {
		this.port = port;
		this.monitor = Monitor.getInstance();
	}

	@Override
	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try {
			this.serverSocket = new ServerSocket(this.port);

			while (true) {
				vista.agregarElemento("Servidor de Direccionamiento escuchando en puerto " + this.port);
				try{
					Socket clientSocket = serverSocket.accept();
					vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

					try {
						ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
						ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
						Direccionamiento parametrosConexion = (Direccionamiento) input.readObject();

						Direccionamiento respuesta = null;

						if (parametrosConexion != null) {
							respuesta = this.monitor.getServidorActivo(this.port, parametrosConexion);
						}
						output.writeObject(respuesta);
						
						input.close();
						output.close();
						clientSocket.close();
					} catch (ClassNotFoundException e) {
						vista.agregarElemento("Error al leer el objeto: " + e.getMessage());
					} catch (IOException e) {
						vista.agregarElemento("Error de E/S: " + e.getMessage());
					}
					
				} catch (IOException e) {
					vista.agregarElemento("Error al aceptar conexión del cliente: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			vista.agregarElemento("Error al crear el ServerSocket: " + e.getMessage());
		} finally {
			if (serverSocket != null && !serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					vista.agregarElemento("Error al cerrar el ServerSocket: " + e.getMessage());
				}
			}
		}
	}
}
