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
		// Pedimos permiso al semáforo. Si devuelve false es que la cola está llena (no hay huecos libres). 
		// Usamos tryAcquire para que no bloquearse si la cola está llena y asi que siga buscando en otras colas.
		if (semaforoAforo.tryAcquire()) {
			// Usamos synchronized para que el acceso a la cola sea exclusivo y evitar problemas si dos hilos entran a la vez.
			synchronized (this) {
				cola.add(cliente);
				return true;
			}
		}
		return false;
	}
	
	public Cliente cogerCliente() {
		Cliente primerCliente = null;
		// Usamos synchronized para que el acceso a la cola sea exclusivo y evitar problemas si dos hilos entran a la vez.
		synchronized (this) {
				primerCliente = cola.poll();
		}		
		// Si el cliente que ha cogido existe (no es null) liberamos un hueco del semáforo con release.
		if (primerCliente != null) {
			semaforoAforo.release();
		}
		return primerCliente;
	}
	
	// Synchronized para lectura segura.
	public synchronized int numClientesEnCola() {
		return cola.size();
	}
	
	// Método para que la taquilla compruebe si hay trabajo antes de intentar coger.
	public synchronized boolean hayClientes() {
		return cola.isEmpty() ? false : true;
	}

	public int getId() {
		return id;
	}

}
