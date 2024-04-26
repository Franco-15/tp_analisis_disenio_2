package main;

import comunicacion.Servidor_notificacion;

public class Main {

	public static void main(String[] args) {
        Servidor_notificacion servidor = new Servidor_notificacion(4);
        servidor.iniciarServidor();
	}

}
