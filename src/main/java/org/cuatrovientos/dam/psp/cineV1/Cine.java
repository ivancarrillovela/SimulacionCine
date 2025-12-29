package org.cuatrovientos.dam.psp.cineV1;

public class Cine {
	
	private String nombre;
	private int asientosDisponibles;
	private int entradasVendidas;
	
	public Cine(String nombre, int asientos) {
		super();
		this.nombre = nombre;
		this.asientosDisponibles = asientos;
	}
	
	public boolean venderEntrada() {
		if (asientosDisponibles < 0) {
			asientosDisponibles--;
			entradasVendidas++;
			return true;
		}
		return false;
	}

}
