package monitoreo;

import java.util.Map;

import comunicacion.Comunicacion;


public class Monitoreo {
	private Comunicacion cliente;
	
	public Monitoreo() {
		this.cliente = new Comunicacion(11, "localhost");
	}

	// Método para actualizar las métricas en la ventana
    public Map<String, Object>  actualizarMetricas() {
        
    	Map<String, Object> mapa = null;
		mapa = (Map<String, Object>) cliente.publicar();

        return mapa;
    }
}
