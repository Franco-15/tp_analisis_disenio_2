package archivos;

import java.util.List;

public interface IArchivoCliente {
	  void escribirClientes(List<ClienteArchivo> clientes);
	  List<ClienteArchivo> leerClientes();
	  void actualizarCliente(ClienteArchivo cliente);
	  void eliminarCliente(String numeroDocumento);
}
