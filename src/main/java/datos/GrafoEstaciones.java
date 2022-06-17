package datos;

import TADGrafoGenerico.GrafoGenerico;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.NoExiste;

import java.util.LinkedList;

public class GrafoEstaciones { //TODO
	private GrafoGenerico <Integer, ZonaRecarga, Integer> grafoEstaciones;
	private ZonaRecarga zonaRecargaInicial; // un vertice perteneciente al grafo por el cual se comienza a hacer un recorrido

	public GrafoEstaciones (int mida){
		grafoEstaciones = new GrafoGenerico<Integer, ZonaRecarga, Integer>(mida);
		zonaRecargaInicial = null; // Inicializamos el vertice inicial de recorridos
	}

	public GrafoEstaciones (LinkedList<ZonaRecarga> listaZonasRecarga){
		grafoEstaciones = new GrafoGenerico<Integer, ZonaRecarga, Integer>(listaZonasRecarga.size()*2);
		TablaHashGenerica<ZonaRecarga, Integer> tablaVisitas  = new TablaHashGenerica<>(grafoEstaciones.getMidaTablaVertices());

		for (ZonaRecarga zonaRecarga : listaZonasRecarga) { // añadimos todas las zonas al grafo
			try {
				addEstacion(zonaRecarga, tablaVisitas);
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
	public void addEstacion (ZonaRecarga newEstacion, TablaHashGenerica<ZonaRecarga, Integer> tablaVisitas) throws NoExiste {
		ZonaRecarga estacionMasCercana = null;

		// Añadimos el vertice
		grafoEstaciones.agregarVertice(newEstacion.getId(), newEstacion);

		if (zonaRecargaInicial != null) { // En caso de que no sea el primer nodo del grafo, añadimos aristas
			añadirCarreteras(tablaVisitas);
		}

		tablaVisitas.insertar(newEstacion, 0);
		// 0 --> no visitado
		// 1 --> visitado pero no terminado
		// 2 --> visitado y terminado

	}


	private void añadirCarreteras(TablaHashGenerica<ZonaRecarga, Integer> tablaVisitas) throws NoExiste {
		// Inicializamos la tabla de nodos visitados
		for (ZonaRecarga vertice : tablaVisitas) {

		}

		for (ZonaRecarga vertice:grafoEstaciones.adyacentes(zonaRecargaInicial)) {

		}

		// Añadimos carreteras entre estaciones a menos de 40km

		// Añadimos el más cercano si no hay ninguno a 40km

	}
}
