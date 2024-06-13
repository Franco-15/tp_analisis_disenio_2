package gestionTurnos;

import java.util.LinkedList;
import java.util.Queue;

import comunes.TElementoColaEspera;

public class ColaRegistrados {
	private static ColaRegistrados instance = null;
	private Queue<TElementoColaEspera> colaRegistrados;

	private ColaRegistrados() {
		this.setColaRegistrados(new LinkedList<>());
	}

	public static ColaRegistrados getInstance() {
		if (instance == null) {
			instance = new ColaRegistrados();
		}
		return instance;
	}

	public Queue<TElementoColaEspera> getColaRegistrados() {
		return colaRegistrados;
	}

	public void setColaRegistrados(Queue<TElementoColaEspera> colaRegistrados) {
		this.colaRegistrados = colaRegistrados;
	}
}
