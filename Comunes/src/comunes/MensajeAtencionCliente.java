package comunes;

import java.io.Serializable;

public class MensajeAtencionCliente implements Serializable {
    private static final long serialVersionUID = 1L;
	private String mensaje;
    private boolean llamado;
    private String dni;

    public MensajeAtencionCliente(String mensaje, boolean llamado, String dni) {
        this.mensaje = mensaje;
        this.llamado = llamado;
        this.dni = dni;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLlamado() {
        return llamado;
    }

    public void setLlamado(boolean llamado) {
        this.llamado = llamado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}

