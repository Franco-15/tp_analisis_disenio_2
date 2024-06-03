package archivos;

import java.util.Queue;

import comunes.TElementoColaEspera;

public interface EstrategiaLlamada {
	Queue<TElementoColaEspera> ordenarClientes(Queue<TElementoColaEspera> colaEspera);  
}
