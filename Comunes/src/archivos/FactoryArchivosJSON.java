package archivos;

public class FactoryArchivosJSON implements IFactoryArchivo {

	@Override
	public IArchivoCliente crearArchivoClientes() {
		return new ArchivoJSONCliente();
	}

	@Override
	public IArchivoLogs crearArchivoLogs() {
		return new ArchivoJSONLogs();
	}

}
