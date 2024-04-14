package gestion_turnos;

public interface IColaEspera {
	public boolean agregar(TElementoColaEspera elementoCola);
	public TElementoColaEspera sacar();
	public int tamaño();
	public TElementoColaEspera primerElemento();
}
