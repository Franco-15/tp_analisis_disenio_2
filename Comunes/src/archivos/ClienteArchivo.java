package archivos;

public class ClienteArchivo {

	private String numeroDocumento;
	private String grupoAfinidad;
	private String fechaNacimiento;

	public ClienteArchivo(String numeroDocumento, String grupoAfinidad, String fechaNacimiento) {
		this.numeroDocumento = numeroDocumento;
		this.grupoAfinidad = grupoAfinidad;
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getGrupoAfinidad() {
		return grupoAfinidad;
	}

	public void setGrupoAfinidad(String grupoAfinidad) {
		this.grupoAfinidad = grupoAfinidad;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
}
