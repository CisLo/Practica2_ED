package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

public class GrafoGenerico<K extends Comparable<K>, V, A> implements TADGrafoGenerico<V, A> {

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
	public void agregarVertice(K clave, V vertice) throws NoExiste{ // TODO la clave no es el vertice
		if (vertice == null || clave == null){
			throw new NoExiste("El vértice pasado por parámetro es nulo");
		}
		tablaVertices.insertar(clave, new Vertice<K, V, A>(clave, vertice));
	}

	@Override
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
	public boolean existeArista(K vertice1, K vertice2) {
		boolean existe = true;
		try{
			buscarArista(vertice1, vertice2);
		} catch (NoExiste e) {
			existe = false;
		}
		return existe;
	}

	@Override
	public A valorArista(K vertice1, K vertice2) throws NoExiste {
		return buscarArista(vertice1, vertice2).getDatos();
	}

	private Arista<K, V, A> buscarArista(K vertice1, K vertice2) throws NoExiste{
		boolean existe = false;
		Arista<K, V, A> aristaHorizontal = null;

		try {
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


	@Override
	public LinkedList<V> adyacentes(K vertice) throws NoExiste {
		LinkedList<V> listaVertices = new LinkedList<>();

		try {
			Vertice<K, V,A> nodoInicio = tablaVertices.obtener(vertice);

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

	public int getMidaTablaVertices(){
		return tablaVertices.midaTabla();
	}

	@Override
	public String toString() {
		return "GrafoGenerico{" +
				"tablaVertices=\n" + tablaVertices +
				'}';
	}
}
