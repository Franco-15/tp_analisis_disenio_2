package comunicacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import comunes.MensajeAtencionCliente;




public class ServidorAtencionClientes {
    
	
	 public ServidorAtencionClientes (int[] puertos) {
	        // Iniciar un hilo por cada puerto para ejecutar ServidorRunnable
	        for (int puerto : puertos) {
	            Thread servidorThread = new Thread(new ServidorRunnable(puerto));
	            servidorThread.start();
	        }
	    }


    private static class ServidorRunnable implements Runnable {
        private int puerto;
        private ServerSocket serverSocket;
    	private GestionRecepcionServidor gestionMensajesRecibidos;

        public ServidorRunnable(int puerto) {
            this.puerto = puerto;
            this.gestionMensajesRecibidos = new GestionRecepcionServidor();
        }
        
     public String cargar_mensaje() {
		
    	 //buscar en la cola de espera y devolver
    	 return null;
     }
     
     
        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(puerto);
                System.out.println("Servidor en el puerto " + puerto + " iniciado...");

                while (true) {
                    // Aceptar la conexión entrante en el puerto actual
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Cliente conectado al puerto " + puerto + " desde " + clientSocket.getInetAddress().getHostAddress());

                 // Crear ObjectOutputStream para enviar objetos
                    ObjectOutputStream objectOutput = new ObjectOutputStream(clientSocket.getOutputStream());

                    // Crear ObjectInputStream para recibir objetos
                    ObjectInputStream objectInput = new ObjectInputStream(clientSocket.getInputStream());
                    

                    // Mantener el servidor corriendo para intercambiar información
                    while (true) {
                        // Leer mensaje del cliente
                        //String mensajeCliente = input.readLine();
                    	MensajeAtencionCliente mensaje = (MensajeAtencionCliente) objectInput.readObject();

                    	System.out.println("Mensaje recibido del cliente en el puerto " + puerto + ": " + mensaje.getMensaje());
                        

                        
                        if(mensaje.getMensaje().equals("no llego")) {
                        	System.out.println("el mensaje del objeto que volvio es :" + mensaje.getMensaje());
                        	//
                        }
                        else
                        if(mensaje.getMensaje().equals( "dame cliente")) {
                        	//devolver el primer cliente en la cola
                        	MensajeAtencionCliente respuesta = new MensajeAtencionCliente("", false, "");
                        	String puertoString = String.valueOf(this.puerto);
                        	String result = this.gestionMensajesRecibidos.atenderCliente(puertoString);
                        	respuesta.setDni(result);
                        	objectOutput.writeObject(respuesta);
                        }
                        else
                        if(mensaje.getMensaje().equals("cliente aceptado")) {
                        	System.out.println("el mensaje del objeto que volvio es :" + mensaje.getMensaje());
                        	//
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}