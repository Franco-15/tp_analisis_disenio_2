package gestionServidores;

import java.time.LocalDateTime;

public class Servidor {
	// Atributos
	private String nombre;
	private String direccionIP;
	private int puertoRegistro;
	private int puertoMonitoreo;
	private int puertoAtencionCliente;
	private int puertoPing;
	private int puertoSincronizacion;
	private EstadoServidor estado; // true para encendido, false para apagado
	private LocalDateTime ultimaVezActivo = null;
	
	// Constructor
	public Servidor(String nombre, String direccionIP, int puertoRegistro, int puertoMonitoreo, int puertoAtencionCliente, int puertoPing, int puertoSincronizacion) {
		this.nombre = nombre;
		this.direccionIP = direccionIP;
		this.puertoRegistro = puertoRegistro;
		this.puertoMonitoreo = puertoMonitoreo;
		this.puertoAtencionCliente= puertoAtencionCliente;
		this.puertoPing = puertoPing;
		this.puertoSincronizacion = puertoSincronizacion;
	}

	public int getPuertoSincronizacion() {
		return puertoSincronizacion;
	}

	public void setPuertoSincronizacion(int puertoSincronizacion) {
		this.puertoSincronizacion = puertoSincronizacion;
	}

	public int getPuertoPing() {
		return puertoPing;
	}

	public void setPuertoPing(int puertoPing) {
		this.puertoPing = puertoPing;
	}

	public EstadoServidor getEstado() {
		return this.estado;
	}
	
	public void setEstado(EstadoServidor estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccionIP() {
		return direccionIP;
	}

	public void setDireccionIP(String direccionIP) {
		this.direccionIP = direccionIP;
	}

	public int getPuertoRegistro() {
		return puertoRegistro;
	}

	public void setPuertoRegistro(int puertoRegistro) {
		this.puertoRegistro = puertoRegistro;
	}

	public int getPuertoMonitoreo() {
		return puertoMonitoreo;
	}

	public void setPuertoMonitoreo(int puertoMonitoreo) {
		this.puertoMonitoreo = puertoMonitoreo;
	}

	public int getPuertoAtencionCliente() {
		return puertoAtencionCliente;
	}

	public void setPuertoAtencionCliente(int puertoAtencionCliente) {
		this.puertoAtencionCliente = puertoAtencionCliente;
	}

	public LocalDateTime getUltimaVezActivo() {
		return ultimaVezActivo;
	}

	public void setUltimaVezActivo(LocalDateTime ultimaVezActivo) {
		this.ultimaVezActivo = ultimaVezActivo;
	}
	
}

