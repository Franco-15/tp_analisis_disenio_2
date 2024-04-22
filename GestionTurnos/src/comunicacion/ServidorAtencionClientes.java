package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAtencionClientes implements Runnable{

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
		try {

			while (true) {
				ServerSocket serverSocket = new ServerSocket(this.port);
				System.out.println("Servidor Atencion Cliente escuchando en puerto" + this.port);

				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				String mensaje;
				while ((mensaje = input.readLine()) != null) {
					System.out.println("Numero de Box: " + mensaje);
					String result = gestionMensajesRecibidos.atenderCliente(mensaje);
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
