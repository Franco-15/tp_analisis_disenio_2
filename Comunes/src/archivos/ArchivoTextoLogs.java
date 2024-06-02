package archivos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArchivoTextoLogs implements IArchivoLogs{

	private static final String FILE_NAME = "logs.txt";

    @Override
    public void escribirLogs(List<Log> logs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            writer.write("numero_documento,evento,fecha_evento\n");
            for (Log log : logs) {
                writer.write(log.getNumeroDocumento() + "," + log.getEvento() + "," + log.getFechaEvento() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Log> leerLogs() {
        List<Log> logs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 3) {
                    logs.add(new Log(fields[0], fields[1], fields[2]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
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
