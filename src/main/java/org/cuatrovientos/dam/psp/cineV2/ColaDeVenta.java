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
	
	public boolean anadirCliente(Cliente cliente) {
		if (semaforoAforo.tryAcquire()) {
			synchronized (this) {
				cola.add(cliente);
				return true;
			}
		}
		return false;
	}
	
	public Cliente cogerCliente() {
		Cliente primerCliente = null;
		if (semaforoAforo.tryAcquire()) {
			synchronized (this) {
				primerCliente = cola.poll();
			}
		}
		if (primerCliente != null) {
			semaforoAforo.release();
		}
		return primerCliente;
	}
	
	public synchronized int numClientesEnCola() {
		return cola.size();
	}

	public int getId() {
		return id;
	}

}
