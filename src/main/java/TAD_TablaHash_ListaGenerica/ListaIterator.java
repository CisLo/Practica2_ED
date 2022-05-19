package TAD_TablaHash_ListaGenerica;

import java.util.Iterator;

public class ListaIterator<T extends Comparable<T>>
		implements Iterator<T> {

	private Nodo<T> posicionIterator;

	/**
	 * Constructor
	 * @param listaGenerica - Lista de la cual se quiere iterar
	 */
	public ListaIterator(ListaGenerica<T> listaGenerica) {
		//lista generica
		posicionIterator= listaGenerica.getPrimerNodo(); 	//empezamos desde el primer nodo
	}

	@Override
	public boolean hasNext() {
		return posicionIterator.hasNext();
	}

	@Override
	public T next() { // Retornamos el valor del elemento actual y pasamos al siguiente elemento
		posicionIterator = posicionIterator.getNodoSiguiente();
		return posicionIterator.getDatos();
	}
}
