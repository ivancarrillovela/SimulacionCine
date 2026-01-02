package org.cuatrovientos.dam.psp.cineV2;

public class Simulador {

	public static void main(String[] args) {

		Cine miCine = new Cine(Configuracion.NOMBRE_CINE, Configuracion.NUM_ASIENTOS);
		
		ColaDeVenta[] colasDeVenta = new ColaDeVenta[Configuracion.NUM_COLAS];
		for (int i = 0; i < Configuracion.NUM_COLAS; i++) {
			colasDeVenta[i] = new ColaDeVenta(i + 1, Configuracion.AFORO_MAX_POR_COLA);
		}
		
		GeneradorClientes generadorClientes = new GeneradorClientes(Configuracion.NUM_ID_INICIAL, colasDeVenta);
		Thread hiloGenerador = new Thread(generadorClientes);
		
		Taquilla[] taquillas = new Taquilla[Configuracion.NUM_TAQUILLAS];
		Thread[] hilosTaquillas = new Thread[Configuracion.NUM_TAQUILLAS];
		
		for (int i = 0; i < Configuracion.NUM_TAQUILLAS; i++) {
			taquillas[i] = new Taquilla(i + 1, miCine, colasDeVenta);
			hilosTaquillas[i] = new Thread(taquillas[i]);
		}
		
		System.out.println("---BIENVENIDO A LOS CINES " + miCine.getNombre().toUpperCase() + ".V2---");
		System.out.println("¡¡¡SE ABREN LAS TAQUILLAS!!! Quedan 30 minutos para empezar la película");
		
		hiloGenerador.start();
		for (Thread hilo : hilosTaquillas) {
			hilo.start();
		}
		
		try {
			Thread.sleep(Configuracion.TIEMPO_APERTURA);
			System.out.println("¡¡¡SE CIERRAN LAS TAQUILLAS!!! La película empieza ya");
			
			generadorClientes.pararGenerador();
			for (Taquilla taquilla : taquillas) {
				taquilla.cerrarTaquilla();
			}
			
			hiloGenerador.join();
			for (Thread hilo : hilosTaquillas) {
				hilo.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("DATOS FINALES:\n");
		System.out.println(miCine.getEntradasVendidas() + "clientes han visto la película\n");
		
		int totalSinEntrada = 0;
		for (ColaDeVenta colaDeVenta : colasDeVenta) {
			System.out.println("Cola de venta " + colaDeVenta.getId() + ": " + colaDeVenta.numClientesEnCola() + "clientes se han quedado sin entrada");
			totalSinEntrada += colaDeVenta.numClientesEnCola();
		}
		System.out.println("TOTAL CLIENTES SIN ENTRADA: " + totalSinEntrada + "\n");
		System.out.println("Se han tardado " + miCine.getTiempoEnVenderTodo() / 60 / 1000 + " minutos en vender todas las entradas");
	}

}
