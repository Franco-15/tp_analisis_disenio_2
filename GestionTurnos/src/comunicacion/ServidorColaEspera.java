package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorColaEspera implements Runnable{
	private static ServidorColaEspera instance = null;
	
	private int port;
	private GestionRecepcionServidor gestionMensajesRecibidos;
	
	private ServidorColaEspera(int port) {
		this.port = port;
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
	}

	public static ServidorColaEspera getInstance() {
		if (instance == null)
			instance = new ServidorColaEspera(5001);

		return instance;
	}

	@Override
	public void run() {
		try {

			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.port);
				System.out.println("Servidor Cola Espera escuchando en puerto" + this.port);

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
