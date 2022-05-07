package TADGrafoGenerico;

import excepciones.ErrorCrearLista;
import excepciones.NoExiste;

import java.util.LinkedList;

public interface TADGrafoGenerico <V, A> {

	void agregarArista(V vertix1, V vertix2, A arista);

	/**
	 * Función que comprueba si existe la arista
	 * @return true - existe la arista. false - no existe
	 */
	boolean existeArista(V vertix1, V vertix2);

	/**
	 * Función para saber el valor de la arista de dos vertices pasados por parámetro
	 * @return el valor de la arista
	 * @throws NoExiste - cuando la arista no existe en el grafo
	 */
	A valorArista(V vertix1, V vertix2) throws NoExiste;

	//TODO usar lista genérica
	/**
	 * Función que busca los vertices conectados directamente al vértice pasado por referencia
	 * @return una lista con todos los vertices adyacentes
	 * @throws ErrorCrearLista -cuando no se ha podido crear la lista
	 */
	LinkedList<V> adyacentes(V vertix) throws ErrorCrearLista;


}
