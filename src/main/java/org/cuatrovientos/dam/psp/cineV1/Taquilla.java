package org.cuatrovientos.dam.psp.cineV1;

import java.util.Random;

public class Taquilla implements Runnable {

	Random rnd = new Random();

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
		long tiempoInicio = System.currentTimeMillis();
		System.out.println("> La Taquilla " + id + " acaba de abrir");
		
		while (abierta && cine.asientosLibres() > 0) {
			try {
				Cliente cliente = colaDeVenta.cogerCliente();

				if (cliente != null) {
					long tiempoPorVenta = rnd.nextLong(Configuracion.TIEMPO_VENTA_MIN, Configuracion.TIEMPO_VENTA_MAX);
					Thread.sleep(tiempoPorVenta);

					if (cine.venderEntrada()) {
						System.out.println("TAQUILLA " + id + ": entrada vendida al cliente " + cliente.getId());
					} else {
						System.out.println("TAQUILLA " + id + ": SE HAN AGOTADO LAS ENTRADAS");
						cine.setTiempoEnVenderTodo(System.currentTimeMillis() - tiempoInicio);
						cerrarTaquilla();					
					}
				}else {
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
