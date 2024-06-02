package gestionServidores;

import java.util.ArrayList;

public class Servidores {
	
	private ArrayList<Servidor> servidores;
	private Servidor servidorPrimario;
	
	public Servidores() {
		this.servidores = new ArrayList<Servidor>();
		this.servidorPrimario = null;
	}
	
	public void agregarServidor(Servidor servidor) {
		servidores.add(servidor);
	}
	
	public void eliminarServidor(Servidor servidor) {
		servidores.remove(servidor);
	}
	
	public ArrayList<Servidor> getServidores(){
		return servidores;
	}
	
	public ArrayList<Servidor> getServidoresActivos(){
		ArrayList<Servidor> servidoresActivos = new ArrayList<>();
		for (Servidor servidor : servidores) {
			if (servidor.getEstado() == EstadoServidor.Activo) {
				servidoresActivos.add(servidor);
			}
		}
		return servidoresActivos;
	}

	public ArrayList<Servidor> getServidoresInactivos(){
		ArrayList<Servidor> servidoresInactivos = new ArrayList<>();
		for (Servidor servidor : servidores) {
			if (servidor.getEstado() == EstadoServidor.Inactivo) {
				servidoresInactivos.add(servidor);
			}
		}
		return servidoresInactivos;
	}
	
	public Servidor getServidorPrimario() {
		return servidorPrimario;
	}
	
	public void setServidorPrimario(Servidor servidorPrimario) {
		this.servidorPrimario = servidorPrimario;
	}

}
