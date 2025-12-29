package org.cuatrovientos.dam.psp.cineV1;

import java.util.LinkedList;
import java.util.Queue;

public class ColaDeVenta{
	
	private Queue<Cliente> colaInfinita;

	public ColaDeVenta() {
		super();
		this.colaInfinita = new LinkedList<>();
	}
	
	public synchronized void anadirCliente(Cliente cliente) {
		this.colaInfinita.add(cliente);
	}
	
	public synchronized boolean estaVacia() {
		return colaInfinita.isEmpty();
	}

}
