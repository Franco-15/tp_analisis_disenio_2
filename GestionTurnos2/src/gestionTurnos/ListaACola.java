package gestionTurnos;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import comunes.Cliente;
import comunes.TElementoColaEspera;

public class ListaACola {
	
	public Queue<TElementoColaEspera> obtenerColaClientes(List<Cliente> clientes){
		Queue<TElementoColaEspera> nuevaColaEspera = new LinkedList<>();
		
		for(Cliente cliente: clientes)
			nuevaColaEspera.add(new TElementoColaEspera(cliente));
		
		return nuevaColaEspera;
	}
}
