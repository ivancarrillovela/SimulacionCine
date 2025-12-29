package org.cuatrovientos.dam.psp.cineV1;

import java.util.LinkedList;
import java.util.Queue;

public class ColaDeVenta{
	
	private Queue<Cliente> colaInfinita = new LinkedList<>();

	public ColaDeVenta() {}
	
	public synchronized void anadirCliente(Cliente cliente) {
		this.colaInfinita.add(cliente);
	}
	
	public synchronized Cliente cogerCliente() {
		return colaInfinita.poll();
	}
	
	public synchronized boolean estaVacia() {
		return colaInfinita.isEmpty();
	}

}
