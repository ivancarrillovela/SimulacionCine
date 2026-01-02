package org.cuatrovientos.dam.psp.cineV2;

import java.util.Random;

public class Taquilla implements Runnable {

	Random rnd = new Random();

	private int id;
	private Cine cine;
	private ColaDeVenta[] colasDeVenta;
	private boolean abierta = true;
			
	public Taquilla(int id, Cine cine, ColaDeVenta[] colasDeVenta) {
		super();
		this.id = id;
		this.cine = cine;
		this.colasDeVenta = colasDeVenta;
	}

	@Override
	public void run() {
		long tiempoInicio = System.currentTimeMillis();
		System.out.println("> La Taquilla " + id + " acaba de abrir");
		
		while (abierta && cine.asientosLibres() > 0) {
			try {
				Cliente clienteAtendido = null;
				// La taquilla recorre el array de colas buscando una con clientes para coger al primero de la cola.
				for (ColaDeVenta colaDeVenta : colasDeVenta) {
					if (colaDeVenta.hayClientes()) {
						clienteAtendido = colaDeVenta.cogerCliente();
						break;
					}
				}

				if (clienteAtendido != null) {
					//El hilo se duerme para simular el tiempo que tarda una venta.
					long tiempoPorVenta = rnd.nextLong(Configuracion.TIEMPO_VENTA_MIN, Configuracion.TIEMPO_VENTA_MAX);
					Thread.sleep(tiempoPorVenta);
					
					//Llamamos al Cine. Al ser synchronized si otra taquilla está vendiendo la última entrada nosotros esperaremos aquí.
					if (cine.venderEntrada()) {
						System.out.println("TAQUILLA " + id + ": entrada vendida al cliente " + clienteAtendido.getId());
					} else {
						// Este caso es por si mientras atendíamos al cliente se vendió la última entrada.
						System.out.println("TAQUILLA " + id + ": SE HAN AGOTADO LAS ENTRADAS");
						cine.setTiempoEnVenderTodo(System.currentTimeMillis() - tiempoInicio);
						cerrarTaquilla();					
					}
				}else {
					// Si no hay nadie en ninguna cola se descansa un poco para evitar un bucle infinito que pone la CPU al 100%.
					Thread.sleep(Configuracion.ESPERA_PASIVA);
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("SE HA CERRADO LA TAQUILLA " + id);
	}

	public void cerrarTaquilla() {
		this.abierta = false;
	}

}
