package main;

import comunicacion.ServidorDireccionamiento;
import monitor.Monitor;
import sincronizacion.Sincronizacion;
import vistas.VistaLogs;

public class Main {

	public static void main(String[] args) {
		VistaLogs vista = VistaLogs.getInstance();
		vista.setVisible(true);
		Sincronizacion sincronizacion = Sincronizacion.getInstance();
		ServidorDireccionamiento servidorDireccionamiento = ServidorDireccionamiento.getInstance();
		Monitor monitor = Monitor.getInstance();
		
		Thread t1 = new Thread(monitor);
		Thread t2 = new Thread(servidorDireccionamiento);
		Thread t3 = new Thread(sincronizacion);
		
		t1.start();
		t2.start();
		t3.start();
	}
}
