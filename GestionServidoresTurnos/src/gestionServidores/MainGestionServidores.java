package gestionServidores;

public class MainGestionServidores {
	public static void main(String[] args) {
		
		Servidores servidores = new Servidores();
		
		servidores.agregarServidor(new Servidor("Gestion_Turnos_1", "localhost", 1, 3, 2, 16, 15));
		servidores.agregarServidor(new Servidor("Gestion_Turnos_2", "localhost", 7, 5, 8, 18, 13));
		
		Monitor monitor = new Monitor(servidores);
		ServidorDireccionamiento servidorDireccionamientoRegistro = new ServidorDireccionamiento(9, servidores);
		ServidorDireccionamiento servidorDireccionamientoAtenciónCliente = new ServidorDireccionamiento(10, servidores);
		ServidorDireccionamiento servidorDireccionamientoMonitoreo = new ServidorDireccionamiento(11, servidores);
		Sincronizacion sincronizacion = new Sincronizacion(servidores);
		
		Thread t1 = new Thread(monitor);
		Thread t2 = new Thread(sincronizacion);
		Thread t3 = new Thread(servidorDireccionamientoRegistro);
		Thread t4 = new Thread(servidorDireccionamientoAtenciónCliente);
		Thread t5 = new Thread(servidorDireccionamientoMonitoreo);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
	}
}
