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

public class ArchivoJSONLogs implements IArchivoLogs {

	private static final String FILE_NAME = "logs.json";
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void escribirLogs(List<Log> logs) {
		try (Writer writer = new FileWriter(FILE_NAME)) {
			gson.toJson(logs, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Log> leerLogs() {
		try (Reader reader = new FileReader(FILE_NAME)) {
			Log[] logs = gson.fromJson(reader, Log[].class);
			return Arrays.asList(logs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public void actualizarLog(Log log) {
		List<Log> logs = leerLogs();
		for (Log l : logs) {
			if (l.getNumeroDocumento().equals(log.getNumeroDocumento()) && l.getEvento().equals(log.getEvento())) {
				l.setFechaEvento(log.getFechaEvento());
				break;
			}
		}
		escribirLogs(logs);
	}

	@Override
	public void eliminarLog(String numeroDocumento, String evento) {
		List<Log> logs = leerLogs();
		logs = logs.stream()
				.filter(l -> !(l.getNumeroDocumento().equals(numeroDocumento) && l.getEvento().equals(evento)))
				.collect(Collectors.toList());
		escribirLogs(logs);
	}

}
