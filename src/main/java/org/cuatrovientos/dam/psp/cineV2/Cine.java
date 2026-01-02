package org.cuatrovientos.dam.psp.cineV2;

public class Cine {
	
	private String nombre;
	private int asientosDisponibles;
	private int entradasVendidas = 0;
	private long tiempoEnVenderTodo;
	
	public Cine(String nombre, int asientos) {
		super();
		this.nombre = nombre;
		this.asientosDisponibles = asientos;
	}
	
	// Synchronized para asegurarnos de no vender la misma entrada por varios hilos diferentes a la vez.
	public synchronized boolean venderEntrada() {
		if (asientosDisponibles > 0) {
			asientosDisponibles--;
			entradasVendidas++;
			return true;
		}
		return false;
	}
	
	// Synchronized para tener siempre el valor real actual.
	public synchronized int asientosLibres() {
		return asientosDisponibles;
	}

	public int getEntradasVendidas() {
		return entradasVendidas;
	}
	
	public synchronized void setTiempoEnVenderTodo(long tiempoEnVenderTodo) {
		this.tiempoEnVenderTodo = tiempoEnVenderTodo;
	}

	public long getTiempoEnVenderTodo() {
		return tiempoEnVenderTodo;
	}

	public String getNombre() {
		return nombre;
	}

}
