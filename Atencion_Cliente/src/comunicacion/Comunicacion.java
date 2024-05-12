package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Comunicacion {
	private String ip;
	private int port;
	private Socket socket = null;
	private BufferedReader input;
	private PrintWriter output;
	
	public Comunicacion(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			if(this.socket == null)
			this.socket = new Socket(this.ip, this.port);
            System.out.println("Conexión establecida con el servidor.");
			 this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
	         this.output = new PrintWriter(this.socket.getOutputStream(), true);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	
	public String obtenerCliente(String mensaje) {
	    String respuesta = ""; // Declaración fuera del bloque try

		try {

			BufferedReader input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			PrintWriter output = new PrintWriter(this.socket.getOutputStream(), true);

			  // Enviar solicitud de publicación
            output.println("dame cliente");

            respuesta = input.readLine();        
            System.out.println("Respuesta del servidor: " + respuesta);
            
            
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return respuesta;
	}
	
	public void respuesta_cliente(int box,String mensaje){
        this.output.println(mensaje);
        System.out.println("mensaje enviado: " + mensaje);

	}
}
