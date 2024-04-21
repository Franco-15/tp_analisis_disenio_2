package colaEspera;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RecepcionRegistro implements IRegistro{
	private static RecepcionRegistro instance = null;
	
	private int port;
	private GestionRecepcion gestionMensajesRecibidos;
	
	private RecepcionRegistro(int port) {
		this.port = port;
		this.gestionMensajesRecibidos = new GestionRecepcion();
	}

	public static RecepcionRegistro getInstance() {
		if (instance == null)
			instance = new RecepcionRegistro(12345);

		return instance;
	}

	public void recepcionarRegistro() {
		try {

			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.port);
				System.out.println("Servidor iniciado. Esperando conexiones...");

				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				String mensaje;
				while ((mensaje = input.readLine()) != null) {
					System.out.println("Cliente con documento: " + mensaje);
					String result = gestionMensajesRecibidos.agregarClienteAColaEspera(mensaje);
					output.println(result);
				}

				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
