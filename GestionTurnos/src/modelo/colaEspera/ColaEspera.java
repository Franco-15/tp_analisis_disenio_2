package modelo.colaEspera;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class ColaEspera implements IColaEspera, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static ColaEspera instance = null;

	private Queue<TElementoColaEspera> colaEspera;

	private ColaEspera() {
		this.colaEspera = new LinkedList<>();
	}

	public static ColaEspera getInstance() {
		if (instance == null) {
			instance = new ColaEspera();
		}
		return instance;
	}

	/**
	 * Este método agrega un elemento a la cola. Si la cola está llena, retorna
	 * false.
	 */
	public boolean agregar(TElementoColaEspera elementoCola) {
		return this.colaEspera.offer(elementoCola);
	}

	/**
	 * Este método quita y retorna el primer elemento de la cola. Si la cola está
	 * vacia retorna null.
	 */
	public TElementoColaEspera sacar() {
		return this.colaEspera.poll();
	}

	/**
	 * Este método retorna la cantidad de elementos que hay en lo cola.
	 */
	public int tamaño() {
		return this.colaEspera.size();
	}

	/**
	 * Este método retorna el primer elemento de la cola sin eliminarlo. Si la cola
	 * está vacia retorna null.
	 */
	public TElementoColaEspera primerElemento() {
		return this.colaEspera.peek();
	}

	public boolean existe(TElementoColaEspera elemento) {
	    for (TElementoColaEspera elem : this.colaEspera) {
	        if (elem.getDni().equals(elemento.getDni())) {
	            return true;
	        }
	    }
	    return false;
	}

	public Queue<TElementoColaEspera> getColaEspera() {
		return colaEspera;
	}

	public void setColaEspera(Queue<TElementoColaEspera> colaEspera) {
		this.colaEspera = colaEspera;
	}

}
