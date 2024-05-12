package registro;

public class Cliente implements ICliente {
	private String dni;
	private boolean fue_llamado;
	
	
	public Cliente(String dni) {
		this.dni = dni;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}
	
	 
}
