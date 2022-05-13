package TADGrafoGenerico;

import excepciones.ErrorCrearLista;
import excepciones.NoExiste;

import java.util.LinkedList;

public class GrafoGenerico<V, A> implements TADGrafoGenerico<V, A> {



	@Override
	public void agregarArista(V vertix1, V vertix2, A arista) {

	}

	@Override
	public boolean existeArista(V vertix1, V vertix2) {
		return false;
	}

	@Override
	public A valorArista(V vertix1, V vertix2) throws NoExiste {
		return null;
	}

	@Override
	public LinkedList adyacentes(V vertix) throws ErrorCrearLista {
		return null;
	}
}
