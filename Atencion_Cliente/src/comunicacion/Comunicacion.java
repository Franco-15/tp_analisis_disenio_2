package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import comunes.MensajeAtencionCliente;

public class Comunicacion {
	private String ip;
	private int port;
	private Socket socket = null;
	private ObjectInputStream objectInput;  
	private ObjectOutputStream objectoutput;
	
	public Comunicacion(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			if(this.socket == null)
			this.socket = new Socket(this.ip, this.port);
            System.out.println("Conexión establecida con el servidor.");
			 this.objectInput = new ObjectInputStream(socket.getInputStream());
	         this.objectoutput = new ObjectOutputStream(socket.getOutputStream());
	         
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	
	public MensajeAtencionCliente obtenerCliente() {
	     MensajeAtencionCliente respuesta = null; // Declaración fuera del bloque try

	        try {
	           MensajeAtencionCliente solicitud = new MensajeAtencionCliente("dame cliente", false, ""); 
	           this.objectoutput.writeObject(solicitud);
	           respuesta = (MensajeAtencionCliente) objectInput.readObject();
	            System.out.println("Respuesta del servidor: " + respuesta);
	            
	        } catch (IOException | ClassNotFoundException e) {
	            // Manejar IOException y ClassNotFoundException, por ejemplo, registrar el error
	            System.out.println(e.getMessage());
	        }

	        return respuesta;
	    }
	
	public void respuesta_cliente(MensajeAtencionCliente cliente){

        try {
			this.objectoutput.writeObject(cliente);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
