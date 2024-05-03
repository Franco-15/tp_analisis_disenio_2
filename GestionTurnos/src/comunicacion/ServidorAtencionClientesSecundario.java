package comunicacion;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ServidorAtencionClientesSecundario implements Runnable {

    private ServidorAtencionClientes primaryServer;
    private ServidorAtencionClientes secondaryServer;

    public ServidorAtencionClientesSecundario(ServidorAtencionClientes primaryServer, ServidorAtencionClientes secondaryServer) {
        this.primaryServer = primaryServer;
        this.secondaryServer = secondaryServer;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.primaryServer.getPort());
            System.out.println("Servidor Secundario escuchando en puerto " + this.primaryServer.getPort());

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexión entrante detectada en el servidor secundario.");

                // Detener el servidor primario y reemplazarlo
                this.primaryServer.setActive(false);
                this.primaryServer = this.secondaryServer;
                this.secondaryServer = ServidorAtencionClientes.getInstance();

                // Iniciar un nuevo servidor secundario
                Thread secondaryThread = new Thread(this.secondaryServer);
                secondaryThread.start();

                // Responder al cliente
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
                output.println("Servidor primario detenido. Se ha activado el servidor secundario.");
                output.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}