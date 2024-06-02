package archivos;

public class FactoryArchivosXML implements IFactoryArchivo{
	@Override
	public IArchivoCliente crearArchivoClientes() {
		return new ArchivoXMLCliente();
	}

	@Override
	public IArchivoLogs crearArchivoLogs() {
		return new ArchivoXMLLogs();
	}
}
