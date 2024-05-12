package comunicacion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAtencionClientes {
	
	private static ServidorAtencionClientes instance = null;
	private GestionRecepcionServidor gestionMensajesRecibidos;
	
	
	public ServidorAtencionClientes() {
		this.gestionMensajesRecibidos = new GestionRecepcionServidor();
		   // Define los puertos en los que quieres que el servidor escuche
        int[] puertos = {6001, 6002, 6003,6004,6005,6006,6007,6008};

        // Iniciar un hilo por cada puerto para ejecutar ServidorRunnable
        for (int puerto : puertos) {
            Thread servidorThread = new Thread(new ServidorRunnable(puerto));
            servidorThread.start();
        }
	}
	
	public static ServidorAtencionClientes getInstance() {
		if (instance == null)
			instance = new ServidorAtencionClientes();

		return instance;
	}
	

    private static class ServidorRunnable implements Runnable {
        private int puerto;
        private ServerSocket serverSocket;

        public ServidorRunnable(int puerto) {
            this.puerto = puerto;
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

                        // Ejemplo de respuesta al cliente
                        //output.println("Mensaje recibido: " + mensajeCliente);
                        
                        
                        // Si necesitas lógica adicional aquí para procesar la información recibida del cliente

                        // Aquí podrías salir del bucle si se cumple alguna condición, como si el cliente envía un mensaje especial para cerrar la conexión
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