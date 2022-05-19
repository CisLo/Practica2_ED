package TADGrafoGenerico;

import TAD_TablaHash_ListaGenerica.TablaHashGenerica;
import excepciones.ErrorCrearLista;
import excepciones.NoExiste;

import java.util.HashMap;
import java.util.LinkedList;

public class GrafoGenerico<V extends Comparable<V>, A> implements TADGrafoGenerico<V, A> {

	TablaHashGenerica<V, Vertice2<V,A>> tablaVertices;

	public GrafoGenerico(){

	}



	@Override
	public void agregarArista(V vertice1, V vertice2, A arista) {

	}

	@Override
	public boolean existeArista(V vertice1, V vertice2) {

		return false;
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
