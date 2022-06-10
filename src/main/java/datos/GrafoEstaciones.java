package datos;

import TADGrafoGenerico.GrafoGenerico;
import excepciones.NoExiste;

import java.util.LinkedList;

public class GrafoEstaciones { //TODO
	private GrafoGenerico <ZonaRecarga, Integer> grafoEstaciones;

	public GrafoEstaciones (int mida){
		grafoEstaciones = new GrafoGenerico<ZonaRecarga, Integer>(mida);
	}

	public GrafoEstaciones (LinkedList<ZonaRecarga> listaZonasRecarga){
		grafoEstaciones = new GrafoGenerico<ZonaRecarga, Integer>(listaZonasRecarga.size()*2);
		for (ZonaRecarga zonaRecarga : listaZonasRecarga) { // añadimos todas las zonas al grafo
			try {
				addEstacion(zonaRecarga);
			} catch (NoExiste e) {
				e.printStackTrace(); //ERROR zona de recarga es null
			}
		}
	}

	/**
	 * Función que añade una Zona de Recarga al grafo y lo une con todas las zonas a menos de 40 km, en caso de que no haya
	 * ninguna estacion a 40 km lo une a la estacion más cercana
	 * @param newEstacion - estacion que se quiere unir al grafo
	 * @param zonaGrafo - estacion que ya pertenece al grafo y del cual se comienza el recorrido
	 * @throws NoExiste - Zona de recarga pasada por parametro es nulo
	 */
	public void addEstacion (ZonaRecarga newEstacion) throws NoExiste {
		//TODO poner zona de inicio como parametro? o preparar GrafoGenerico para obtener una zona de recarga para hacer el recorrido?
		ZonaRecarga estacionMasCercana = null;

		// Añadimos el vertice
		grafoEstaciones.agregarVertice(newEstacion);


		//TODO
		/* Añadimos aristas a menos de 40km
		for (vertice:grafoEstaciones.adyacentes(grafoEstaciones.getVerticeDelGrafo())) {

		}*/


		// Añadimos el más cercano si no hay ninguno a 40km



	}
}
