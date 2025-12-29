package org.cuatrovientos.dam.psp.cineV1;

public class GeneradorClientes implements Runnable{
	
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
			Cliente cliente = new Cliente(idNuevoCliente++);
			colaDeVenta.anadirCliente(cliente);
		}
	}

}
