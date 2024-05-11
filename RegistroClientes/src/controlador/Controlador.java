package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import registro.Registro;
import vistas.VistaConfirmacionRegistro;
import vistas.VistaRegistro;

public class Controlador implements ActionListener {

	private Registro registro;
	private VistaRegistro vistaRegistro;
	private VistaConfirmacionRegistro vistaConfirmacion;

	public Controlador() {
	}

	public void actionPerformed(ActionEvent e) {
		String numeroDocumento = this.vistaRegistro.getDni();

		if (e.getActionCommand().equals("CANCELAR")) {
			this.vistaConfirmacion.cerrarVista();
		} else if (e.getActionCommand().equals("REGISTRARSE")) {
			if (numeroDocumento.matches("[0-9]+")) {
				this.vistaConfirmacion = new VistaConfirmacionRegistro(this.vistaRegistro, numeroDocumento); // LINEA DE
																												// PRUEBA
				this.vistaConfirmacion.setActionListener(this);
				this.vistaConfirmacion.setVisible(true);
			} else {
				this.vistaRegistro.dniNoValido();
			}
			this.vistaRegistro.borroPanel();
		} else if (e.getActionCommand().equals("CONFIRMAR")) {
			this.vistaConfirmacion.cerrarVista();
			String resultado = this.registro.registrarCliente(numeroDocumento);
			this.vistaRegistro.mostrarResultadoRegistro(resultado);
		}
	}

	public void run() {
		this.vistaRegistro = new VistaRegistro();
		this.vistaRegistro.setActionListener(this);
		this.vistaRegistro.setVisible(true);
		this.registro = new Registro();
	}
}
