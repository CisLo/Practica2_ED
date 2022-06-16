package Juego_Pruebas;

import datos.GrafoEstaciones;
import datos.ZonaRecarga;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class PruebaZonaRecarga { //TODO MIRAR
	public static void main(String[] args) throws FileNotFoundException { //TODO controlar excepcion
		// Cargamos los datos de JSON al grafo
		LinkedList<ZonaRecarga> listaZonas = ZonaRecarga.leerJson("src/main/resources/icaen.json");
		//System.out.println(ZonaRecarga.zonasRepetidas(listaZonas));
		//System.out.println(ZonaRecarga.repes(listaZonas));


		// Añadimos las estaciones al grafo
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);

		System.out.println(grafoEstaciones);
	}
}
