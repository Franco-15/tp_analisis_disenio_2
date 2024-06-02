package archivos;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ArchivoJSONCliente implements IArchivoCliente{
	private static final String FILE_NAME = "clientes.json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void escribirClientes(List<ClienteArchivo> clientes) {
		try (Writer writer = new FileWriter(FILE_NAME)) {
			gson.toJson(clientes, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ClienteArchivo> leerClientes() {
		try (Reader reader = new FileReader(FILE_NAME)) {
			ClienteArchivo[] clientes = gson.fromJson(reader, ClienteArchivo[].class);
			return Arrays.asList(clientes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public void actualizarCliente(ClienteArchivo cliente) {
		List<ClienteArchivo> clientes = leerClientes();
		for (ClienteArchivo c : clientes) {
			if (c.getNumeroDocumento().equals(cliente.getNumeroDocumento())) {
				c.setGrupoAfinidad(cliente.getGrupoAfinidad());
				c.setFechaNacimiento(cliente.getFechaNacimiento());
				break;
			}
		}
		escribirClientes(clientes);
	}

	@Override
	public void eliminarCliente(String numeroDocumento) {
		List<ClienteArchivo> clientes = leerClientes();
		clientes = clientes.stream().filter(c -> !c.getNumeroDocumento().equals(numeroDocumento))
				.collect(Collectors.toList());
		escribirClientes(clientes);
	}
}
