package registro;

public class RegistroControlador {
	
	private Registro registro = new Registro();
	
	public RegistroControlador() {}
	
	public String registrar(String dni) {
		return registro.registrarCliente(dni);
	}
}
