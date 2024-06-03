package main;

import comunicacion.ServidorAtencionClientes;
import comunicacion.ServidorColaEspera;
import comunicacion.ServidorMonitorGestionTurnos;
import comunicacion.ServidorMonitoreo;
import comunicacion.ServidorSincronizacion;
import gestionTurnos.Turnos;
import vistas.VistaLogs;

public class GestionServidores {
	public void iniciarServidores() {
		Turnos turnos = Turnos.getInstance();
		VistaLogs vista = VistaLogs.getInstance();
		ServidorColaEspera servidorColaEspera = ServidorColaEspera.getInstance();
		ServidorAtencionClientes servidorAtencionClientes = ServidorAtencionClientes.getInstance();
		ServidorMonitoreo servidorMonitoreo = ServidorMonitoreo.getInstance();
		ServidorMonitorGestionTurnos servidorMonitorGestionTurnos = ServidorMonitorGestionTurnos.getInstance();
		ServidorSincronizacion servidorSincronizacion = ServidorSincronizacion.getInstance();
		
		vista.setVisible(true);
		
		Thread t1 = new Thread(servidorColaEspera);
		Thread t2 = new Thread(servidorAtencionClientes);
		Thread t3 = new Thread(servidorMonitoreo);
		Thread t4 = new Thread(servidorMonitorGestionTurnos);
		Thread t5 = new Thread(servidorSincronizacion);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
	}
}
