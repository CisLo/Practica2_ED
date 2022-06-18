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
		//System.out.println(ZonaRecarga.zonasRepetidas(listaZonas));
		//System.out.println(ZonaRecarga.repes(listaZonas));


		// Añadimos las estaciones al grafo
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);
		//System.out.println(grafoEstaciones);

		try {
			LinkedList<String> ruta = grafoEstaciones.caminoOptimo("9165", "9168", 100);
			System.out.println(ruta);

			ruta = grafoEstaciones.caminoOptimo("9165", "34252288", 20);
			System.out.println(ruta);

			ruta = grafoEstaciones.caminoOptimo("9165", "34252288", 50);
			System.out.println(ruta);

			ruta = grafoEstaciones.caminoOptimo("13361299", "34252288", 30);
			System.out.println(ruta);

			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", 30);
			System.out.println(ruta);

			ruta = grafoEstaciones.caminoOptimo("13361299", "7562018", -5);
			System.out.println(ruta); // ERROR

			ruta = grafoEstaciones.caminoOptimo("13361299", "1", 30);
			System.out.println(ruta); // ERROR
		} catch (NoExiste e) {
			System.out.println(e.getMessage());;
		}
	}


}