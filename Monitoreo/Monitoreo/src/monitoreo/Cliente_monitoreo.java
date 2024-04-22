package monitoreo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Cliente_monitoreo {
    private static Socket socket;
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private int puerto;
    private String ip;
    
 
    
	public Cliente_monitoreo(int puerto, String ip) {
		this.puerto = puerto;
		this.ip = ip;
		
	}


 

    // Función para enviar el objeto HashMap al servidor y recibir una respuesta
    public  Object enviarYRecibir() throws IOException, ClassNotFoundException {
    	
    	// Paso 1: Conectar al servidor en el puerto 12345
        socket = new Socket(this.ip, this.puerto);
        // Paso 2: Crear flujos de entrada y salida para comunicarse con el servidor
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    	
        // Paso 3: Crear el objeto Map con las métricas
        Map<String, Object> metricasMap = new HashMap<>();
       
        
       //paso 4: mandar al server
        output.writeObject(metricasMap);
        
        // Paso 5: Esperar la respuesta del servidor
        System.out.println("Esperando respuesta del servidor ");
        Map<String, Object> respuesta = null;
        respuesta =  (Map<String, Object>)input.readObject();
        
        input.close();
        output.close();
        socket.close();
        
        return respuesta;
    }
    
}

