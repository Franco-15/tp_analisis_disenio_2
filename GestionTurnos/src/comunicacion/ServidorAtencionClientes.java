package comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorAtencionClientes implements Runnable {

    private static ServidorAtencionClientes instance = null;

    private int port;
    private GestionRecepcionServidor gestionMensajesRecibidos;
    private boolean active = true;

    private ServidorAtencionClientes(int port) {
        this.port = port;
        this.gestionMensajesRecibidos = new GestionRecepcionServidor();
    }

    public static ServidorAtencionClientes getInstance() {
        if (instance == null)
            instance = new ServidorAtencionClientes(2);

        return instance;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void run() {
        while (active) {
            try {
                ServerSocket serverSocket = new ServerSocket(this.getPort());
                System.out.println("Servidor Atencion Cliente escuchando en puerto " + this.getPort());

                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostName());

                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String mensaje;
                while ((mensaje = input.readLine()) != null) {
                    System.out.println("Numero de Box: " + mensaje);
                    String result = gestionMensajesRecibidos.atenderCliente(mensaje);
                    output.println(result);
                }

                input.close();
                output.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Servidor Atencion Cliente detenido.");
    }

    public static void main(String[] args) {
        // Crear servidor primario y secundario
        ServidorAtencionClientes primaryServer = ServidorAtencionClientes.getInstance();
        ServidorAtencionClientes secondaryServer = ServidorAtencionClientes.getInstance();

        // Iniciar ambos servidores
        Thread primaryThread = new Thread(primaryServer);
        Thread secondaryThread = new Thread(secondaryServer);

        primaryThread.start();
        secondaryThread.start();

        // Iniciar el servidor secundario para tomar el lugar del primario en caso de fallo
        ServidorAtencionClientesSecundario servidorSecundario = new ServidorAtencionClientesSecundario(primaryServer, secondaryServer);
        Thread secondaryReplacementThread = new Thread(servidorSecundario);
        secondaryReplacementThread.start();
    }

	public int getPort() {
		return port;
	}
}