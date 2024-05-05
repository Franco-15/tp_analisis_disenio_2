package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import monitor.Monitor;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Monitor monitor;

    public ClientHandler(Socket clientSocket, Monitor monitor) {
        this.clientSocket = clientSocket;
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
			BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

			String mensaje = input.readLine();
			if (mensaje.equals("publicar")) {
				output.println(monitor.getActiveServerPort()); // Le envio a subsistema Registro donde debe	publicar al cliente 
			}

			input.close();
			output.close();
			clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

