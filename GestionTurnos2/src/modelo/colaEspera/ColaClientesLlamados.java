package modelo.colaEspera;

import java.util.LinkedList;
import java.util.Queue;

public class ColaClientesLlamados implements IColaEspera{
	private static ColaClientesLlamados instance = null;
	
	private Queue<TElementoColaEspera> colaEspera = new LinkedList<>();
	
	private ColaClientesLlamados() {}
	
	public static ColaClientesLlamados getInstance() {
        if (instance == null) {
            instance = new ColaClientesLlamados();
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

	public boolean existe(TElementoColaEspera elemento) {
		return this.colaEspera.contains(elemento);
	}

	@Override
	public Queue<TElementoColaEspera> getColaEspera() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColaEspera(Queue<TElementoColaEspera> colaEspera) {
		// TODO Auto-generated method stub
		
	}
	
}
