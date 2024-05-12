package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modelo.atencion.Atencion;
import modelo.box.Box;
import vistas.LoginEmpleado;
import vistas.VentanaMainEmpleado;

public class Controlador implements ActionListener{
	private LoginEmpleado vistaLoginEmpleado;
	private VentanaMainEmpleado vistaPrincipalEmpleado;
	private Box boxAtencion;
	private Atencion atencion;

	
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
				this.atencion = new Atencion(this.boxAtencion.getNumero());
		        
		        this.vistaLoginEmpleado.cerrarVentana();
			} else
				this.vistaLoginEmpleado.numeroBoxInvalido();
		}
		else if(e.getActionCommand().equals("LLAMAR CLIENTE")) {
			if (this.vistaPrincipalEmpleado.get_cliente().isEmpty()) {
			String proximoCliente = this.atencion.llamarCliente(this.boxAtencion);
			System.out.println("proximo cliente" + proximoCliente);
			if(proximoCliente.equals(""))
				this.vistaPrincipalEmpleado.errorServidor();
			else if(proximoCliente.equals("-1"))
				this.vistaPrincipalEmpleado.noHayClienteParaAtender();
			else
				this.vistaPrincipalEmpleado.agregarCliente(proximoCliente);
			}
		}
		else if(e.getActionCommand().equals("aceptar")) {
			if (!this.vistaPrincipalEmpleado.get_cliente().isEmpty()) {
				 System.out.println("acpetado cliente");
				String mensaje = "aceptado";
				this.atencion.respuesta_cliente(boxAtencion, mensaje);
				this.vistaPrincipalEmpleado.aceptar_cliente();
			}
		}
		else if (e.getActionCommand().equals("no llego")) {
			if (!this.vistaPrincipalEmpleado.get_cliente().isEmpty()) {
			String mensaje = "no llego";
			 System.out.println("no llego cliente");
			 
			this.atencion.respuesta_cliente(boxAtencion, mensaje);
			this.vistaPrincipalEmpleado.cliente_no_llego();
		}
			}
	}

	
	public void run() {
		this.vistaLoginEmpleado.setVisible(true);
	}

}

