package main;

import notificacion.Servidor_notificacion;

public class Main {

	public static void main(String[] args) {
        Servidor_notificacion servidor = new Servidor_notificacion(4);
        servidor.iniciarServidor();
	}

}
