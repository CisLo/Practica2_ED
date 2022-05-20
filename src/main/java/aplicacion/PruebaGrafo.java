package aplicacion;

import TADGrafoGenerico.GrafoGenerico;
import excepciones.NoExiste;

public class PruebaGrafo {
	public static void main(String[] args) {

		// Probamos constructor
		GrafoGenerico<String, Integer> algo = new GrafoGenerico<String, Integer>(10);

		// Probamos Insercion
		try {
			algo.agregarVertice("1");
			algo.agregarVertice("2");
		} catch (NoExiste e) {
			e.printStackTrace();
		}

	}
}
