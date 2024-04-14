package gestion_turnos;

import java.util.LinkedList;
import java.util.Queue;

public class ColaEspera implements IColaEspera {
	private static ColaEspera instance = null;
	
	private Queue<TElementoColaEspera> colaEspera = new LinkedList<>();
	
	private ColaEspera() {}
	
	public static ColaEspera getInstance() {
        if (instance == null) {
            instance = new ColaEspera();
        }
        return instance;
    }
	
	/**
	 * Este método agrega un elemento a la cola.
	 * Si la cola está llena, retorna false.
	 * */
	public boolean agregar(TElementoColaEspera elementoCola) {
		return this.colaEspera.offer(elementoCola);
	}
	
	/**
	 * Este método quita y retorna el primer elemento de la cola.
	 * Si la cola está vacia retorna null.
	 * */
	public TElementoColaEspera sacar() {
		return this.colaEspera.poll();
	}
	
	/**
	 * Este método retorna la cantidad de elementos que hay en lo cola.
	 * */
	public int tamaño() {
		return this.colaEspera.size();
	}
	
	/**
	 * Este método retorna el primer elemento de la cola sin eliminarlo.
	 * Si la cola está vacia retorna null.
	 * */
	public TElementoColaEspera primerElemento() {
		return this.colaEspera.peek();
	}
	
}
