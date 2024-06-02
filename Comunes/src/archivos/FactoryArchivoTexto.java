package archivos;

public class FactoryArchivoTexto implements IFactoryArchivo {

	@Override
	public IArchivoCliente crearArchivoClientes() {
		return new ArchivoTextoCliente();
	}

	@Override
	public IArchivoLogs crearArchivoLogs() {
		return new ArchivoTextoLogs();
	}

}
