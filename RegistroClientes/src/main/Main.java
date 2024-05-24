package main;

import controlador.Controlador;
import registro.Registro;
import vistas.VistaRegistro;

public class Main {

	public static void main(String[] args) {
		VistaRegistro vistaRegistro = new VistaRegistro();
		Registro registro = new Registro();

		Controlador controlador = new Controlador();
		controlador.setRegistro(registro);
		controlador.setVistaRegistro(vistaRegistro);
		controlador.run();
	}
}
