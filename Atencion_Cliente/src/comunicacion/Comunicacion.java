package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Comunicacion {
	private String ip;
	private int port;

	public Comunicacion(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public String obtenerCliente(String mensaje) {
		String response = "";

		try {
			Socket socket = new Socket(this.ip, this.port);

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

			if (mensaje != null) {
				output.println(mensaje);
				response = input.readLine();
			}

			input.close();
			output.close();
			socket.close();

			return response;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return response;
	}
}
