package comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import monitor.Monitor;
import vistas.VistaLogs;

public class ServidorDireccionamiento implements Runnable{
	private static ServidorDireccionamiento instance = null;
	private int port;
	private Monitor monitor; // Nombre cambiable la verdad, no me convence
	ServerSocket serverSocket;

	private ServidorDireccionamiento(int port) {
		this.port = port;
		this.monitor = Monitor.getInstance();
	}
	
	public static ServidorDireccionamiento getInstance() {
		if(instance == null)
			instance = new ServidorDireccionamiento(10);
		
		return instance;
	}

	public void run() {
		VistaLogs vista = VistaLogs.getInstance();
		try {

			this.serverSocket = new ServerSocket(this.port);
			vista.agregarElemento("Servidor de Direccionamiento escuchando en puerto " + this.port);
			
	        while (true) {
	            Socket clientSocket = serverSocket.accept();
	            vista.agregarElemento("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());
	            Thread clientThread = new Thread(new ClientHandler(clientSocket, monitor));
	            clientThread.start();
	        }
			
		} catch (IOException e) {
			vista.agregarElemento(e.getMessage());
		}
		
	}
}
