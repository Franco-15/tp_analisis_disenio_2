package gestionServidores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

public class Monitor implements Runnable {

	private Servidores servidores;

	public Monitor(Servidores servidores) {
		this.servidores = servidores;
	}

	@Override
	public void run() {

		while (true) {
			System.out.println("Monitoreando Servidores...");
			for (Servidor servidor : this.servidores.getServidores()) {
				if (isServidorActivo(servidor)) {
					System.out.println("Servidor Activo: " + servidor.getNombre());
					servidor.setEstado(EstadoServidor.Activo);
					servidor.setUltimaVezActivo(LocalDateTime.now());
				} else {
					System.out.println("Servidor Inactivo: " + servidor.getNombre());
					servidor.setEstado(EstadoServidor.Inactivo);
				}
			}
			
			if(servidores.getServidoresActivos().size()>0)
				setServidorPrimario();
			else
				System.out.println("No hay servidores activos");

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}

	}

	private void setServidorPrimario() {
		if (servidores.getServidorPrimario() == null
				|| servidores.getServidorPrimario().getEstado() == EstadoServidor.Inactivo) {
			servidores.setServidorPrimario(servidores.getServidoresActivos().get(0));
			System.out.println("Servidor Primario seteado: " + servidores.getServidorPrimario().getNombre());
		}
	}

	private boolean isServidorActivo(Servidor servidor) {
		int retryCount = 0;
		int maxRetries = 3;
		boolean isConnected = false;
		Socket socket = null;

		while (retryCount < maxRetries && !isConnected) {
			try {
				socket = new Socket(servidor.getDireccionIP(), servidor.getPuertoPing());
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

				// Enviar un mensaje al servidor para verificar su estado
				output.println("check");
				// Esperar la respuesta del servidor
				String response = (String) input.readLine();

				isConnected = "active".equals(response); // El servidor está activo si responde "active"

				socket.close();
				output.close();
				input.close();
			} catch (IOException e) {
				// Manejar la excepción de conexión
				e.getMessage();
			}

			if (!isConnected) {
				retryCount++;
//				vista.agregarElemento("Reintentando conexión a servidor " + servidor.getDireccionIP() + ":" + serverPort
//						+ " (" + retryCount + "/" + maxRetries + ")");
				try {
					Thread.sleep(1000); // Esperar antes de intentar nuevamente
				} catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
		}

		return isConnected;
	}

}
