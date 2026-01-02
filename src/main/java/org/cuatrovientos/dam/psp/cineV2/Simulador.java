package org.cuatrovientos.dam.psp.cineV2;

public class Simulador {

	public static void main(String[] args) {

		Cine miCine = new Cine(Configuracion.NOMBRE_CINE, Configuracion.NUM_ASIENTOS);
		
		ColaDeVenta[] colasDeVenta = new ColaDeVenta[4];
		for (int i = 0; i < 4; i++) {
			colasDeVenta[i] = new ColaDeVenta(i + 1, 10);
		}
		
		GeneradorClientes generadorClientes = new GeneradorClientes(Configuracion.NUM_ID_INICIAL, colasDeVenta);
		Taquilla taquilla1 = new Taquilla(1, miCine, colasDeVenta);
		Taquilla taquilla2 = new Taquilla(2, miCine, colasDeVenta);
		
		Thread hiloGenerador = new Thread(generadorClientes);
		Thread hiloT1 = new Thread(taquilla1);
		Thread hiloT2 = new Thread(taquilla2);
		
		System.out.println("---BIENVENIDO A LOS CINES " + miCine.getNombre().toUpperCase() + ".V2---");
		System.out.println("¡¡¡SE ABREN LAS TAQUILLAS!!! Quedan 30 minutos para empezar la película");
		
		hiloGenerador.start();
		hiloT1.start();
		hiloT2.start();
		
		try {
			Thread.sleep(Configuracion.TIEMPO_APERTURA);
			System.out.println("¡¡¡SE CIERRAN LAS TAQUILLAS!!! La película empieza ya");
			
			generadorClientes.pararGenerador();
			taquilla1.cerrarTaquilla();
			taquilla2.cerrarTaquilla();
			
			hiloGenerador.join();
			hiloT1.join();
			hiloT2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("DATOS FINALES:");
		System.out.println(miCine.getEntradasVendidas() + "clientes han visto la película");
		System.out.println(colaDeVenta.numClientesEnCola() + "clientes se han quedado sin entrada");
		System.out.println("Se han tardado " + miCine.getTiempoEnVenderTodo() / 60 / 1000 + " minutos en vender todas las entradas");
	}

}
