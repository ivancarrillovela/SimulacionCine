package org.cuatrovientos.dam.psp.cineV1;

public class Configuracion {
	
	// Nombre del cine
	public static final String NOMBRE_CINE = "Carlos III";
	
	// Asientos que tiene el cine
    public static final int NUM_ASIENTOS = 200;

    // Duración de apertura de taquillas (30 minutos) en milisegundos
    public static final int TIEMPO_APERTURA = 30 * 60 * 1000;

    // Tiempos para hacer una venta de entrada
    public static final long TIEMPO_VENTA_MIN = 20 * 1000;
    public static final long TIEMPO_VENTA_MAX = 30 * 1000 + 1; // Sumo 1 por que Random excluye el número máximo

    // Tiempos para generar cada cliente
    // Entre 10 clientes/min (cada 6 segs) y 15 clientes/min (cada 4 segs)
    public static final long TIEMPO_GENERAR_CLIENTE_MIN = 4 * 1000;
    public static final long TIEMPO_GENERAR_CLIENTE_MAX = 6 * 1000 + 1; // Sumo 1 por que Random excluye el número máximo
    
    // Tiempo de espera pasivo para Threat.sleep
    public static final long ESPERA_PASIVA = 1000;
    
    // Numero incial para Ids
    public static final int ID_INICIAL = 1;
    
}
