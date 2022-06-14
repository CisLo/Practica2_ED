package datos;

import TADGrafoGenerico.GrafoGenerico;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

public class GrafoEstaciones { //TODO
	private GrafoGenerico <ZonaRecarga, Double> grafoEstaciones;
	private ZonaRecarga zonaRecargaInicial; // un vertice perteneciente al grafo por el cual se comienza a hacer un recorrido

	public GrafoEstaciones (int mida){
		grafoEstaciones = new GrafoGenerico<ZonaRecarga, Double>(mida);
		zonaRecargaInicial = null; // Inicializamos el vertice inicial de recorridos
	}

	public GrafoEstaciones (LinkedList<ZonaRecarga> listaZonasRecarga){
		grafoEstaciones = new GrafoGenerico<ZonaRecarga, Double>(listaZonasRecarga.size()*2);
		LinkedList<ZonaRecarga> listaZonasGrafo = new LinkedList<>();

		for (ZonaRecarga zonaRecarga : listaZonasRecarga) { // añadimos todas las zonas al grafo
			try {
				addEstacion(zonaRecarga, listaZonasGrafo);
				if (zonaRecargaInicial == null) {
					zonaRecargaInicial = zonaRecarga; // El vertice inicial de recorridos se asigna al primer nodo del grafo
				}
			} catch (NoExiste e) {
				e.printStackTrace(); //ERROR zona de recarga es null
			}
		}
	}

	/**
	 * Función que añade una Zona de Recarga al grafo y lo une con todas las zonas a menos de 40 km, en caso de que no haya
	 * ninguna estacion a 40 km lo une a la estacion más cercana
	 * @param newEstacion - estacion que se quiere unir al grafo
	 * @param tablaVisitas
	 * @throws NoExiste - Zona de recarga pasada por parametro es nulo
	 */
	public void addEstacion (ZonaRecarga newEstacion, LinkedList<ZonaRecarga> listaZonasGrafo) throws NoExiste {


		// Añadimos el vertice
		grafoEstaciones.agregarVertice(newEstacion);

		if (zonaRecargaInicial != null) { // En caso de que no sea el primer nodo del grafo, añadimos aristas
			// Recorremos la lista de vertices del grafo
			ZonaRecarga estacionMasCercana = null;
			boolean conectado = false;
			double distancia;

			try {
				for (ZonaRecarga vertice : listaZonasGrafo) {
					distancia = vertice.distancia(newEstacion);
					if (distancia <= 40) { // Añadimos carreteras entre estaciones a menos de 40km
						grafoEstaciones.agregarArista(vertice, newEstacion, distancia);
						conectado = true;
					} else if (estacionMasCercana == null || distancia < newEstacion.distancia(estacionMasCercana)) {
						estacionMasCercana = vertice;
					}
				}

				// Añadimos el más cercano si no hay ninguno a 40km
				if (!conectado) {
					grafoEstaciones.agregarArista(newEstacion, estacionMasCercana, newEstacion.distancia(estacionMasCercana));
				}

			}catch (YaExisteArista e){
				e.printStackTrace(); //ERROR
			}
		}

		// Añadimos el nuevo vertice a la lista de estaciones que pertenecen al grafo
		listaZonasGrafo.add(newEstacion);
	}
}
