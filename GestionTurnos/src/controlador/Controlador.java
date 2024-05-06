package controlador;

import comunicacion.ServidorAtencionClientes;
import comunicacion.ServidorColaEspera;
import comunicacion.ServidorMonitoreo;
import turnos.Turnos;
import vista.VistaLogs;

public class Controlador {
	
	
	public void run() {
		Turnos turnos = Turnos.getInstance();
		VistaLogs vista = VistaLogs.getInstance();
		vista.setVisible(true);

		ServidorColaEspera servidorColaEspera = ServidorColaEspera.getInstance();
		ServidorAtencionClientes servidorAtencionClientes = ServidorAtencionClientes.getInstance();
		ServidorMonitoreo servidorMonitoreo = ServidorMonitoreo.getInstance();
		
		Thread t1 = new Thread(servidorColaEspera);
		Thread t2 = new Thread(servidorAtencionClientes);
		Thread t3 = new Thread(servidorMonitoreo);
		
		t1.start();
		t2.start();
		t3.start();
	}
}
