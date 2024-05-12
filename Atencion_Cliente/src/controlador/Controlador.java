package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import comunes.MensajeAtencionCliente;
import modelo.atencion.Atencion;
import modelo.box.Box;
import vistas.LoginEmpleado;
import vistas.VentanaMainEmpleado;

public class Controlador implements ActionListener{
	private LoginEmpleado vistaLoginEmpleado;
	private VentanaMainEmpleado vistaPrincipalEmpleado;
	private Box boxAtencion;
	private Atencion atencion;
	private MensajeAtencionCliente Cliente_actual; 
	
	public Controlador() {
		this.vistaLoginEmpleado = new LoginEmpleado();
		this.vistaLoginEmpleado.setActionListener(this);
		this.vistaLoginEmpleado.setVisible(true);
		this.Cliente_actual = null;
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
				this.atencion = new Atencion(this.boxAtencion.getNumero());
		        
		        this.vistaLoginEmpleado.cerrarVentana();
			} else
				this.vistaLoginEmpleado.numeroBoxInvalido();
		}
		else if(e.getActionCommand().equals("LLAMAR CLIENTE")) {
			if (this.vistaPrincipalEmpleado.get_cliente().isEmpty()) {
			MensajeAtencionCliente proximoCliente = this.atencion.llamarCliente();
			this.Cliente_actual = proximoCliente;	
			this.vistaPrincipalEmpleado.agregarCliente(proximoCliente.getDni());
			}
		}
		else if(e.getActionCommand().equals("aceptar")) {
			if (!this.vistaPrincipalEmpleado.get_cliente().isEmpty()) {
				this.Cliente_actual.setMensaje("cliente aceptado");
				this.atencion.respuesta_cliente(this.Cliente_actual);
				this.vistaPrincipalEmpleado.aceptar_cliente();
			}
		}
		else if (e.getActionCommand().equals("no llego")) {
			if (!this.vistaPrincipalEmpleado.get_cliente().isEmpty()) {			
			 this.Cliente_actual.setMensaje("no llego");
			this.atencion.respuesta_cliente(this.Cliente_actual);
			this.vistaPrincipalEmpleado.cliente_no_llego();
		}
			}
	}

	
	public void run() {
		this.vistaLoginEmpleado.setVisible(true);
	}

}

