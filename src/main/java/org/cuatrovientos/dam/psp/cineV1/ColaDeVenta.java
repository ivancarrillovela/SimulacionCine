package org.cuatrovientos.dam.psp.cineV1;

import java.util.LinkedList;
import java.util.Queue;

public class ColaDeVenta{
	
	private int id;
	private Queue<Cliente> cola = new LinkedList<>();

	public ColaDeVenta(int id) {
		this.id = id;
	}
	
	public synchronized void anadirCliente(Cliente cliente) {
		this.cola.add(cliente);
	}
	
	public synchronized Cliente cogerCliente() {
		return cola.isEmpty() ? null : cola.poll();
	}
	
	public synchronized int numClientesEnCola() {
		return cola.size();
	}

}
