package main;

import comunicacion.ServidorDireccionamiento;
import monitor.Monitor;

public class Main {

	public static void main(String[] args) {
		ServidorDireccionamiento servidorDireccionamiento = ServidorDireccionamiento.getInstance();
		Monitor monitor = Monitor.getInstance();
		
		Thread t1 = new Thread(monitor);
		Thread t2 = new Thread(servidorDireccionamiento);
		
		t1.start();
		t2.start();

	}

}
