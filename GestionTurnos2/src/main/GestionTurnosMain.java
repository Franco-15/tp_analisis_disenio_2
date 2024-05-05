package main;

import comunicacion.ServidorAtencionClientes;
import comunicacion.ServidorColaEspera;
import comunicacion.ServidorMonitorGestionTurnos;
import comunicacion.ServidorMonitoreo;
import modelo.turnos.Turnos;

public class GestionTurnosMain {
	public static void main(String[] args) {
		Turnos turnos = Turnos.getInstance();

		ServidorColaEspera servidorColaEspera = ServidorColaEspera.getInstance();
		ServidorAtencionClientes servidorAtencionClientes = ServidorAtencionClientes.getInstance();
		ServidorMonitoreo servidorMonitoreo = ServidorMonitoreo.getInstance();
		ServidorMonitorGestionTurnos servidorMonitorGestionTurnos = ServidorMonitorGestionTurnos.getInstance();
		
		Thread t1 = new Thread(servidorColaEspera);
		Thread t2 = new Thread(servidorAtencionClientes);
		Thread t3 = new Thread(servidorMonitoreo);
		Thread t4 = new Thread(servidorMonitorGestionTurnos);
		
//		t1.start();
//		t2.start();
//		t3.start();
		t4.start();
		
	}
}