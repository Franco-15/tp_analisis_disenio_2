package archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import comunes.Cliente;

public class ArchivoTextoCliente implements IArchivoCliente{
	private static final String FILE_NAME = "clientes.txt";

	@Override
	public void escribirClientes(List<Cliente> clientes) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
			writer.write("numero_documento,grupo_afinidad,fecha_nacimiento\n");
			for (Cliente cliente : clientes) {
				writer.write(cliente.getDni() + "," + cliente.getGrupoAfinidad() + ","
						+ cliente.getFechaNacimiento() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Cliente> leerClientes() {
		List<Cliente> clientes = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			reader.readLine(); // Skip header
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(",");
				if (fields.length == 3) {
					clientes.add(new Cliente(fields[0], fields[1], fields[2]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
