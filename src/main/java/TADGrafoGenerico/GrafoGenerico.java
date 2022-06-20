package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.ElementoNoEncontrado;
import TAD_TablaHash_ListaGenerica.ListaGenerica;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import datos.ZonaRecarga;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

/**
 * Clase Grafo Generico, implementa TADGrafoGenerico, Proporciona una implementación para crear un grafo etiquetado y no dirigido.
 * @param <K> Tipo de Objeto que será la clave o identificador del vertice. Debe implementar Comparable
 * @param <V> Tipo de Objeto que será el valor del vertice
 * @param <A> Tipo de Objeto que será el valor o etiqueta de la arista
 */
public class GrafoGenerico<K extends Comparable<K>, V, A> implements TADGrafoGenerico<K, V, A> {

	TablaHashGenerica<K, Vertice<K, V, A>> tablaVertices;

	/**
	 * Constructor de la clase GrafoGenerico
	 * @param mida - longitud de la tabla hash de los vertices (posteriormente se redimensiona automáticamente)
	 */
	public GrafoGenerico(int mida){
		tablaVertices = new TablaHashGenerica<>(mida);
	}

	/**
	 * Función que agrega un vértice
	 * @param vertice - dato que se quiere añadir al grafo
	 * @throws NoExiste - El vértice pasado por parámetro es nulo
	 */
	public void agregarVertice(K clave, V vertice) throws NoExiste{
		if (vertice == null || clave == null){ // Se lanza excepcion si los parámetros son nulos
			throw new NoExiste("El vértice pasado por parámetro es nulo");
		}
		tablaVertices.insertar(clave, new Vertice<K, V, A>(clave, vertice));
	}



	public void agregarArista(K vertice1, K vertice2, A arista) throws NoExiste, YaExisteArista {
		try {
			// Comprobamos los vertices
			Vertice<K, V,A> nodoMenor = tablaVertices.obtener(vertice1);
			Vertice<K, V,A> nodoMayor = tablaVertices.obtener(vertice2);

			// Añadimos la arista siempre al vértice menor
			if (nodoMenor.compareTo(nodoMayor)>0){ // nodoMenor es mayor que el nodoMayor, entonces los intercambiamos
				nodoMenor = tablaVertices.obtener(vertice2);
				nodoMayor = tablaVertices.obtener(vertice1);
			}

			//Comprobamos si ya existe la arista
			if(existeArista(vertice1, vertice2)){
				throw new YaExisteArista("Ya existe una arista entre ambos vertices");
			}

			// Creamos la arista y la unimos
			Arista<K, V, A> nodoArista = new Arista<K, V, A>(arista);
			nodoArista.setReferVerticeHorizontal(nodoMenor); // Lo unimos al vertice menor
			nodoArista.setReferVerticeVertical(nodoMayor); // Lo unimos al vertice mayor

			// Apuntamos a las siguientes aristas
			nodoArista.setNodoHorizontal(nodoMenor.getPunteroAristaHorizontal());
			nodoArista.setNodoVertical(nodoMayor.getPunteroAristaVertical());

			// Modificamos los punteros de los nodos vertice
			nodoMenor.setNodoHorizontal(nodoArista);
			nodoMayor.setNodoVertical(nodoArista);

		} catch (ClaveException e) {
			throw new NoExiste("No existe alguno de los vertices al añadir arista");
		}
	}


	public boolean existeArista(K vertice1, K vertice2) {
		boolean existe = true;
		try{ // Buscamos la arista en la multilista y si no la encuentra es que no existe
			buscarArista(vertice1, vertice2);
		} catch (NoExiste e) {
			existe = false;
		}
		return existe;
	}


	public A valorArista(K vertice1, K vertice2) throws NoExiste {
		return buscarArista(vertice1, vertice2).getDatos(); // Buscamos la arista en la multilista y devuelve el valor
	}

