package comunes;

import java.io.Serializable;

public class TElementoNotificacion implements Serializable{
	private static final long serialVersionUID = 1L;
	private String dni;
    private int box;
	
    
    public TElementoNotificacion(String dni, int box) {
		this.dni = dni;
		this.box = box;
	}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getBox() {
        return box;
    }

    public void setBox(int box) {
        this.box = box;
    }
    
}
