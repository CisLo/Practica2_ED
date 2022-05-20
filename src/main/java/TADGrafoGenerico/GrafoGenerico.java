package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.ClaveException;
import TAD_TablaHash_ListaGenerica.ElementoNoEncontrado;
import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.ErrorCrearLista;
import excepciones.NoExiste;

import java.util.HashMap;
import java.util.LinkedList;

public class GrafoGenerico<V extends Comparable<V>, A> implements TADGrafoGenerico<V, A> {

	TablaHashGenerica<V, Vertice2<V,A>> tablaVertices;

	public GrafoGenerico(int mida){
		tablaVertices = new TablaHashGenerica<>(mida);
		System.out.println(tablaVertices);
	}

	/**
	 * Función que agrega un vertice
	 * @param vertice -
	 * @throws NoExiste
	 */
	public void agregarVertice(V vertice) throws NoExiste{ // TODO la clave no es el vertice
		if (vertice == null){
			throw new NoExiste();
		}
		tablaVertices.insertar(vertice, new Vertice2<V,A>(vertice));
		System.out.println(tablaVertices);
	}

	@Override
	public void agregarArista(V vertice1, V vertice2, A arista) throws NoExiste {
		// Comprobamos los vertices

		try {
			Vertice2<V,A> nodoInicio = tablaVertices.obtener(vertice1);
			Vertice2<V,A> nodoDestino = tablaVertices.obtener(vertice2);

			//TODO
		} catch (ClaveException e) {
			throw new NoExiste("No existe el vertice al añadir arista");
		}


	}

	@Override
	public boolean existeArista(V vertice1, V vertice2) {
		boolean existe = true;
		try {
			Vertice2<V,A> nodoInicio = tablaVertices.obtener(vertice1);
			Vertice2<V,A> nodoDestino = tablaVertices.obtener(vertice2);

			//TODO
		} catch (ClaveException e) { // No existe alguno de los vertices
			existe = false;
		}
		return existe;
	}

	@Override
	public A valorArista(V vertice1, V vertice2) throws NoExiste {
		return null;
	}

	@Override
	public LinkedList<V> adyacentes(V vertice) throws ErrorCrearLista {
		return null;
	}
}