	private Arista<K, V, A> buscarArista(K vertice1, K vertice2) throws NoExiste{
		boolean existe = false;
		Arista<K, V, A> aristaHorizontal = null;

		try { // Obtenemos los vertices
			Vertice<K, V, A> nodoMenor = tablaVertices.obtener(vertice1);
			Vertice<K, V, A> nodoMayor = tablaVertices.obtener(vertice2);

			// Identificamos el nodoMenor y nodoMayor
			if (nodoMenor.compareTo(nodoMayor)>0){ // nodoMenor es mayor que el nodoMayor, entonces los intercambiamos
				nodoMenor = tablaVertices.obtener(vertice2);
				nodoMayor = tablaVertices.obtener(vertice1);
			}

			// El nodo menor será el que tenga la arista en su puntero Horizontal
			if (nodoMenor.hasNextHorizontal()) {
				aristaHorizontal = nodoMenor.getPunteroAristaHorizontal();
				existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoMayor) == 0;

				// Se busca en la lista de aristas horizontales el nodo, si existe se vuelve true se retorna se ha encontrado si no se lanza excepcion
				while (!existe && aristaHorizontal.hasNextHorizontal()){
					aristaHorizontal = aristaHorizontal.getPunteroAristaHorizontal(); // Guardamos la siguiente arista
					existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoMayor) == 0; //Miramos si es la arista que buscamos
				}
			}

			if (!existe){ // En caso de que la arista exista devolvemos como es
				throw new NoExiste("No existe la arista entre los vertices "+vertice1+" <---> "+vertice2);
			}
		} catch (ClaveException e) {/* No existe alguno de los vertices*/
			throw new NoExiste("No existe alguno de los vertices");
		}
		return aristaHorizontal;
	}



	public LinkedList<V> adyacentes(K vertice) throws NoExiste {
		LinkedList<V> listaVertices = new LinkedList<>();

		try {// Obtenemos el vertice
			Vertice<K, V, A> nodoInicio = tablaVertices.obtener(vertice);

			// Guardamos las primeras aristas del vertice, de las cuales partiremos para hacer el recorrido de ambas listas
			Arista<K, V, A> aristaHorizontal = nodoInicio.getPunteroAristaHorizontal();
			Arista<K, V, A> aristaVertical = nodoInicio.getPunteroAristaVertical();

			// Cuando el vértice es mayor que el otro vértice al que está unido
			while (aristaVertical != null){
				listaVertices.add(aristaVertical.getReferVerticeHorizontal().getDatos());
				aristaVertical = aristaVertical.getPunteroAristaVertical();
			}

			// Cuando el vértice es menor que el otro vértice al que está unido
			while (aristaHorizontal != null){
				listaVertices.add(aristaHorizontal.getReferVerticeVertical().getDatos());
				aristaHorizontal = aristaHorizontal.getPunteroAristaHorizontal();
			}

		} catch (ClaveException  e) {// No existe alguno de los vertices
			throw new NoExiste("No existe el vertice " + vertice);
		}

		return listaVertices;
	}

	/**
	 * Obtener tamaño tabla hash de vertices
	 * @return la longitud de la tabla hash de vertices del grafo
	 */
	public int getMidaTablaVertices(){
		return tablaVertices.midaTabla();
	}

	/**
	 * Obtener Lista de Claves Vertices
	 * @return una lista con los identificadores de los vertices del grafo
	 */
	public ListaGenerica<K> getClavesVertices(){
		return tablaVertices.obtenerClaves();
	}

	/**
	 * Valor del vertice
	 * @param idVertice - identificador/clave del vertice
	 * @return valor del vertice que tiene ese id
	 * @throws ClaveException - en caso de que no exista el vertice en el grafo
	 */
	public V valorVertice(K idVertice) throws ClaveException, NullPointerException {
		return tablaVertices.obtener(idVertice).getDatos();
	}

	@Override
	public String toString() {
		return "GrafoGenerico{" +
				"tablaVertices=\n" + tablaVertices +
				'}';
	}
}
