package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import registro.Registro;
import registro.Response;
import vistas.VistaConfirmacionRegistro;
import vistas.VistaRegistro;

public class Controlador implements ActionListener, Observer {

	private Registro registro = new Registro();
	// private Object textField_numero_dni;
	// private Object dialog;
	private VistaRegistro vista;
	private VistaConfirmacionRegistro vistaConfirmacion;

	public Controlador() {
		this.vista = new VistaRegistro();
		this.vista.setActionListener(this);

	}

	public void actionPerformed(ActionEvent e) {
		String numeroDocumento = this.vista.getDni();

		if (e.getActionCommand().equals("CANCELAR")) {
			this.vistaConfirmacion.dispose();
		} 
		else if (e.getActionCommand().equals("REGISTRARSE")) {
			if (numeroDocumento.matches("[0-9]+")) {
				this.vistaConfirmacion = new VistaConfirmacionRegistro(this.vista, numeroDocumento); // LINEA DE PRUEBA
				this.vistaConfirmacion.setActionListener(this);
				this.vistaConfirmacion.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(this.vista, "El documento ingresado no es válido.", "Documento inválido",
						JOptionPane.ERROR_MESSAGE);
			}
			this.vista.borroPanel();
		} else if (e.getActionCommand().equals("CONFIRMAR")) {
			this.vistaConfirmacion.cerrarVista();
			String resultado = this.registro.registrarCliente(numeroDocumento);
			System.out.println(resultado);
			if(resultado.equals("0"))
                JOptionPane.showMessageDialog(this.vista, Response.RegistroExistoso, "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
        	else if(resultado.equals("1"))
                JOptionPane.showMessageDialog(this.vista, Response.DocumentoYaRegistro, "Operacion Inválida", JOptionPane.WARNING_MESSAGE);
        	else if(resultado.equals("2"))
                JOptionPane.showMessageDialog(this.vista, Response.ErrorDeSistema, "Error de Sistema", JOptionPane.ERROR_MESSAGE);
			
			
		}
	}

	public void run() {
		this.vista.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {  //Habria que implementar algo aca para alguna posible escucha del controlador al modelo,pero creo que no habria
		// TODO Auto-generated method stub

	}
}
