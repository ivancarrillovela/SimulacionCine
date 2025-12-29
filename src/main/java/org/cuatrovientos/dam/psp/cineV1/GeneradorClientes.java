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
		int idNuevoCliente = Configuracion.ID_INICIAL;
		while (estaActivo) {
			try {
				Cliente cliente = new Cliente(idNuevoCliente++);
				colaDeVenta.anadirCliente(cliente);
				
				long tiempoEspera = rnd.nextLong(Configuracion.TIEMPO_GENERAR_CLIENTE_MIN, Configuracion.TIEMPO_GENERAR_CLIENTE_MAX);
				Thread.sleep(tiempoEspera);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("> SE HA PARADO EL GENERADOR DE CLIENTES");
	}
	
	public void pararGenerador() {
		this.estaActivo = false;
	}

}
