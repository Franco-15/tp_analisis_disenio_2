package main;

import controlador.Controlador;
import vistas.VistaConfirmacionRegistro;
import vistas.VistaRegistro;

public class Main {
	//VistaConfirmacionRegistro vistaConfirmacionRegistro =new VistaConfirmacionRegistro(); 
	
	public static void main(String[] args) {
		VistaRegistro vistaRegistro= new VistaRegistro();
		Controlador controlador= new Controlador(); 
		controlador.run();
	
	} 
	
	
	
	
}
