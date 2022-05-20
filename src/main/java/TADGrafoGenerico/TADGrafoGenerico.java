package TADGrafoGenerico;

import excepciones.ErrorCrearLista;
import excepciones.NoExiste;

import java.util.LinkedList;

public interface TADGrafoGenerico <V, A> {

	/**
	 * Funcion agregar Arista
	 * @param vertice1 - un vertice
	 * @param vertice2 - otro vertice
	 * @param arista - union entre ambos vertices
	 * @throws NoExiste - cuando no existe uno de los vertices
	 */
	void agregarArista(V vertice1, V vertice2, A arista) throws NoExiste;

	/**
	 * Función que comprueba si existe la arista
	 * @return true - existe la arista. false - no existe
	 */
	boolean existeArista(V vertice1, V vertice2);

	/**
	 * Función para saber el valor de la arista de dos vertices pasados por parámetro
	 * @return el valor de la arista
	 * @throws NoExiste - cuando la arista no existe en el grafo
	 */
	A valorArista(V vertice1, V vertice2) throws NoExiste;

	//TODO usar lista genérica
	/**
	 * Función que busca los vertices conectados directamente al vértice pasado por referencia
	 * @return una lista con todos los vertices adyacentes
	 * @throws ErrorCrearLista -cuando no se ha podido crear la lista
	 */
	LinkedList<V> adyacentes(V vertice) throws ErrorCrearLista;


}
