package org.cuatrovientos.dam.psp.cineV1;

public class Simulador {
	
	static int NUM_ASIENTOS = 200;
	static int TIEMPO_APERTURA = 30 * 60 * 1000;

	public static void main(String[] args) {

		Cine miCine = new Cine("Golem La Morea", NUM_ASIENTOS);
		ColaDeVenta colaDeVenta = new ColaDeVenta(1);
		
		GeneradorClientes generadorClientes = new GeneradorClientes(1, colaDeVenta);
		Taquilla taquilla1 = new Taquilla(1, miCine, colaDeVenta);
		Taquilla taquilla2 = new Taquilla(2, miCine, colaDeVenta);
		
		Thread hiloGenerador = new Thread(generadorClientes);
		Thread hiloT1 = new Thread(taquilla1);
		Thread hiloT2 = new Thread(taquilla2);
		
		System.out.println("> ABREN LAS TAQUILLAS!!! Quedan 30 mins para empezar la película");
		
		hiloGenerador.start();
		hiloT1.start();
		hiloT2.start();
		
		try {
			Thread.sleep(TIEMPO_APERTURA);
			System.out.println("SE CIERRAN LAS TAQUILLAS!!! La película va ha empezar ya");
			
			generadorClientes.pararGenerador();
			taquilla1.cerrarTaquilla();
			taquilla2.cerrarTaquilla();
			
			hiloGenerador.join();
			hiloT1.join();
			hiloT2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
