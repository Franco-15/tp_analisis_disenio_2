package comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import monitor.Monitor;

public class ServidorDireccionamiento{
	private static ServidorDireccionamiento instance = null;
	private int port;
	private Monitor monitor; // Nombre cambiable la verdad, no me convence
	ServerSocket serverSocket;

	private ServidorDireccionamiento(int port) {
		this.port = port;
		this.monitor = Monitor.getInstance();
		this.monitor.run();
	}
	
	public static ServidorDireccionamiento getInstace() {
		if(instance == null)
			instance = new ServidorDireccionamiento(10);
		
		return instance;
	}
	

	public void escuchar() {
		try {

			this.serverSocket = new ServerSocket(this.port);
			System.out.println("Servidor de Direccionamiento escuchando en puerto " + this.port);
			
	        while (true) {
	            Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());
	            Thread clientThread = new Thread(new ClientHandler(clientSocket, monitor));
	            clientThread.start();
	        }
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
