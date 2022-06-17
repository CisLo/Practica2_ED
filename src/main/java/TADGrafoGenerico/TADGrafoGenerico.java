package TADGrafoGenerico;

import excepciones.NoExiste;
import excepciones.YaExisteArista;

import java.util.LinkedList;

public interface TADGrafoGenerico <K, V, A> {

	/**
	 * Función agregar Arista
	 * @param vertice1 - un vértice
	 * @param vertice2 - otro vértice
	 * @param arista - union entre ambos vertices
	 * @throws NoExiste - cuando no existe uno de los vertices
	 */
	void agregarArista(K vertice1, K vertice2, A arista) throws NoExiste, YaExisteArista;

	/**
	 * Función que comprueba si existe la arista
	 * @return true - existe la arista. false - no existe
	 */
	boolean existeArista(K vertice1, K vertice2);

	/**
	 * Función para saber el valor de la arista de dos vertices pasados por parámetro
	 * @return el valor de la arista
	 * @throws NoExiste - cuando la arista no existe en el grafo
	 */
	A valorArista(K vertice1, K vertice2) throws NoExiste;
	
	/**
	 * Función que busca los vertices conectados directamente al vértice pasado por referencia
	 * @return una lista con todos los vertices adyacentes
	 * @throws NoExiste -cuando no se ha podido crear la lista
	 */
	LinkedList<V> adyacentes(K vertice) throws NoExiste;


}
