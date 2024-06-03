package comunes;

import java.io.Serializable;

public class Cliente implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dni;
	private String grupoAfinidad;
	private String fechaNacimiento;

	public Cliente(String dni,String grupoAfinidad,String fechaNacimiento) {
		this.dni = dni;
		this.grupoAfinidad = grupoAfinidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getGrupoAfinidad() {
		return this.grupoAfinidad;
	}

	public void setGrupoAfinidad(String grupoAfinidad) {
		this.grupoAfinidad =  grupoAfinidad;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
