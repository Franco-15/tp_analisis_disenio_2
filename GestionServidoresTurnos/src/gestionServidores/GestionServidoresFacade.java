package gestionServidores;

import vistas.VistaLogs;

public class GestionServidoresFacade {

        private Monitor monitor;
        private Sincronizacion sincronizacion;
        private ServidorDireccionamiento servidorDireccionamientoRegistro;
        private ServidorDireccionamiento servidorDireccionamientoAtenciónCliente;
        private ServidorDireccionamiento servidorDireccionamientoMonitoreo;
        private VistaLogs vista;
        
        public GestionServidoresFacade(Servidores servidores) {
        	this.vista = VistaLogs.getInstance();
            this.monitor = new Monitor(servidores);
            this.sincronizacion = new Sincronizacion(servidores);
            this.servidorDireccionamientoRegistro = new ServidorDireccionamiento(9, servidores);
            this.servidorDireccionamientoAtenciónCliente = new ServidorDireccionamiento(10, servidores);
            this.servidorDireccionamientoMonitoreo = new ServidorDireccionamiento(11, servidores);
        }


        public void iniciarServidores() {
        	this.vista.setVisible(true);
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
