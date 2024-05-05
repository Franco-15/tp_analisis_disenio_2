package main;

import comunicacion.ServidorDireccionamiento;

public class Main {

	public static void main(String[] args) {
		ServidorDireccionamiento servidorDireccionamiento = ServidorDireccionamiento.getInstace();
		
		servidorDireccionamiento.escuchar();

	}

}
