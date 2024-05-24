package comunes;

import java.io.Serializable;

public class Direccionamiento implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int puerto;
	private String ip;
	
	public Direccionamiento() {}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	
}
