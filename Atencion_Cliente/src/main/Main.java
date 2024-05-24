package main;

import atencion.Atencion;
import controlador.Controlador;
import vistas.VistaLoginEmpleado;

public class Main {

	public static void main(String[] args) {
		Atencion atencion = new Atencion();
		VistaLoginEmpleado vistaLoginEmpleado = new VistaLoginEmpleado();
		
		Controlador controlador = new Controlador();
		controlador.setVistaLoginEmpleado(vistaLoginEmpleado);
		controlador.setAtencion(atencion);
		controlador.run();
	}

}
