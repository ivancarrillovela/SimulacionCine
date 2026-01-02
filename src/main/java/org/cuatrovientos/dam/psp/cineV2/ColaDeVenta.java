package org.cuatrovientos.dam.psp.cineV2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ColaDeVenta{
	
	private int id;
	private Queue<Cliente> cola = new LinkedList<>();
	private Semaphore semaforoAforo;

	public ColaDeVenta(int id, int aforoMaximo) {
		this.id = id;
		this.semaforoAforo = new Semaphore(aforoMaximo);
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
