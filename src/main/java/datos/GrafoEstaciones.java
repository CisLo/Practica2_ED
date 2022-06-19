package datos;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Stack;

import TADGrafoGenerico.GrafoGenerico;
import TAD_TablaHash_ListaGenerica.*;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import javax.management.Query;

public class GrafoEstaciones { //TODO
	private GrafoGenerico <Integer, ZonaRecarga, Double> grafoEstaciones;
	private ZonaRecarga zonaRecargaInicial; // un vertice perteneciente al grafo por el cual se comienza a hacer un recorrido

	public GrafoEstaciones (LinkedList<ZonaRecarga> listaZonasRecarga){
		grafoEstaciones = new GrafoGenerico<Integer, ZonaRecarga, Double>(listaZonasRecarga.size()*2);

		// Añadimos los vertices
		for (ZonaRecarga zonaRecarga : listaZonasRecarga) {
			try {
				grafoEstaciones.agregarVertice(zonaRecarga.getId(), zonaRecarga);
				if (zonaRecargaInicial == null) {
					zonaRecargaInicial = zonaRecarga; // El vertice inicial de recorridos se asigna al primer nodo del grafo
				}
			} catch (NoExiste e) {
				e.printStackTrace(); //ERROR zona de recarga es null
			}
		}
		// Añadimos las aristas tras añadir los vertices
		for (ZonaRecarga zonaRecarga : listaZonasRecarga) {
			try {
				addCarreteras(zonaRecarga, grafoEstaciones, listaZonasRecarga);
			} catch (NoExiste e) {
				e.printStackTrace();
			}
		}
	}

	private void addCarreteras(ZonaRecarga newEstacion, GrafoGenerico<Integer, ZonaRecarga, Double> grafoEstaciones, LinkedList<ZonaRecarga> listaZonasGrafo) throws NoExiste{
		if (zonaRecargaInicial != null) { // En caso de que no sea el primer nodo del grafo, añadimos aristas
			// Recorremos la lista de vertices del grafo
			ZonaRecarga estacionMasCercana = null;
			boolean conectado = false;
			double distancia;

			//TODO puedo hacer esto tambien grafoEstaciones.getClavesVertices();
			try {
				for (ZonaRecarga vertice : listaZonasGrafo) {
					if (vertice != newEstacion) {
						distancia = vertice.distancia(newEstacion);
						if (distancia <= 40) { // Añadimos carreteras entre estaciones a menos de 40km
							grafoEstaciones.agregarArista(vertice.getId(), newEstacion.getId(), distancia);
							conectado = true;
						} else if (estacionMasCercana == null || distancia < newEstacion.distancia(estacionMasCercana)) {
							estacionMasCercana = vertice;
						}
					}
				}

				// Añadimos el más cercano si no hay ninguno a 40km
				if (!conectado && estacionMasCercana!=null) {
					grafoEstaciones.agregarArista(newEstacion.getId(), estacionMasCercana.getId(), newEstacion.distancia(estacionMasCercana));
				}

			}catch (YaExisteArista e){
				// No hacer nada
			}
		}
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
		// Comprobamos parametros validos
		if(id_origen == null || id_destino == null || autonomia <= 0) {
			throw new NoExiste("Parametros no validos introducidos en funcion: camino optimo");
		}

		// Obtenemos los vertices del grafo
		Double peso;
		Integer destino = Integer.parseInt(id_destino);
		Integer origen = Integer.parseInt(id_origen);
		ListaGenerica<Integer> listaZonas = grafoEstaciones.getClavesVertices(); //Lista de las claves

		// Creamos las tablas auxiliares de Dijkstra
		int mida = grafoEstaciones.getMidaTablaVertices();
		TablaHashGenerica<Integer, Boolean> tablaVisitas = new TablaHashGenerica<>(mida);
		TablaHashGenerica<Integer, Double> tablaCostes = new TablaHashGenerica<>(mida);
		TablaHashGenerica<Integer, Integer> tablaPredecesores =new TablaHashGenerica<>(mida);

		// Inicializamos las tablas auxiliares
		for (Integer idZona: listaZonas) {
			tablaVisitas.insertar(idZona, false); // Marcamos todas como no visitadas
			tablaCostes.insertar(idZona, null); // Marcamos a todas como su coste correspondiente
			tablaPredecesores.insertar(idZona, null); // Marcamos a todas como que no tienen predecesor
		}

		// Comprobamos la existencia del nodo de origen y el nodo de destino en el grafo
		try {
			tablaVisitas.buscar(origen);
			tablaVisitas.buscar(destino);
		} catch (ElementoNoEncontrado e) {
			throw new NoExiste("El nodo de origen o de destino no se encuentra en el grafo");
		}

		// Empezamos por el nodo inicial
		Integer vertice = origen;
		tablaCostes.insertar(vertice, 0.0); // Coste de la arista inicial es 0


		try{ // Bucle Dijkstra
			while(!destino.equals(vertice) && !isVisitadosTodos(listaZonas, tablaVisitas)) {
				// Lo marcamos como visitados
				tablaVisitas.insertar(vertice, true);

				// Para cada adyacente al vertice comprobamos si mejora la distancia
				for (ZonaRecarga zona:grafoEstaciones.adyacentes(vertice)) {
					Double pesoActual = tablaCostes.obtener(zona.getId());
					Double costeArista = grafoEstaciones.valorArista(vertice, zona.getId());
					if (costeArista <= autonomia) { // Descartamos las aristas por las que no puede pasar el coche
						Double pesoNuevo = tablaCostes.obtener(vertice) + costeArista;

						if (pesoActual == null || pesoActual > pesoNuevo) { // Si mejora el coste, actualizamos el coste y predecesor
							tablaCostes.insertar(zona.getId(), pesoNuevo);
							tablaPredecesores.insertar(zona.getId(), vertice);
						}
					}
				}
				// Elegimos el siguiente vertice, y si
				vertice = elegirVerticeMinimoCoste(listaZonas, tablaVisitas, tablaCostes);
			}
		} catch (ClaveException e) {
			e.printStackTrace(); // Error
		}

		if (!destino.equals(vertice)){
			throw new NoExiste("No se ha podido alcanzar el destino con un coche con esa autonomia");
		}

		// Generamos la ruta
		LinkedList<String> ruta = new LinkedList<>();


		try { // Obtenemos el nombre del enchufe con mayor potencia de la zona de recarga
			ruta.add(grafoEstaciones.valorVertice(destino).getEnchufeMasPotencia().getNom());
			while (!Objects.equals(destino, origen)){ //Comparamos valores
				destino = tablaPredecesores.obtener(destino);
				ruta.add(0, grafoEstaciones.valorVertice(destino).getEnchufeMasPotencia().getNom());

			}
		} catch (ClaveException e) {
			e.printStackTrace();
		}

		return ruta;
	}

