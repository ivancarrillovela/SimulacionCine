package org.cuatrovientos.dam.psp.cineV2;

public class Simulador {

	private static Cine miCine = new Cine(Configuracion.NOMBRE_CINE, Configuracion.NUM_ASIENTOS);
	private static ColaDeVenta[] colasDeVenta = new ColaDeVenta[Configuracion.NUM_COLAS];

	private static GeneradorClientes generadorClientes = new GeneradorClientes(Configuracion.NUM_ID_INICIAL, colasDeVenta);
	private static Thread hiloGenerador = new Thread(generadorClientes);

	private static Taquilla[] taquillas = new Taquilla[Configuracion.NUM_TAQUILLAS];
	private static Thread[] hilosTaquillas = new Thread[Configuracion.NUM_TAQUILLAS];

	public static void main(String[] args) {
		try {
			crearColasDeVenta();

			crearTaquillas();

			System.out.println("---BIENVENIDO A LOS CINES " + miCine.getNombre().toUpperCase() + ".V2---");
			System.out.println("¡¡¡SE ABREN LAS TAQUILLAS!!! Quedan 30 minutos para empezar la película");

			lanzarHilosTaquillas();
			
			// Paramos el hilo main el tiempo de apertura asignado en la configuración.
			Thread.sleep(Configuracion.TIEMPO_APERTURA); 
			
			System.out.println("¡¡¡SE CIERRAN LAS TAQUILLAS!!! La película empieza ya");

			generadorClientes.pararGenerador();
			
			cerrarTaquillas();
			
			mostrarDatosFinales();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void mostrarDatosFinales() {
		System.out.println("DATOS FINALES:\n");
		System.out.println(miCine.getEntradasVendidas() + "clientes han visto la película\n");

		int totalSinEntrada = 0;
		for (ColaDeVenta colaDeVenta : colasDeVenta) {
			System.out.println("Cola de venta " + colaDeVenta.getId() + ": " + colaDeVenta.numClientesEnCola()
					+ "clientes se han quedado sin entrada");
			totalSinEntrada += colaDeVenta.numClientesEnCola();
		}
		System.out.println("TOTAL CLIENTES SIN ENTRADA: " + totalSinEntrada + "\n");
		System.out.println("Se han tardado " + miCine.getTiempoEnVenderTodo() / 60 / 1000
				+ " minutos en vender todas las entradas");
	}

	private static void cerrarTaquillas() throws InterruptedException {
		for (Taquilla taquilla : taquillas) {
			taquilla.cerrarTaquilla();
		}

		hiloGenerador.join();
		for (Thread hilo : hilosTaquillas) {
			hilo.join();
		}
	}

	private static void crearColasDeVenta() {
		for (int i = 0; i < Configuracion.NUM_COLAS; i++) {
			colasDeVenta[i] = new ColaDeVenta(i + 1, Configuracion.AFORO_MAX_POR_COLA);
		}
	}

	private static void lanzarHilosTaquillas() {
		hiloGenerador.start();
		for (Thread hilo : hilosTaquillas) {
			hilo.start();
		}
	}

	private static void crearTaquillas() {
		for (int i = 0; i < Configuracion.NUM_TAQUILLAS; i++) {
			taquillas[i] = new Taquilla(i + 1, miCine, colasDeVenta);
			hilosTaquillas[i] = new Thread(taquillas[i]);
		}
	}

}
