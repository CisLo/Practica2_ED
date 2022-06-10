package Juego_Pruebas;

import TADGrafoGenerico.GrafoGenerico;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

public class PruebaGrafoGenerico {
	public static void main(String[] args) {

		// Probamos constructor
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(6);

		// Probamos Inserción Vértice
		pruebaInsercionVertice();

		// Probamos función Añadir Arista
		pruebaAgregarArista();

		// Probamos función Existe Arista
		pruebaExisteArista();

		// Probamos función Valor Arista
		pruebaValorArista();

		// Probamos función Adyacentes
		pruebaAdyacentes();
	}

	private static void pruebaAdyacentes() {
		System.out.println("\n----------PRUEBA ADYACENTES--------------");
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(6);
		agregarVertices(grafo);
		agregarAristas(grafo);

		System.out.println("\nEstado Inicial Grafo:");
		System.out.println(grafo);

		try {
			System.out.println("\nVertices adyacentes a 1");
			System.out.println("\tLongitud Lista: " + grafo.adyacentes("1").size());
			System.out.println(grafo.adyacentes("1"));

			System.out.println("\nVertices adyacentes a 2");
			System.out.println("\tLongitud Lista: " + grafo.adyacentes("2").size());
			System.out.println(grafo.adyacentes("2"));

			System.out.println("\nVertices adyacentes a 3");
			System.out.println("\tLongitud Lista: " + grafo.adyacentes("3").size());
			System.out.println(grafo.adyacentes("3"));

			System.out.println("\nVertices adyacentes a 4");
			System.out.println("\tLongitud Lista: " + grafo.adyacentes("4").size());
			System.out.println(grafo.adyacentes("4"));

			System.out.println("\nIntentamos listar vertices adyacentes a 5");
			System.out.println("\tLongitud Lista: " + grafo.adyacentes("5").size());
			System.out.println(grafo.adyacentes("5"));
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}
	}

	private static void pruebaValorArista() {
		System.out.println("\n----------PRUEBA VALOR ARISTA--------------");
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(6);
		agregarVertices(grafo);
		agregarAristas(grafo);

		System.out.println("\nEstado Inicial Grafo:");
		System.out.println(grafo);

		try {
			System.out.println("\nArista 1 <--> 2");
			System.out.println(grafo.valorArista("1", "2"));

			System.out.println("\nArista 2 <--> 3");
			System.out.println(grafo.valorArista("3", "2"));

			System.out.println("\nArista 2 <--> 4");
			System.out.println(grafo.valorArista("2", "4"));

		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}
	}


	private static void agregarVertices(GrafoGenerico<String, Integer> grafo) {
		try {
			grafo.agregarVertice("1");
			grafo.agregarVertice("2");
			grafo.agregarVertice("3");
			grafo.agregarVertice("4");
		} catch (NoExiste e) {
			e.printStackTrace(); //ERROR
		}
	}

	private static void agregarAristas(GrafoGenerico<String, Integer> grafo) {
		try {
			grafo.agregarArista("1", "2", 100);
			grafo.agregarArista("3", "2", 75);
			grafo.agregarArista("1", "3", 90);
		} catch (NoExiste | YaExisteArista e) {
			e.printStackTrace();
		}
	}

	private static void pruebaAgregarArista() {
		System.out.println("\n----------PRUEBA AÑADIR ARISTA--------------");
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(6);
		agregarVertices(grafo);
		try {
			System.out.println("\nAñadimos arista entre vertice 1 y 2");
			grafo.agregarArista("1", "2", 100);
			System.out.println(grafo);

			System.out.println("\nIntentamos añadir vertice entre 3 y 2");
			grafo.agregarArista("3", "2", 75); // Añadimos arista, vertice 3 -> es el mayor, vertice 2 -> el menor
			System.out.println(grafo);

			System.out.println("\nAñadimos arista a los vertices 1 y 3");
			grafo.agregarArista("1", "3", 90); // Añadimos arista, vertice 1 -> es el mayor, vertice 3 -> el menor
			System.out.println(grafo);

			System.out.println("\nIntentamos añadir arista entre vertice 1 y 2");
			grafo.agregarArista("2", "1", 50); // Intentamos sobrescribir
			System.out.println("ERROR" + grafo);
		} catch (NoExiste | YaExisteArista e) {
			System.out.println(e.getMessage());
		}
	}

	private static void pruebaExisteArista() {
		System.out.println("\n----------PRUEBA EXISTE ARISTA--------------");
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(6);
		agregarVertices(grafo);
		agregarAristas(grafo);

		System.out.println("\nEstado Inicial Grafo:");
		System.out.println(grafo);

		System.out.println("\nComprobamos si existe arista entre 1 <--> 3");
		System.out.println("1-->3 "+grafo.existeArista("1", "3"));
		System.out.println("3-->1 "+grafo.existeArista("3", "1"));

		System.out.println("\nComprobamos si existe arista entre 1 <--> 2");
		System.out.println("1-->2 "+grafo.existeArista("1", "2"));
		System.out.println("2-->1 "+grafo.existeArista("2", "1"));

		System.out.println("\nComprobamos si existe arista entre 1 <--> 1");
		System.out.println(grafo.existeArista("1", "1"));

		System.out.println("\nComprobamos si existe arista entre 1 <--> 4");
		System.out.println("1-->4 "+grafo.existeArista("1", "4"));
		System.out.println("4-->1 "+grafo.existeArista("4", "1"));
	}

	private static void pruebaInsercionVertice() {
		GrafoGenerico<String, Integer> grafo = new GrafoGenerico<String, Integer>(6);
		System.out.println("\n----------PRUEBA AÑADIR VERTICE--------------");
		System.out.println("Añadimos vertice 1, 2, 3 y 4");
		agregarVertices(grafo);
		System.out.println(grafo);
		System.out.println("Intentamos añadir un vertice nulo");
		try{
			grafo.agregarVertice(null);
		} catch (NoExiste e) {
			System.out.println(e.getMessage());
		}
	}


}
