package archivos;

import java.util.List;

public interface IArchivoLogs {
    void escribirLogs(List<Log> logs);
    List<Log> leerLogs();
    void actualizarLog(Log log);
    void eliminarLog(String numeroDocumento, String evento);
}
