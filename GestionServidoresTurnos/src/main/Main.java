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
		ServidorDireccionamiento servidorDireccionamientoRegistro = new ServidorDireccionamiento(9);
		ServidorDireccionamiento servidorDireccionamientoAtenciónCliente = new ServidorDireccionamiento(10);
		ServidorDireccionamiento servidorDireccionamientoMonitoreo = new ServidorDireccionamiento(11);
		Monitor monitor = Monitor.getInstance();
		
		Thread t1 = new Thread(monitor);
		Thread t2 = new Thread(servidorDireccionamientoRegistro);
		Thread t3 = new Thread(servidorDireccionamientoAtenciónCliente);
		Thread t4 = new Thread(servidorDireccionamientoMonitoreo);
		Thread t5 = new Thread(sincronizacion);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}
