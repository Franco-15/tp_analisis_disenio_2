package comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Servidor {
	public static void main(String[] args) {
		try {
			// Paso 1: Crear un ServerSocket para escuchar conexiones entrantes en el puerto
			// 12345
			ServerSocket serverSocket = new ServerSocket(12345);
			ServerSocket serverSocket2 = new ServerSocket(12346);
			System.out.println("Servidor iniciado. Esperando conexiones...");

			// Paso 2: Esperar a que llegue una conexión de un cliente
			Socket clientSocket = serverSocket.accept();
			Socket clientSocket2 = serverSocket2.accept();
			
			System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());
			System.out.println("Cliente2 conectado desde " + clientSocket2.getInetAddress().getHostName());

			
			// Paso 3: Crear flujos de entrada y salida para comunicarse con el cliente
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
			
			// Paso 3: Crear flujos de entrada y salida para comunicarse con el cliente
			BufferedReader input2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
			PrintWriter output2 = new PrintWriter(clientSocket2.getOutputStream(), true);

			// Paso 4: Leer mensajes del cliente y responder
			String mensaje;
			while ((mensaje = input.readLine()) != null) {
				System.out.println("Cliente dice: " + mensaje);
				output.println("Respuesta del servidor: " + mensaje);
			}
			
			// Paso 4: Leer mensajes del cliente y responder
			String mensaje2;
			while ((mensaje2 = input2.readLine()) != null) {
				System.out.println("Cliente dice: " + mensaje2);
				output2.println("Respuesta del servidor: " + mensaje2);
			}

			// Paso 5: Cerrar conexiones
			input.close();
			output.close();
			clientSocket.close();
			serverSocket.close();
			
			// Paso 5: Cerrar conexiones
			input2.close();
			output2.close();
			clientSocket2.close();
			serverSocket2.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
