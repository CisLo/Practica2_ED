package datos;

import TADGrafoGenerico.GrafoGenerico;
import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.ListaGenerica;
import TAD_TablaHash_ListaGenerica.PosicionIncorrectaException;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import javax.management.Query;
import java.util.LinkedList;
import java.util.Objects;
import java.util.PriorityQueue;

public class GrafoEstaciones { //TODO
	private GrafoGenerico <Integer, ZonaRecarga, Double> grafoEstaciones;
	private ZonaRecarga zonaRecargaInicial; // un vertice perteneciente al grafo por el cual se comienza a hacer un recorrido

	public GrafoEstaciones (int mida){
		grafoEstaciones = new GrafoGenerico<Integer, ZonaRecarga, Double>(mida);
		zonaRecargaInicial = null; // Inicializamos el vertice inicial de recorridos
	}

	public GrafoEstaciones (LinkedList<ZonaRecarga> listaZonasRecarga){
		grafoEstaciones = new GrafoGenerico<Integer, ZonaRecarga, Double>(listaZonasRecarga.size()*2);
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
	 * @throws NoExiste - Zona de recarga pasada por parametro es nulo
	 */
	public void addEstacion (ZonaRecarga newEstacion, LinkedList<ZonaRecarga> listaZonasGrafo) throws NoExiste {


		// Añadimos el vertice
		grafoEstaciones.agregarVertice(newEstacion.getId(), newEstacion);

		if (zonaRecargaInicial != null) { // En caso de que no sea el primer nodo del grafo, añadimos aristas
			// Recorremos la lista de vertices del grafo
			ZonaRecarga estacionMasCercana = null;
			boolean conectado = false;
			double distancia;

			try {
				for (ZonaRecarga vertice : listaZonasGrafo) {
					distancia = vertice.distancia(newEstacion);
					if (distancia <= 40) { // Añadimos carreteras entre estaciones a menos de 40km
						grafoEstaciones.agregarArista(vertice.getId(), newEstacion.getId(), distancia);
						conectado = true;
					} else if (estacionMasCercana == null || distancia < newEstacion.distancia(estacionMasCercana)) {
						estacionMasCercana = vertice;
					}
				}

				// Añadimos el más cercano si no hay ninguno a 40km
				if (!conectado && estacionMasCercana!=null) {
					grafoEstaciones.agregarArista(newEstacion.getId(), estacionMasCercana.getId(), newEstacion.distancia(estacionMasCercana));
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
	public LinkedList<String> caminoOptimo(String id_origen, String id_destino, int autonomia) throws NoExiste{
		Double peso;
		Integer idOrigen = Integer.parseInt(id_origen);
		ListaGenerica<Integer> listaZonas = grafoEstaciones.getClavesVertices(); //Lista de las claves

		// Creamos las tablas auxiliares de Dijkstra
		int mida = grafoEstaciones.getMidaTablaVertices();
		TablaHashGenerica<Integer, Boolean> tablaVisitas = new TablaHashGenerica<>(mida);
		TablaHashGenerica<Integer, Double> tablaCostes = new TablaHashGenerica<>(mida);
		TablaHashGenerica<Integer, Integer> tablaPredecesores =new TablaHashGenerica<>(mida);

		// Inicializamos las tablas auxiliares
		for (Integer idZona: listaZonas) {
			tablaVisitas.insertar(idZona, false); // Marcamos todas como no visitadas
			try {
				peso = grafoEstaciones.valorArista(idOrigen, idZona); // El coste será el de arista inicial al resto
			}catch (NoExiste e){
				peso = null; // El coste es infinito si no existe la arista
			}
			tablaCostes.insertar(idZona, peso); // Marcamos a todas como su coste correspondiente
			tablaPredecesores.insertar(idZona, null); // Marcamos a todas como que no tienen predecesor
		}

		// Empezamos por el nodo inicial
		tablaCostes.insertar(idOrigen, 0.0); // Coste de la arista inicial es 0
		tablaVisitas.insertar(idOrigen,true);

		// Bucle Dijkstra
		try{
			while(!isVisitadosTodos(listaZonas, tablaVisitas)) {
				Integer vertice = elegirVerticeMinimoCoste(listaZonas, tablaVisitas, tablaCostes);
				tablaVisitas.insertar(vertice, true); // Lo marcamos como visitados

				// Para cada adyacente al vertice comprobamos si mejora la distancia
				for (ZonaRecarga zona:grafoEstaciones.adyacentes(vertice)) {
					Double pesoActual = tablaCostes.obtener(zona.getId());
					Double pesoNuevo = tablaCostes.obtener(vertice) + grafoEstaciones.valorArista(vertice, zona.getId());

					if (pesoActual == null || pesoActual > pesoNuevo){
						tablaCostes.insertar(zona.getId(), pesoNuevo);
						tablaPredecesores.insertar(zona.getId(), vertice);
					}
				}

			}
		} catch (ClaveException e) {
			e.printStackTrace();
			throw new NoExiste(""); //TODO QUitar
		}

		// Generamos la ruta
		LinkedList<String> ruta = new LinkedList<>();
		Integer nodo = Integer.parseInt(id_destino);

		try { // Obtenemos el nombre del enchufe con mayor potencia de la zona de recarga
			ruta.add(grafoEstaciones.valorVertice(nodo).getEnchufeMasPotencia().getNom());
			while (!Objects.equals(nodo, idOrigen)){ //Comparamos valores
				nodo = tablaPredecesores.obtener(nodo);
				ruta.add(grafoEstaciones.valorVertice(nodo).getEnchufeMasPotencia().getNom());
			}
		} catch (ClaveException e) {
			e.printStackTrace();
		}

		return ruta;
	}

	private Integer elegirVerticeMinimoCoste(ListaGenerica<Integer> listaZonas, TablaHashGenerica<Integer, Boolean> tablaVisitas, TablaHashGenerica<Integer, Double> tablaCostes) {
		// Inicializamos bucle
		int index = 0;
		Integer idZona;
		Integer verticeElegido = null;
		Double coste, costeVerticeElegido = null;
		try { // Buscamos el vertice con menor coste y que no este visitado
			while (index < listaZonas.longitud()) {
				idZona = listaZonas.obtener(index);
				if (!tablaVisitas.obtener(idZona)) { // Vertice no visitado
					coste = tablaCostes.obtener(idZona);
					if(verticeElegido == null || coste != null && (costeVerticeElegido==null || coste < costeVerticeElegido)){ // Vertice con menor coste
						verticeElegido = idZona; //Entonces se elige el vertice
						costeVerticeElegido = tablaCostes.obtener(verticeElegido);
					}
				}
				index++;
			}
		} catch (ClaveException | PosicionIncorrectaException e) {
			e.printStackTrace();
		}
		if (verticeElegido == null){
			throw new NullPointerException("No se ha podido escoger un vertice");
		}
		return  verticeElegido;
	}

	private boolean isVisitadosTodos(ListaGenerica<Integer> listaZonas, TablaHashGenerica<Integer, Boolean> tablaVisitas) {
		// Inicializamos bucle
		boolean visitadosTodos;
		visitadosTodos = true;
		int index = 0;
		Integer idZona;
		try { // Recorremos todos los vertices del grafo y comprobamos si estan visitados en la tabla de visitas
			while (visitadosTodos && index < listaZonas.longitud()) {
				idZona = listaZonas.obtener(index);
				if (!tablaVisitas.obtener(idZona)) {
					visitadosTodos = false;
				}
				index++;
			}
		} catch (ClaveException | PosicionIncorrectaException e) {
			e.printStackTrace();
		}
		return visitadosTodos;
	}

	/**
	 * Zonas con Distancia Máxima No Garantizada
	 * @param id_origen estacion de recarga inicial
	 * @param autonomia distancia máxima del coche que puede recorrer sin recargar
	 * @return lista que contiene aquellas zonas de recarga que no cumplen la condicion de estar enlazadas con almentos otra zona de recarga a una distancia maxima determinada por la autonomia
	 * @throws NoExiste no se ha podido crear la lista
	 */
	public LinkedList<String> zonasDistMaxNoGarantizada(String id_origen, int autonomia) throws NoExiste{
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