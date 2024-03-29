package aplicacion;

import datos.GrafoEstaciones;
import datos.ZonaRecarga;
import excepciones.NoExiste;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class PruebaGrafoEstaciones {
	public static void main(String[] args) throws FileNotFoundException {
		// Cargamos los datos de JSON al grafo
		LinkedList<ZonaRecarga> listaZonas = ZonaRecarga.leerJson("src/main/resources/icaen.json");
		LinkedList<ZonaRecarga> listaZonasReducida = ZonaRecarga.leerJson("src/main/resources/pruebas.json");

		// Añadimos las estaciones al grafo
		System.out.println("\n----------PRUEBA CONSTRUCTOR--------------");
		System.out.println("Pasamos al constructor los datos a guardar en el grafo");
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);
		GrafoEstaciones grafoReducido = new GrafoEstaciones(listaZonasReducida);
		System.out.println(grafoReducido); // Imprimimos solo el grafo reducido porque es más facil de ver

		// Prueba de camino optimo
		pruebaCaminoOptimo(grafoEstaciones);

		// Algoritmo de zonas no alcanzables
		pruebaDistMaxNoGarantizada(grafoEstaciones, grafoReducido);
	}

	private static void pruebaDistMaxNoGarantizada(GrafoEstaciones grafoEstaciones, GrafoEstaciones grafoReducido) {
		System.out.println("\n----------PRUEBA ZONAS DISTANCIA MAXIMA NO GARANTIZADA--------------");
		try {
			System.out.println("En el grafo reducido escogemos el nodo 1 (Tarragona) y miramos a que zonas no se puede acceder con una autonomia de 300km");
			System.out.println(grafoReducido.zonasDistMaxNoGarantizada("1", 300) + "son zonas que no estan conectadas al grafo");

			System.out.println("\nEscogemos como origen la zona 9165 (Cambrils), las zonas que no se pueden alcanzar");
			System.out.println("Probamos autonomia de 10000 km:"+grafoEstaciones.zonasDistMaxNoGarantizada("9165", 10000));
			System.out.println("Probamos autonomia de    40 km:"+grafoEstaciones.zonasDistMaxNoGarantizada("9165", 40));
			System.out.println("Probamos autonomia de    39 km:"+grafoEstaciones.zonasDistMaxNoGarantizada("9165", 39));
			System.out.println("Probamos autonomia de    20 km:"+grafoEstaciones.zonasDistMaxNoGarantizada("9165", 20));

			System.out.println("\nTomamos como origen la zona 9904800 (Móra d'Ebre)");
			System.out.println("Probamos autonomia de 25 km:"+grafoEstaciones.zonasDistMaxNoGarantizada("9904800", 25));

			System.out.println("\nUsamos como origen la zona 33852430 (Almedinilla, Córdoba), es una zona no conectada a la red");
			System.out.println("Probamos autonomia de 5000 km:"+grafoEstaciones.zonasDistMaxNoGarantizada("33852430", 5000));
		} catch (NoExiste e) {
			e.printStackTrace();
		}


		try {
			System.out.println("\nIntentamos usar un nodo no presente en el grafo");
			System.out.println("ERROR:"+grafoEstaciones.zonasDistMaxNoGarantizada("1", 5000));
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\nIntentamos usar un vertice nulo");
			System.out.println("ERROR:"+grafoEstaciones.zonasDistMaxNoGarantizada(null, 5000));
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\nIntentamos usar una autonomia de 0");
			System.out.println("ERROR:"+grafoEstaciones.zonasDistMaxNoGarantizada("33852430", 0));
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}
	}

	private static void pruebaCaminoOptimo(GrafoEstaciones grafoEstaciones) {
		System.out.println("\n----------PRUEBA CAMINO OPTIMO--------------");
		LinkedList<String> ruta;
		StringBuilder distancia;
		try {
			// Probamos una ruta entre dos zonas de recarga cercanas
			distancia = new StringBuilder("-Distancia Recorrida: ");
			System.out.println("\nProbamos una ruta entre dos zonas de recarga cercanas (9165-->9168)");
			ruta = grafoEstaciones.caminoOptimo("9165", "9168", 100, distancia);
			System.out.println(distancia + " km --> " +ruta);

			// Probamos una ruta entre dos zonas lejanas y con autonomia baja
			distancia = new StringBuilder("-Distancia Recorrida: ");
			System.out.println("\nProbamos una ruta entre dos zonas lejanas y con autonomia baja (9165-->34252288) 20km");
			ruta = grafoEstaciones.caminoOptimo("9165", "34252288", 20, distancia);
			System.out.println(distancia + " km --> " +ruta);

			// Probamos una ruta entre dos zonas lejanas y con autonomia media
			distancia = new StringBuilder("-Distancia Recorrida: ");
			System.out.println("\nProbamos una ruta entre dos zonas lejanas y con autonomia media (9165-->34252288) 50km");
			ruta = grafoEstaciones.caminoOptimo("9165", "34252288", 50, distancia);
			System.out.println(distancia + " km --> " +ruta);

			// Probamos una ruta entre dos zonas muy lejanas con autonomia alta 150 km
			distancia = new StringBuilder("-Distancia Recorrida: ");
			System.out.println("\nProbamos una ruta entre dos zonas muy lejanas y con autonomia alta (13361299-->7562018) 150km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", 150, distancia);
			System.out.println(distancia + " km --> " +ruta);

			// Probamos una ruta entre dos zonas muy lejanas con autonomia baja 30 km
			distancia = new StringBuilder("-Distancia Recorrida: ");
			System.out.println("\nProbamos una ruta entre dos zonas muy lejanas y con autonomia media (13361299-->7562018) 30km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", 30, distancia);
			System.out.println(distancia + " km --> " +ruta);
		} catch (NoExiste e) {
			System.out.println("ERROR"); // Fallo
		}

		try {// Probamos una ruta pasando como parametro una autonomia no valida
			System.out.println("\nProbamos una ruta pasando como parametro una autonomia no valida");
			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", -5);
			System.out.println("ERROR" + ruta); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {// Probamos una ruta pasando como parametro una zona de destino no presente en el grafo
			System.out.println("\nProbamos una ruta pasando como parametro una zona de destino no presente en el grafo (9165->1)");
			ruta = grafoEstaciones.caminoOptimo("9165", "1", 30);
			System.out.println("ERROR" + ruta); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {// Probamos una ruta pasando como parametro una zona de destino nulo
			System.out.println("\nProbamos una ruta pasando como parametro una zona de destino nulo");
			ruta = grafoEstaciones.caminoOptimo(null, "13361299", 30);
			System.out.println("ERROR" + ruta); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {// Probamos una ruta pasando como parametro una zona de destino igual al origen
			distancia = new StringBuilder("-Distancia Recorrida: ");
			System.out.println("\nProbamos una ruta pasando como parametro una zona de destino igual al origen (13361299-->13361299) 30km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "13361299", 30, distancia);
			System.out.println(distancia + " km -->" + ruta);
		} catch (NoExiste e) {
			System.out.println("ERROR"); // ERROR
		}


		try {
			System.out.println("\nProbamos una ruta entre dos zonas distantes y con autonomia muy baja (13361299-->34252288) 10km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "34252288", 10);
			System.out.println("ERROR" + ruta);
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

	}
}