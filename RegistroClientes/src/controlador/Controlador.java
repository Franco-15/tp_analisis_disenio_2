package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import comunes.TRespuesta;
import registro.Registro;
import vistas.VistaConfirmacionRegistro;
import vistas.VistaRegistro;

public class Controlador implements ActionListener {

	private Registro registro;
	private VistaRegistro vistaRegistro;
	private VistaConfirmacionRegistro vistaConfirmacion;

	public Controlador() {}

	public void actionPerformed(ActionEvent e) {
		String numeroDocumento = this.vistaRegistro.getDni();

		if (e.getActionCommand().equals("CANCELAR")) {
			this.vistaConfirmacion.cerrarVista();
		} else if (e.getActionCommand().equals("REGISTRARSE")) {
			if (numeroDocumento.matches("[0-9]+")) {
				this.vistaConfirmacion = new VistaConfirmacionRegistro(this.vistaRegistro, numeroDocumento);
				this.vistaConfirmacion.setActionListener(this);
				this.vistaConfirmacion.setVisible(true);
			} else {
				this.vistaRegistro.dniNoValido();
			}
			this.vistaRegistro.borroPanel();
		} else if (e.getActionCommand().equals("CONFIRMAR")) {
			this.vistaConfirmacion.cerrarVista();
			TRespuesta resultado = this.registro.registrarCliente(numeroDocumento);
			this.vistaRegistro.mostrarResultadoRegistro(resultado);
		}
	}
	
	public void setRegistro(Registro registro) {
		this.registro = registro;
	}
	
	public void setVistaRegistro(VistaRegistro vista) {
		this.vistaRegistro = vista;
	}
	
	public void run() {
		this.vistaRegistro = new VistaRegistro();
		this.vistaRegistro.setActionListener(this);
		this.vistaRegistro.setVisible(true);
	}
}
