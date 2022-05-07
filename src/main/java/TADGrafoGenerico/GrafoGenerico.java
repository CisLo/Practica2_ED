package TADGrafoGenerico;

import excepciones.ErrorCrearLista;
import excepciones.NoExiste;

import java.util.HashMap;
import java.util.LinkedList;

public class GrafoGenerico<V, A> implements TADGrafoGenerico {



	@Override
	public void agregarArista(Object vertix1, Object vertix2, Object arista) {

	}

	@Override
	public boolean existeArista(Object vertix1, Object vertix2) {
		return false;
	}

	@Override
	public Object valorArista(Object vertix1, Object vertix2) throws NoExiste {
		return null;
	}

	@Override
	public LinkedList adyacentes(Object vertix) throws ErrorCrearLista {
		return null;
	}
}
