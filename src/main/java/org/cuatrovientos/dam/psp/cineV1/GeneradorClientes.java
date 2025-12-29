package org.cuatrovientos.dam.psp.cineV1;

import java.util.Random;

public class GeneradorClientes implements Runnable{
	
	Random rnd = new Random();
	
	private int id;
	private ColaDeVenta colaDeVenta;
	private boolean estaActivo = true;

	public GeneradorClientes(int id, ColaDeVenta colaDeVenta) {
		super();
		this.id = id;
		this.colaDeVenta = colaDeVenta;
	}

	@Override
	public void run() {
		int idNuevoCliente = 0;
		while (estaActivo) {
			try {
				Cliente cliente = new Cliente(idNuevoCliente++);
				colaDeVenta.anadirCliente(cliente);
				
				long tiempoEspera = rnd.nextLong(4000,6001);
				Thread.sleep(tiempoEspera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
