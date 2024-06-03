package archivos;

import java.util.ArrayList;
import java.util.List;

public class Logs {
	private static Logs instance = null;
    private List<Log> listaLogs;

    private Logs() {
        this.listaLogs = new ArrayList<>();
    }

    public static Logs getInstance() {
    	if(instance == null)
    		instance = new Logs();
    	
    	return instance;
    }
    
    public void agregarLog(Log log) {
        this.listaLogs.add(log);
    }

    public void eliminarLog(int indice) {
        if (indice >= 0 && indice < this.listaLogs.size()) {
            this.listaLogs.remove(indice);
            System.out.println("Log eliminado correctamente.");
        } else {
            System.out.println("Índice fuera de rango. No se pudo eliminar el log.");
        }
    }
    
    public List<Log> obtenerLista(){
    	return this.listaLogs;
    }
}
