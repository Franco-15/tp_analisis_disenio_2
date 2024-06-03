package gestionServidores;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import comunes.Direccionamiento;
import comunes.EServidores;
import vistas.VistaLogs;

public class ServidorDireccionamiento implements Runnable {

	private Servidores servidores;
	private int puerto;

	public ServidorDireccionamiento(int puertoConexion, Servidores servidores) {
		this.servidores = servidores;
		this.puerto = puertoConexion;
	}

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(this.puerto)) {
			while (true) {
//				vista.agregarElemento("Servidor de Direccionamiento escuchando en puerto " + this.puerto);
				try {
					Socket clientSocket = serverSocket.accept();
//					vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

					try {
						ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
						ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
						EServidores servidorQueSolicito = (EServidores) input.readObject();

						Direccionamiento direccionamiento = null;

						if (servidorQueSolicito != null) {
							Servidor servidorPrimario = servidores.getServidorPrimario();
							if (servidorPrimario != null) {
								direccionamiento = new Direccionamiento();
								direccionamiento.setIp(servidorPrimario.getDireccionIP());
								if (servidorQueSolicito == EServidores.REGISTRO)
									direccionamiento.setPuerto(servidorPrimario.getPuertoRegistro());
								else if (servidorQueSolicito == EServidores.MONITOREO)
									direccionamiento.setPuerto(servidorPrimario.getPuertoMonitoreo());
								else if (servidorQueSolicito == EServidores.ATENCION_CLIENTE)
									direccionamiento.setPuerto(servidorPrimario.getPuertoAtencionCliente());
							}
						}
						output.writeObject(direccionamiento);

						input.close();
						output.close();
						clientSocket.close();
					} catch (ClassNotFoundException e) {
//						vista.agregarElemento("Error al leer el objeto: " + e.getMessage());
					} catch (IOException e) {
//						vista.agregarElemento("Error de E/S: " + e.getMessage());
					}

				} catch (IOException e) {
//					vista.agregarElemento("Error al aceptar conexión del cliente: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
//			vista.agregarElemento("Error al crear el ServerSocket: " + e.getMessage());
		}

	}
}
