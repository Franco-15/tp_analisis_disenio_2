package monitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Monitor {
	private static Monitor instance = null; 
	private String serverHost;
	private int primaryServerPort;
	private int secondaryServerPort;
	private int checkPrimaryServerPort;
	private int checkSecondaryServerPort;
	private int activeServerPort;
	private int portToCheck;

	private Monitor() {
		this.serverHost = "localhost";
		this.primaryServerPort = 1;  //En el grafico aparece como 2
		this.checkPrimaryServerPort = 13;
		this.secondaryServerPort = 7;
		this.checkSecondaryServerPort = 17;
		this.activeServerPort = this.primaryServerPort;
		this.portToCheck = this.checkPrimaryServerPort;
	}
	
	public static Monitor getInstance() {
		if(instance == null)
			instance = new Monitor();
		
		return instance;
	}
	
	public void run() {
		try {

			while (true) {
				Thread.sleep(5000); // Espera 5 segundos antes de verificar nuevamente
				// Verificar el estado del servidor primario
				
				if(this.activeServerPort == -1) {
					this.activeServerPort = this.primaryServerPort;
					this.portToCheck = this.checkPrimaryServerPort;
				}
				System.out.println("Chequeando si el servidor " +this.serverHost +":"+this.activeServerPort + " se encuentra activo");
				
				boolean isServerActive = checkServer(serverHost, this.portToCheck);

				if (!isServerActive) {
					// Si el servidor primario estaba activo y ahora está inactivo,
					// activar el servidor secundario
					System.out.println("El servidor " + this.serverHost + ":" + this.activeServerPort + " está inactivo. Activando el auxiliar...");
					
					if(this.activeServerPort == this.primaryServerPort) {
						this.activeServerPort = this.secondaryServerPort;
						this.portToCheck = this.checkSecondaryServerPort;
					}
					else {
						this.activeServerPort = this.primaryServerPort;
						this.portToCheck = this.checkPrimaryServerPort;
					}
					if(!checkServer(serverHost, this.portToCheck)) {
						this.activeServerPort = -1;
						this.portToCheck = -1;	
					}
					
				}else
					System.out.println("El servidor " +this.serverHost +":"+this.activeServerPort + " se encuentra activo");

			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean checkServer(String serverHost, int serverPort) {
	    int retryCount = 0;
	    int maxRetries = 3;
	    boolean isConnected = false;
	    Socket socket = null;
	    
	    while (retryCount < maxRetries && !isConnected) {
	        try {
	        	socket = new Socket(serverHost, serverPort);
	        	BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

	            // Enviar un mensaje al servidor para verificar su estado
				output.println("check");
	            // Esperar la respuesta del servidor
	            String response = (String) input.readLine();

	            isConnected = "active".equals(response); // El servidor está activo si responde "active"
	            
	            socket.close();
	            output.close();
	            input.close();
	        } catch (IOException e) {
	            // Manejar la excepción de conexión
	            e.getMessage();
	        }
	        
	        if (!isConnected) {
	            retryCount++;
	            System.out.println("Reintentando conexión a servidor " + serverHost +":"+serverPort + " (" + retryCount + "/" + maxRetries + ")");
	            try {
	                Thread.sleep(1000); // Esperar antes de intentar nuevamente
	            } catch (InterruptedException ex) {
	                Thread.currentThread().interrupt();
	            }
	        }
	    }

	    return isConnected;
	}

	public synchronized int getActiveServerPort() {
		return this.activeServerPort;
	}
}
