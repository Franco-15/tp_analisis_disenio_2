package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import atencion.Atencion;
import comunes.Box;
import comunes.MensajeAtencionCliente;
import vistas.VistaLoginEmpleado;
import vistas.VistaPrincipalEmpleado;

public class Controlador implements ActionListener {
	private VistaLoginEmpleado vistaLoginEmpleado;
	private VistaPrincipalEmpleado vistaPrincipalEmpleado;
	private Box boxAtencion;
	private Atencion atencion;
	private MensajeAtencionCliente mensajeAtencion;

	public Controlador() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("INGRESAR")) {
			String numeroBox = this.vistaLoginEmpleado.getNumeroBox();
			if (numeroBox.matches("[0-9]+")) {
				this.vistaPrincipalEmpleado = new VistaPrincipalEmpleado();
				this.vistaPrincipalEmpleado.setActionListener(this);
				this.vistaPrincipalEmpleado.setNumeroBox(numeroBox);
				this.vistaPrincipalEmpleado.setVisible(true);
				this.boxAtencion = new Box(Integer.parseInt(numeroBox));
				this.vistaLoginEmpleado.cerrarVentana();
			} else
				this.vistaLoginEmpleado.numeroBoxInvalido();
		} 
		
		else if (e.getActionCommand().equals("LLAMAR CLIENTE")) {
			try {
				MensajeAtencionCliente proximoCliente = atencion.llamarCliente(this.boxAtencion);
				if (proximoCliente == null)
					this.vistaPrincipalEmpleado.errorServidor();
				else if (proximoCliente.getCliente() == null)
					this.vistaPrincipalEmpleado.noHayClienteParaAtender();
				else {
					this.mensajeAtencion = proximoCliente;
					this.vistaPrincipalEmpleado.agregarProximoCliente(proximoCliente.getCliente().getCliente());
				}
			} catch (IOException | ClassNotFoundException e1) {
				this.vistaPrincipalEmpleado.errorServidor();
			}
		}

		else if (e.getActionCommand().equals("ATENDER")) {
			try {
				if (this.mensajeAtencion != null) {
					this.vistaPrincipalEmpleado.agregarClienteAtendido(mensajeAtencion.getCliente().getCliente());
					this.mensajeAtencion.setEsAtendido(true);
					this.atencion.respuestaAtencion(mensajeAtencion);
					this.mensajeAtencion = null;
				} else
					this.vistaPrincipalEmpleado.errorServidor();
			} catch (IOException | ClassNotFoundException e1) {
				this.vistaPrincipalEmpleado.errorServidor();
			}
		}

		else if (e.getActionCommand().equals("NO RESPONDIO")) {
			try {
				if (this.mensajeAtencion != null) {
					this.vistaPrincipalEmpleado.agregarClienteNoAtendido(mensajeAtencion.getCliente().getCliente());
					this.mensajeAtencion.setEsAtendido(false);
					this.atencion.respuestaAtencion(mensajeAtencion);
					this.mensajeAtencion = null;
				} else
					this.vistaPrincipalEmpleado.errorServidor();
			} catch (IOException | ClassNotFoundException e1) {
				this.vistaPrincipalEmpleado.errorServidor();
			}
		}
	}

	public void setAtencion(Atencion atencion) {
		this.atencion = atencion;
	}

	public void setVistaLoginEmpleado(VistaLoginEmpleado vista) {
		this.vistaLoginEmpleado = vista;
	}

	public void run() {
		this.vistaLoginEmpleado.setActionListener(this);
		this.vistaLoginEmpleado.setVisible(true);
	}

}
