package colaEspera;

import turnos.Turnos;

public class GestionTurnosMain {
	public static void main(String[] args) {
		RecepcionRegistro.getInstance().recepcionarRegistro();
		Turnos turnos = Turnos.getInstance();
	}
}
