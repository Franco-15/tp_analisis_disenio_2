package main;

import comunicacion.ServidorAtencionClientes;
import comunicacion.ServidorColaEspera;
import comunicacion.ServidorMonitorGestionTurnos;
import comunicacion.ServidorMonitoreo;
import comunicacion.ServidorSincronizacion;
import modelo.turnos.Turnos;

public class GestionTurnosMain {
	public static void main(String[] args) {
		Turnos turnos = Turnos.getInstance();
		
		int[] puertos = {6001, 6002, 6003, 6004, 6005, 6006, 6007, 6008};
		ServidorAtencionClientes servidorAtencionClientes = new ServidorAtencionClientes(puertos);
		
		
		ServidorColaEspera servidorColaEspera = ServidorColaEspera.getInstance();
		//ServidorAtencionClientes servidorAtencionClientes = ServidorAtencionClientes.getInstance();
		ServidorMonitoreo servidorMonitoreo = ServidorMonitoreo.getInstance();
		ServidorMonitorGestionTurnos servidorMonitorGestionTurnos = ServidorMonitorGestionTurnos.getInstance();
		ServidorSincronizacion servidorSincronizacion = ServidorSincronizacion.getInstance();
		
		Thread t1 = new Thread(servidorColaEspera);
		//Thread t2 = new Thread((Runnable) servidorAtencionClientes);
		Thread t3 = new Thread(servidorMonitoreo);
		Thread t4 = new Thread(servidorMonitorGestionTurnos);
		Thread t5 = new Thread(servidorSincronizacion);
		
		t1.start();
		//t2.start();
		t3.start();
		t4.start();
		t5.start();
	
	}
}