	private Integer elegirVerticeMinimoCoste(ListaGenerica<Integer> listaZonas, TablaHashGenerica<Integer, Boolean> tablaVisitas, TablaHashGenerica<Integer, Double> tablaCostes) throws NoExiste {
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
			if (tablaCostes.obtener(verticeElegido) == null){
				throw new NoExiste("No se puede alcanzar el destino");
			}
		} catch (ClaveException | PosicionIncorrectaException e) {
			e.printStackTrace();
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
		// Comprobamos parametros validos
		if(id_origen == null || autonomia <= 0) {
			throw new NoExiste("Parametros no validos introducidos en funcion: distancia maxima no garantizada");
		}

		// Creamos variables y usamos una pila y una tabla hash como estructura auxiliar del recorrido en anchura
		Integer origen = Integer.parseInt(id_origen);
		Stack<Integer> pilaZonasAdyacentes = new Stack<>();
		TablaHashGenerica<Integer, Boolean> tablaVisitas = new TablaHashGenerica<>(grafoEstaciones.getMidaTablaVertices());

		// Inicializamos las estructuras auxiliares
		ListaGenerica<Integer> listaZonas = grafoEstaciones.getClavesVertices();
		for(Integer vertice: listaZonas){
			tablaVisitas.insertar(vertice, false);
		}
		pilaZonasAdyacentes.add(origen);

		// Bucle de exploracion en anchura
		Integer zona;
		while (!pilaZonasAdyacentes.isEmpty()){
			// Extraemos una zona de la pila
			zona = pilaZonasAdyacentes.pop();
			tablaVisitas.insertar(zona, true);

			for (ZonaRecarga adyacente : grafoEstaciones.adyacentes(zona)){
				try {// Si el adyacente no esta visitado y su arista es menor que la autonomia se añade a la pila
					if (!tablaVisitas.obtener(adyacente.getId()) && grafoEstaciones.valorArista(zona, adyacente.getId()) <= autonomia){
						pilaZonasAdyacentes.add(adyacente.getId());
						System.out.println(adyacente.getId()+" añadido");
					}
				} catch (ClaveException e) {
					e.printStackTrace();
				}
			}

		}



		return null; //TODO
	}

	//TODO quitar
	public LinkedList<ZonaRecarga> pruebaAdyacentes(Integer idZona) throws NoExiste {
		return grafoEstaciones.adyacentes(idZona);
	}

	@Override
	public String toString() {
		return "GrafoEstaciones{" +
				"grafoEstaciones=" + grafoEstaciones +
				'}';
	}
}