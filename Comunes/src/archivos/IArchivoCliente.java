package archivos;

import java.util.List;
import comunes.Cliente;

public interface IArchivoCliente {
	  void escribirClientes(List<Cliente> clientes);
	  List<Cliente> leerClientes();
	  void actualizarCliente(Cliente cliente);
	  void eliminarCliente(String numeroDocumento);
}
