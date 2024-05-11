package comunes;

import java.io.Serializable;
import java.util.Map;

public class DatosSincronizacion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IColaEspera colaEspera;
	private Map<String, Object> metricas;
	
	public DatosSincronizacion() {}

	public IColaEspera getColaEspera() {
		return colaEspera;
	}

	public void setColaEspera(IColaEspera colaEspera) {
		this.colaEspera = colaEspera;
	}

	public Map<String, Object> getMetricas() {
		return metricas;
	}

	public void setMetricas(Map<String, Object> metricas) {
		this.metricas = metricas;
	}
}
