package aplicacion;

import datos.GrafoEstaciones;
import datos.ZonaRecarga;
import excepciones.NoExiste;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class PruebaGrafoEstaciones {
	public static void main(String[] args) throws FileNotFoundException { //TODO controlar excepcion
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
		System.out.println("\n----------PRUEBA ZONAS DISTANCIA MAXIMA NO GARANTIZADA--------------");
	}

	private static void pruebaCaminoOptimo(GrafoEstaciones grafoEstaciones) {
		System.out.println("\n----------PRUEBA CAMINO OPTIMO--------------");
		LinkedList<String> ruta;
		try {
			// Probamos una ruta entre dos zonas de recarga cercanas
			System.out.println("\nProbamos una ruta entre dos zonas de recarga cercanas (9165-->9168)");
			ruta = grafoEstaciones.caminoOptimo("9165", "9168", 100);
			System.out.println("-->"+ruta);

			// Probamos una ruta entre dos zonas lejanas y con autonomia baja
			System.out.println("\nProbamos una ruta entre dos zonas lejanas y con autonomia baja (9165-->34252288) 20km");
			ruta = grafoEstaciones.caminoOptimo("9165", "34252288", 20);
			System.out.println("-->"+ruta);

			// Probamos una ruta entre dos zonas lejanas y con autonomia media
			System.out.println("\nProbamos una ruta entre dos zonas lejanas y con autonomia media (9165-->34252288) 50km");
			ruta = grafoEstaciones.caminoOptimo("9165", "34252288", 50);
			System.out.println("-->"+ruta);

			// Probamos una ruta entre dos zonas muy lejanas con autonomia alta 150km
			System.out.println("\nProbamos una ruta entre dos zonas lejanas y con autonomia alta (13361299-->34252288) 150km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", 150);
			System.out.println("-->"+ruta);

			// Probamos una ruta entre dos zonas muy lejanas con autonomia baja 30km
			System.out.println("\nProbamos una ruta entre dos zonas muy lejanas y con autonomia baja (13361299-->7562018) 30km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", 30);
			System.out.println("-->"+ruta);

			// Probamos una ruta pasando como parametro una autonomia no valida
			System.out.println("\nProbamos una ruta pasando como parametro una autonomia no valida");
			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", -5);
			System.out.println("ERROR"); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {// Probamos una ruta pasando como parametro una zona de destino no presente en el grafo
			System.out.println("\nProbamos una ruta pasando como parametro una zona de destino no presente en el grafo (9165->1)");
			ruta = grafoEstaciones.caminoOptimo("9165", "1", 30);
			System.out.println("ERROR"); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {// Probamos una ruta pasando como parametro una zona de destino nulo
			System.out.println("\nProbamos una ruta pasando como parametro una zona de destino nulo");
			ruta = grafoEstaciones.caminoOptimo(null, "13361299", 30);
			System.out.println("ERROR"); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

		try {// Probamos una ruta pasando como parametro una zona de destino igual al origen
			System.out.println("\nProbamos una ruta pasando como parametro una zona de destino igual al origen (13361299-->13361299) 30km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "13361299", 30);
			System.out.println(ruta);
		} catch (NoExiste e) {
			System.out.println("ERROR"); // ERROR
		}


		try {
			System.out.println("\nProbamos una ruta entre dos zonas distantes y con autonomia muy baja (13361299-->34252288) 10km");
			ruta = grafoEstaciones.caminoOptimo("13361299", "34252288", 10);
			System.out.println("ERROR");
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}

	}


}