package archivos;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import comunes.Cliente;

public class ArchivoJSONCliente implements IArchivoCliente{
	private static final String FILE_NAME = "clientes.json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void escribirClientes(List<Cliente> clientes) {
		try (Writer writer = new FileWriter(FILE_NAME)) {
			gson.toJson(clientes, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> leerClientes() {
		List<Cliente> clientes = new ArrayList<>();
		try (FileReader reader = new FileReader(FILE_NAME)) {
			// Parsea el archivo JSON a un JsonElement
			JsonElement jsonElement = JsonParser.parseReader(reader);
			// Convierte el JsonElement a un JsonObject
			JsonArray jsonArray = jsonElement.getAsJsonArray();
			
			for(JsonElement element : jsonArray) {
			   JsonObject jsonObject = element.getAsJsonObject();	
			   clientes.add(new Cliente(jsonObject.get("numero_documento").getAsString(),jsonObject.get("grupo_afinidad").getAsString(),jsonObject.get("fecha_nacimiento").getAsString() ));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println("No pudo leerse el archivo de configuracion");
		};
		return clientes;
	}

	@Override
	public void actualizarCliente(Cliente cliente) {
		List<Cliente> clientes = leerClientes();
		for (Cliente c : clientes) {
			if (c.getDni().equals(cliente.getDni())) {
				c.setGrupoAfinidad(cliente.getGrupoAfinidad());
				c.setFechaNacimiento(cliente.getFechaNacimiento());
				break;
			}
		}
		escribirClientes(clientes);
	}

	@Override
	public void eliminarCliente(String numeroDocumento) {
		List<Cliente> clientes = leerClientes();
		clientes = clientes.stream().filter(c -> !c.getDni().equals(numeroDocumento))
				.collect(Collectors.toList());
		escribirClientes(clientes);
	}
}
