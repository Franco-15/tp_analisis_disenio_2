package main;

import comunicacion.ServidorNotificacion;

public class Main {

	public static void main(String[] args) {
        ServidorNotificacion servidor = new ServidorNotificacion(4);
        servidor.iniciarServidor();
	}

}
