package archivos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

import comunes.Cliente;
import comunes.TElementoColaEspera;

import java.util.*;

public class OrdenPorAfinidad implements EstrategiaLlamada {

	 @Override
	    public Queue<TElementoColaEspera> ordenarClientes(Queue<TElementoColaEspera> colaEspera) {
	        
	        // Crear una lista temporal para ordenar los clientes por rango etario
	        List<TElementoColaEspera> tempList = new ArrayList<>(colaEspera);

	        // Ordenar la lista temporal por rango etario (usando Comparator)
	        Collections.sort(tempList, (cliente1, cliente2) -> {
	        	String grupoEtarioCliente1 = cliente1.getCliente().getGrupoAfinidad();
	        	String grupoEtarioCliente2 = cliente2.getCliente().getGrupoAfinidad();
	        	return grupoEtarioCliente2.compareTo(grupoEtarioCliente1);
	        });

	        // Limpiar la cola original
	        colaEspera.clear();

	        // Agregar los elementos ordenados de vuelta a la cola
	        for (TElementoColaEspera elemento : tempList) {
	            colaEspera.offer(elemento); // Agregar cliente ordenado de vuelta a la cola
	        }
	        
	        return colaEspera;
	    }

}

