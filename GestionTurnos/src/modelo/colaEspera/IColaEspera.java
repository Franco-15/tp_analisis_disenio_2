package modelo.colaEspera;

import java.util.Queue;

public interface IColaEspera {
	public boolean agregar(TElementoColaEspera elementoCola);

	public TElementoColaEspera sacar();

	public int tamaño();

	public TElementoColaEspera primerElemento();
	
	public boolean existe(TElementoColaEspera elemento);

	public Queue<TElementoColaEspera> getColaEspera();

	public void setColaEspera(Queue<TElementoColaEspera> colaEspera);
	
}
