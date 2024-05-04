package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PublicacionRegistro {
    private static PublicacionRegistro instance = null;
    private String ipMonitor;
    private int portMonitor;

    private PublicacionRegistro(String ipMonitor, int portMonitor) {
        this.ipMonitor = ipMonitor;
        this.portMonitor = portMonitor;
    }

    public static PublicacionRegistro getInstance() {
        if (instance == null)
            instance = new PublicacionRegistro("localhost", 9000); // Definir el puerto del servidor monitor

        return instance;
    }

    public String publicar(String mensaje) {
        String response = "";

        try {
            // Conectar al servidor monitor
            Socket socketMonitor = new Socket(this.ipMonitor, this.portMonitor);
            BufferedReader inputMonitor = new BufferedReader(new InputStreamReader(socketMonitor.getInputStream()));
            PrintWriter outputMonitor = new PrintWriter(socketMonitor.getOutputStream(), true);

            // Enviar solicitud de publicación
            outputMonitor.println("publicar");

            // Recibir el número de puerto del servidor destino
            int portDestino = Integer.parseInt(inputMonitor.readLine());
            
            // Cerrar conexión con el servidor monitor
            inputMonitor.close();
            outputMonitor.close();
            socketMonitor.close();

            // Conectar al servidor destino para publicar el mensaje
            Socket socketDestino = new Socket("localhost", portDestino);
            BufferedReader inputDestino = new BufferedReader(new InputStreamReader(socketDestino.getInputStream()));
            PrintWriter outputDestino = new PrintWriter(socketDestino.getOutputStream(), true);

            // Publicar el mensaje
            outputDestino.println(mensaje);
            response = inputDestino.readLine();

            // Cerrar conexión con el servidor destino
            inputDestino.close();
            outputDestino.close();
            socketDestino.close();

            return response;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "2"; // Error al publicar
        }
    }
}
