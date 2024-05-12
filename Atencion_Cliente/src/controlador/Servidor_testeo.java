package controlador;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Random;



public class Servidor_testeo {
    public static void main(String[] args) {
        // Define los puertos en los que quieres que el servidor escuche
        int[] puertos = {6001, 6002, 6003,6004,6005,6006,6007,6008};

        // Iniciar un hilo por cada puerto para ejecutar ServidorRunnable
        for (int puerto : puertos) {
            Thread servidorThread = new Thread(new ServidorRunnable(puerto));
            servidorThread.start();
        }
    }
    
    public String devolver_dni() {
        // Generar un número aleatorio
        Random rand = new Random();
        int numeroAleatorio = rand.nextInt(1000000000) + 1000000000; // Genera un número entre 1000000000 y 1999999999

        // Convertir el número aleatorio a String
        String numeroAleatorioString = String.valueOf(numeroAleatorio);
        
        // Imprimir el número aleatorio como String
        System.out.println("Número aleatorio como String: " + numeroAleatorioString);
        
    	return null;
    }

    private static class ServidorRunnable implements Runnable {
        private int puerto;
        private ServerSocket serverSocket;
    

        public ServidorRunnable(int puerto) {
            this.puerto = puerto;
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

                    // Crear flujos de entrada y salida para comunicarse con el cliente
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Mantener el servidor corriendo para intercambiar información
                    while (true) {
                        // Leer mensaje del cliente
                        String mensajeCliente = input.readLine();
                        System.out.println("Mensaje recibido del cliente en el puerto " + puerto + ": " + mensajeCliente);
                        

                        
                        if(mensajeCliente.equals("no llego")) {
                        	//agregar a cola de espera o no eliminar
                        	 // Ejemplo de respuesta al cliente
                          	 System.out.println("Mensaje recibido: " + mensajeCliente);
                        }
                        else
                        if(mensajeCliente.equals( "dame cliente")) {
                        	//devolver el primer cliente en la cola
                        	String paciente = "123";
                        	output.println(paciente);
                        }
                        else
                        if(mensajeCliente.equals("cliente aceptado")) {
                        	// devolver confirmacion 
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
