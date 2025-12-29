package org.cuatrovientos.dam.psp.cineV1;

public class Taquilla implements Runnable{

	private int id;
	private Cine cine;
	private ColaDeVenta colaDeVenta;
	private boolean abierta = true;
	
	public Taquilla(int id, Cine cine, ColaDeVenta colaDeVenta) {
		super();
		this.id = id;
		this.cine = cine;
		this.colaDeVenta = colaDeVenta;
	}

	@Override
	public void run() {
		System.out.println("La Taquilla " + id + " acaba de abrir");
		while(abierta && cine.asientosLibres() > 0) {
			
		}
	}
	
	public void cerrarTaquilla() {
		this.abierta = false;
	}

}
