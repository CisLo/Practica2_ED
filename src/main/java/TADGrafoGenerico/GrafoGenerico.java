package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.ErrorCrearLista;
import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

public class GrafoGenerico<V extends Comparable<V>, A> implements TADGrafoGenerico<V, A> {

	TablaHashGenerica<V, Vertice<V,A>> tablaVertices;

	/**
	 * Constructor de la clase GrafoGenerico
	 * @param mida - longitud de la tabla hash de los vertices (posteriormente se redimensiona automaticamente)
	 */
	public GrafoGenerico(int mida){
		tablaVertices = new TablaHashGenerica<>(mida);
	}

	/**
	 * Función que agrega un vertice
	 * @param vertice -
	 * @throws NoExiste
	 */
	public void agregarVertice(V vertice) throws NoExiste{ // TODO la clave no es el vertice
		if (vertice == null){
			throw new NoExiste("El vertice no existe, no se puede agregar al grafo");
		}
		tablaVertices.insertar(vertice, new Vertice<V,A>(vertice));
	}

	@Override
	public void agregarArista(V vertice1, V vertice2, A arista) throws NoExiste, YaExisteArista {
		try {
			// Comprobamos los vertices
			Vertice<V,A> nodoMenor = tablaVertices.obtener(vertice1);
			Vertice<V,A> nodoMayor = tablaVertices.obtener(vertice2);

			// Añadimos la arista siempre al vertice menor
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
		boolean existe = false;
		try {
			Vertice<V,A> nodoInicio = tablaVertices.obtener(vertice1);
			Vertice<V,A> nodoFinal = tablaVertices.obtener(vertice2);

			if (nodoInicio.hasNextHorizontal()) {
				Arista<V, A> aristaHorizontal = nodoInicio.getPunteroAristaHorizontal();
				existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoFinal) == 0;

				while (!existe && aristaHorizontal.hasNextHorizontal()){
					aristaHorizontal = aristaHorizontal.getPunteroAristaHorizontal();
					existe = aristaHorizontal.getReferVerticeVertical().compareTo(nodoFinal) == 0;
				}
			}
			if (!existe && nodoInicio.hasNextVertical()){
				Arista<V, A> aristaVertical = nodoInicio.getPunteroAristaVertical();
				existe = aristaVertical.getReferVerticeHorizontal().compareTo(nodoFinal) == 0;

				while (!existe && aristaVertical.hasNextVertical()){
					aristaVertical = aristaVertical.getPunteroAristaVertical();
					existe = aristaVertical.getReferVerticeHorizontal().compareTo(nodoFinal) == 0;
				}
			}
		} catch (ClaveException e) {/* No existe alguno de los vertices*/}
		return existe;
	}

	@Override
	public A valorArista(V vertice1, V vertice2) throws NoExiste {
		return null;
	}

	private Arista<V, A> buscarArista(V vertice1, V vertice2) throws NoExiste{
		boolean existe = false;
		Arista<V, A> aristaEncontrada = null;
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

			if (existe){ // En caso de que la arista exista devolvemos cual es
				aristaEncontrada = aristaHorizontal == null? aristaVertical: aristaHorizontal;
			} else{
				throw new NoExiste("No existe la arista entre los vertices"+vertice1+"<--->"+vertice2);
			}
		} catch (ClaveException e) {/* No existe alguno de los vertices*/
			throw new NoExiste("No existe alguno de los vertices");
		}
		return aristaEncontrada;
	}

	@Override
	public LinkedList<V> adyacentes(V vertice) throws ErrorCrearLista {
		return null;
	}

	@Override
	public String toString() {
		return "GrafoGenerico{" +
				"tablaVertices=\n" + tablaVertices +
				'}';
	}
}
