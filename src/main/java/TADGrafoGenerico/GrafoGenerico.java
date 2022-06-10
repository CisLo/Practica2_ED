package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

public class GrafoGenerico<V extends Comparable<V>, A> implements TADGrafoGenerico<V, A> {

	TablaHashGenerica<V, Vertice<V,A>> tablaVertices;
	//V verticeDelGrafo; // TODO puntero a un vertice del grafo, lo usaremos para empezar a recorrer el grafo por una pa

	/**
	 * Constructor de la clase GrafoGenerico
	 * @param mida - longitud de la tabla hash de los vertices (posteriormente se redimensiona automáticamente)
	 */
	public GrafoGenerico(int mida){
		tablaVertices = new TablaHashGenerica<>(mida);
		//verticeDelGrafo = null; //TODO
	}

	/**
	 * Función que agrega un vértice
	 * @param vertice - dato que se quiere añadir al grafo
	 * @throws NoExiste - El vértice pasado por parámetro es nulo
	 */
	public void agregarVertice(V vertice) throws NoExiste{ // TODO la clave no es el vertice
		if (vertice == null){
			throw new NoExiste("El vértice pasado por parámetro es nulo");
		}
		tablaVertices.insertar(vertice, new Vertice<V,A>(vertice));
		/*if(verticeDelGrafo == null){ // Actualizamos el puntero a un vertice aleatorio del grafo para hacer recorridos
			verticeDelGrafo = vertice; //TODO apuntamos el vertice añadido para comenzar el recorrido del grafo
		}*/
	}

	@Override //TODO la arista se puede sobrescribir
	public void agregarArista(V vertice1, V vertice2, A arista) throws NoExiste, YaExisteArista {
		try {
			// Comprobamos los vertices
			Vertice<V,A> nodoMenor = tablaVertices.obtener(vertice1);
			Vertice<V,A> nodoMayor = tablaVertices.obtener(vertice2);

			// Añadimos la arista siempre al vértice menor
			if (nodoMenor.compareTo(nodoMayor)>0){ // nodoMenor es mayor que el nodoMayor, entonces los intercambiamos
				nodoMenor = tablaVertices.obtener(vertice2);
				nodoMayor = tablaVertices.obtener(vertice1);
			}

			//TODO hay que sobrescribir si ya existe la arista?
			//Comprobamos la arista
			if(existeArista(vertice1, vertice2)){
				throw new YaExisteArista("Ya existe una arista entre ambos vertices");
			}

			// Creamos la arista y la unimos
			Arista<V, A> nodoArista = new Arista<V, A>(arista);
			nodoArista.setReferVerticeHorizontal(nodoMenor); // Lo unimos al vertice menor
			nodoArista.setReferVerticeVertical(nodoMayor); // Lo unimos al vertice mayor
			//TODO unir ordenado??
			// esto es añadirlo al principio de ambos nodos de forma desordenada
			nodoArista.setNodoHorizontal(nodoMenor.getPunteroAristaHorizontal());
			nodoArista.setNodoVertical(nodoMayor.getPunteroAristaVertical());
			nodoMenor.setNodoHorizontal(nodoArista);
			nodoMayor.setNodoVertical(nodoArista);

		} catch (ClaveException e) {
			throw new NoExiste("No existe alguno de los vertices al añadir arista");
		}
	}

	@Override
	public boolean existeArista(V vertice1, V vertice2) {
		boolean existe = true;
		try{
			buscarArista(vertice1, vertice2);
		} catch (NoExiste e) {
			existe = false;
		}
		return existe;
	}

	@Override
	public A valorArista(V vertice1, V vertice2) throws NoExiste {
		return buscarArista(vertice1, vertice2).getDatos();
	}

	private Arista<V, A> buscarArista(V vertice1, V vertice2) throws NoExiste{
		boolean existe = false;
		Arista<V, A> aristaHorizontal = null;

		try {
			Vertice<V,A> nodoMenor = tablaVertices.obtener(vertice1);
			Vertice<V,A> nodoMayor = tablaVertices.obtener(vertice2);

			// Identificamos el nodoMenor y nodoMayor
			if (nodoMenor.compareTo(nodoMayor)>0){ // nodoMenor es mayor que el nodoMayor, entonces los intercambiamos
				nodoMenor = tablaVertices.obtener(vertice2);
				nodoMayor = tablaVertices.obtener(vertice1);
			}

			// El nodo menor será el que tenga la arista en su puntero Horizontal
			if (nodoMenor.hasNextHorizontal()) {
				aristaHorizontal = nodoMenor.getPunteroAristaHorizontal();
				existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoMayor) == 0;

				while (!existe && aristaHorizontal.hasNextHorizontal()){
					aristaHorizontal = aristaHorizontal.getPunteroAristaHorizontal();
					existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoMayor) == 0;
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

	//TODO
	/**
	 * Getter
	 * @return un vertice que pertenezca al grafo, null en caso de que no haya vertices en el grafo
	 */
	/*
	public V getVerticeDelGrafo() {
		return verticeDelGrafo;
	}*/

	@Override
	public LinkedList<V> adyacentes(V vertice) throws NoExiste {
		LinkedList<V> listaVertices = new LinkedList<>();

		try {
			Vertice<V,A> nodoInicio = tablaVertices.obtener(vertice);

			Arista<V, A> aristaHorizontal = nodoInicio.getPunteroAristaHorizontal();
			Arista<V, A> aristaVertical = nodoInicio.getPunteroAristaVertical();

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

	@Override
	public String toString() {
		return "GrafoGenerico{" +
				"tablaVertices=\n" + tablaVertices +
				'}';
	}
}
