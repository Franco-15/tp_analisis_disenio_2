package main;

import comunicacion.GestionRecepcionServidor;

public class GestionTurnosMain {
	public static void main(String[] args) {
		
		Configuracion configuracion = Configuracion.getInstance();
		configuracion.configurar("config.json");
		
		GestionServidores gestionServidores = new GestionServidores();
		gestionServidores.iniciarServidores();
		
		GestionRecepcionServidor gestionRecepcionServidor = new GestionRecepcionServidor();
		gestionRecepcionServidor.cargarClientesRegistrados();
	}
}