package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

public class GrafoGenerico<V extends Comparable<V>, A> implements TADGrafoGenerico<V, A> {

	TablaHashGenerica<V, Vertice<V,A>> tablaVertices;
	//TODO V verticeDelGrafo; // puntero a un vertice del grafo, lo usaremos para empezar a recorrer el grafo por una pa

	/**
	 * Constructor de la clase GrafoGenerico
	 * @param mida - longitud de la tabla hash de los vertices (posteriormente se redimensiona automáticamente)
	 */
	public GrafoGenerico(int mida){
		tablaVertices = new TablaHashGenerica<>(mida);
		//TODO verticeDelGrafo = null;
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
	}

	@Override //TODO la arista se puede sobrescribir
	public void agregarArista(V vertice1, V vertice2, A arista) throws NoExiste, YaExisteArista {
		try {
			// Comprobamos los vertices
			Vertice<V,A> nodoMenor = tablaVertices.obtener(vertice1);
			Vertice<V,A> nodoMayor = tablaVertices.obtener(vertice2);

			// Añadimos la arista siempre al vértice menor
			// TODO nodoMenor = tablaVertices.obtener(vertice1).compareTo(tablaVertices.obtener(vertice2)) < 0? tablaVertices.obtener(vertice1): tablaVertices.obtener(vertice2);
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
		Arista<V, A> aristaEncontrada;
		try {
			Vertice<V,A> nodoInicio = tablaVertices.obtener(vertice1);
			Vertice<V,A> nodoFinal = tablaVertices.obtener(vertice2);

			Arista<V, A> aristaHorizontal = null;
			Arista<V, A> aristaVertical = null;

			if (nodoInicio.hasNextHorizontal()) {
				aristaHorizontal = nodoInicio.getPunteroAristaHorizontal();
				existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoFinal) == 0;

				while (!existe && aristaHorizontal.hasNextHorizontal()){
					aristaHorizontal = aristaHorizontal.getPunteroAristaHorizontal();
					existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoFinal) == 0;
				}
			}
			if (!existe && nodoInicio.hasNextVertical()){
				aristaVertical = nodoInicio.getPunteroAristaVertical();
				existe = aristaVertical.getReferVerticeHorizontal().compareTo(nodoFinal) == 0;

				while (!existe && aristaVertical.hasNextVertical()){
					aristaVertical = aristaVertical.getPunteroAristaVertical();
					existe = aristaVertical.getReferVerticeHorizontal().compareTo(nodoFinal) == 0;
				}
			}

			if (existe){ // En caso de que la arista exista devolvemos como es
				aristaEncontrada = aristaHorizontal == null? aristaVertical: aristaHorizontal;
			} else{
				throw new NoExiste("No existe la arista entre los vertices "+vertice1+" <---> "+vertice2);
			}
		} catch (ClaveException e) {/* No existe alguno de los vertices*/
			throw new NoExiste("No existe alguno de los vertices");
		}
		return aristaEncontrada;
	}

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
