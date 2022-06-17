package datos;

import TADGrafoGenerico.GrafoGenerico;
import TAD_TablaHash_ListaGenerica.ListaGenerica;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import javax.management.Query;
import java.util.LinkedList;
import java.util.PriorityQueue;

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

	/**
	 * Camino Optimo
	 * @param id_origen estacion de recarga inicial
	 * @param id_destino estacion de recarga final
	 * @param autonomia distancia máxima del coche sin recargar
	 * @return una lista que contiene todos los nombres de los puntos de carga por los que hay que pasar para llegar al destino
	 * @exception NoExiste no se ha podido crear la lista de camino optimo
	 */
	LinkedList<String> caminoOptimo(String id_origen, String id_destino, int autonomia) throws NoExiste{
		// Creamos las tablas auxiliares de Dijkstra
		int mida = grafoEstaciones.getMidaTablaVertices();
		TablaHashGenerica<Integer, Boolean> tablaVisitas = new TablaHashGenerica<>(mida);
		TablaHashGenerica<Integer, Double> tablaCostes = new TablaHashGenerica<>(mida);
		TablaHashGenerica<Integer, ZonaRecarga> tablaPredecesores =new TablaHashGenerica<>(mida);

		ListaGenerica<ZonaRecarga> listaZonas = grafoEstaciones.getClavesVertices();

		// Inicializamos las tablas auxiliares
		for (ZonaRecarga zona: listaZonas) {
			tablaVisitas.insertar(zona.getId(), false); // Marcamos todas como no visitadas
			double peso = grafoEstaciones.valorArista()
			tablaCostes.insertar(zona.getId(), null); // Marcamos a todas como que el coste es infinito
			tablaPredecesores.insertar(zona.getId(), null); // Marcamos a todas como que no tienen predecesor
		}
		tablaCostes.insertar(Integer.parseInt(id_origen), 0.0);



		return null; //TODO
	}

	/**
	 * Zonas con Distancia Máxima No Garantizada
	 * @param id_origen estacion de recarga inicial
	 * @param autonomia distancia máxima del coche que puede recorrer sin recargar
	 * @return lista que contiene aquellas zonas de recarga que no cumplen la condicion de estar enlazadas con almentos otra zona de recarga a una distancia maxima determinada por la autonomia
	 * @throws NoExiste no se ha podido crear la lista
	 */
	LinkedList<String> zonasDistMaxNoGarantizada(String id_origen, int autonomia) throws NoExiste{
		return null; //TODO
	}

	@Override
	public String toString() {
		return "GrafoEstaciones{" +
				"grafoEstaciones=" + grafoEstaciones +
				", zonaRecargaInicial=" + zonaRecargaInicial +
				'}';
	}
}
