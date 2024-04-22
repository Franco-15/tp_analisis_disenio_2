package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import atencion.Atencion;
import box.Box;
import vistas.LoginEmpleado;
import vistas.VentanaMainEmpleado;

public class Controlador implements ActionListener{
	private LoginEmpleado vistaLoginEmpleado;
	private VentanaMainEmpleado vistaPrincipalEmpleado;
	private Box boxAtencion;
	
	public Controlador() {
		this.vistaLoginEmpleado = new LoginEmpleado();
		this.vistaLoginEmpleado.setActionListener(this);
		this.vistaLoginEmpleado.setVisible(true);
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("INGRESAR")) {
			String numeroBox = this.vistaLoginEmpleado.getNumeroBox();
			if (numeroBox.matches("[0-9]+")) {
				this.vistaPrincipalEmpleado = new VentanaMainEmpleado();
				this.vistaPrincipalEmpleado.setActionListener(this);
				this.vistaPrincipalEmpleado.setNumeroBox(numeroBox);
		        this.vistaPrincipalEmpleado.setVisible(true);
		        this.boxAtencion = new Box(Integer.parseInt(numeroBox));
		        this.vistaLoginEmpleado.cerrarVentana();
			} else
				this.vistaLoginEmpleado.numeroBoxInvalido();
		}
		else if(e.getActionCommand().equals("LLAMAR CLIENTE")) {
			Atencion atencion = new Atencion();
			String proximoCliente = atencion.llamarCliente(this.boxAtencion);
			if(proximoCliente.equals(""))
				this.vistaPrincipalEmpleado.errorServidor();
			else if(proximoCliente.equals("-1"))
				this.vistaPrincipalEmpleado.noHayClienteParaAtender();
			else
				this.vistaPrincipalEmpleado.agregarCliente(proximoCliente);
		}
		
	}
}
