package comunes;

import java.io.Serializable;

import javax.swing.JOptionPane;

public class Respuestas implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static TRespuesta RegistroExitoso(){
		int estado = JOptionPane.INFORMATION_MESSAGE;
		String mensaje = "El registro fue exitoso. Espere en la sala de espera para ser atendido";
		String titulo = "Registro Exitoso";
		return new TRespuesta(estado, mensaje, titulo);
	}
	
	public static TRespuesta DocumentoYaRegistrado(){
		int estado = JOptionPane.WARNING_MESSAGE;
		String mensaje = "El documento ya se encuentra registrado en el sistema";
		String titulo =  "Operacion Inválida";
		return new TRespuesta(estado, mensaje, titulo);
	}
	
	public static TRespuesta ErrorDeSistema(){
		int estado = JOptionPane.ERROR_MESSAGE;
		String mensaje = "Error del sistema. Intente nuevamente";
		String titulo =  "Error de sistema";
		return new TRespuesta(estado, mensaje, titulo);
	}
}
