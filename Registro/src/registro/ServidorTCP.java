package registro;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class ServidorTCP {
	public static void main(String[] args) {
		try {
			while (true) {
				// Paso 1: Crear un ServerSocket para escuchar conexiones entrantes en el puerto
				// 12345
				ServerSocket serverSocket = new ServerSocket(12345);
				System.out.println("Servidor iniciado. Esperando conexiones...");

				// Paso 2: Esperar a que llegue una conexión de un cliente
				Socket clientSocket = serverSocket.accept();
				System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

				// Paso 3: Crear flujos de entrada y salida para comunicarse con el cliente
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

				// Paso 4: Leer mensajes del cliente y responder
				String mensaje;
				while ((mensaje = input.readLine()) != null) {
					System.out.println("Cliente dice: " + mensaje);
					output.println(0);
				}
				
				System.out.println("Cliente desconectado desde " + clientSocket.getInetAddress().getHostName());

				// Paso 5: Cerrar conexiones
				input.close();
				output.close();
				clientSocket.close();
				serverSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}