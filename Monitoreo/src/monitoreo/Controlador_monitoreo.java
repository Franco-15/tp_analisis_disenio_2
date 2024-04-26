package monitoreo;

import java.io.IOException;
import java.util.Map;

import comunicacion.Cliente_monitoreo;


public class Controlador_monitoreo {
	private Cliente_monitoreo cliente;
	
	
	
	public Controlador_monitoreo(int puerto,String ip) {
		this.cliente = new Cliente_monitoreo(puerto,ip);
		
	}



	// Método para actualizar las métricas en la ventana
    public Map<String, Object>  actualizarMetricas() {
        
    	
    	Map<String, Object> mapa = null;
		try {
			mapa = (Map<String, Object>) cliente.enviarYRecibir();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return mapa;
    }
}
