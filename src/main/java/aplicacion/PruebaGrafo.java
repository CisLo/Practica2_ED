package aplicacion;

import TADGrafoGenerico.GrafoGenerico;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

public class PruebaGrafo {
	public static void main(String[] args) {

		// Probamos constructor
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(5);

		// Probamos Insercion Vertice
		pruebaInsercionVertice();


		// Probamos a Añadir una arista
		pruebaAgregarArista();


		//

	}

	private static void pruebaAgregarArista() {
		System.out.println("\n----------PRUEBA AÑADIR ARISTA--------------");
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(5);
		agregarVertices(grafo);
		try {
			System.out.println("\nAñadimos arista entre vertice 1 y 2");
			grafo.agregarArista("1", "2", 100);
			System.out.println(grafo);

			System.out.println("\nIntentamos añadir vertice entre 3 y 2");
			grafo.agregarArista("3", "2", 75); // Añadimos arista, vertice 1 -> es el mayor, vertice 2 -> el menor
			System.out.println(grafo);

			System.out.println("\nAñadimos arista a los vertices 1 y 3");
			grafo.agregarArista("1", "3", 90); // Añadimos arista, vertice 1 -> es el mayor, vertice 2 -> el menor
			System.out.println(grafo);

			System.out.println("\nIntentamos añadir arista entre vertice 1 y 2");
			grafo.agregarArista("2", "1", 50); // Intentamos sobrescribir
			System.out.println("ERROR" + grafo);
		} catch (NoExiste | YaExisteArista e) {
			System.out.println(e.getMessage());
		}
	}

	private static void pruebaInsercionVertice() {
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(5);
		System.out.println("\n----------PRUEBA AÑADIR VERTICE--------------");
		System.out.println("Añadimos vertice 1, 2, 3");
		agregarVertices(grafo);
		System.out.println(grafo);
		System.out.println("Intentamos añadir un vertice nulo");
		try{
			grafo.agregarVertice(null);
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}
	}

	private static void agregarVertices(GrafoGenerico<String, Integer> grafo) {
		try {
			grafo.agregarVertice("1");
			grafo.agregarVertice("2");
			grafo.agregarVertice("3");
		} catch (NoExiste e) {
			e.printStackTrace(); //ERROR
		}
	}
}
