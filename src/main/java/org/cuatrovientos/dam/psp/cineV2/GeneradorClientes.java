package org.cuatrovientos.dam.psp.cineV2;

import java.util.Random;

public class GeneradorClientes implements Runnable{
	
	Random rnd = new Random();
	
	private int id;
	private ColaDeVenta[] colasDeVenta;
	private boolean estaActivo = true;

	public GeneradorClientes(int id, ColaDeVenta[] colasDeVenta) {
		super();
		this.id = id;
		this.colasDeVenta = colasDeVenta;
	}

	@Override
	public void run() {
		int idNuevoCliente = Configuracion.NUM_ID_INICIAL;
		while (estaActivo) {
			try {
				Cliente cliente = new Cliente(idNuevoCliente++);
				boolean clienteAnadido = false;
				
				for (ColaDeVenta colaDeVenta : colasDeVenta) {
					if (colaDeVenta.anadirCliente(cliente)) {
						System.out.println("Cliente " + id + " añadido a la cola de venta " + colaDeVenta.getId());
						clienteAnadido = true;
						break;
					}
				}
				
				if (!clienteAnadido) {
					System.out.println("CLIENTE " + id + "SE MARCHA! NO HAY AFORO!");
				}
				
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
