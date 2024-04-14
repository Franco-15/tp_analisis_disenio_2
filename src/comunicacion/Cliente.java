package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
	public static void main(String[] args) {
		try {
			// Paso 1: Conectar al servidor en el puerto 12345
			Socket socket = new Socket("localhost", 12345);

			// Paso 2: Crear flujos de entrada y salida para comunicarse con el servidor
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

			// Paso 3: Leer mensajes del usuario y enviar al servidor
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
			String mensaje;
			while ((mensaje = userInput.readLine()) != null) {
				output.println(mensaje);
				System.out.println("Servidor dice: " + input.readLine());
			}

			// Paso 4: Cerrar conexiones
			userInput.close();
			input.close();
			output.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}