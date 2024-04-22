package turnos;

import java.io.Serializable;

public class TElementoNotificacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String dni;
    private String box;
	
    
    public TElementoNotificacion(String dni, String box) {
		this.dni = dni;
		this.box = box;
	}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }
    
}
