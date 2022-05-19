package TAD_TablaHash_ListaGenerica;

public interface TADTablaHash<K extends Comparable<K>, T extends Comparable<T>> {

	/**
	 * Funcion para insertar un elemento en la tabla Hash
	 * si la clave existe se actualiza el valor
	 * @param key clave del elemento
	 * @param data valor del elemento
	 */
	void insertar(K key, T data);

	/**
	 * Función obtener
	 * @param key clave del elemento a obtener
	 * @return el elemento que tiene la clave K
	 * @throws ClaveException - Excepcion de que no se ha encontrado ningun elemento con la clave en la tabla
	 */
	T obtener (K key) throws ClaveException;

	/**
	 * Función que comprueba si un elemento está en la tabla
	 * @param key clave del elemento buscado
	 * @return el número de elementos accedidos para comprobar si existe
	 */
	int buscar(K key) throws ElementoNoEncontrado;

	/**
	 * Funcion longitud
	 * @return el número de elementos que contiene la tabla actual
	 */
	int longitud();

	/**
	 * Función para eliminar un elemento de la tabla
	 * @param key clave del elemento a eliminar
	 */
	void eliminar(K key) throws ClaveException;

	/**
	 * Funcion obtener Valores
	 * @return una lista con todos los valores de la tabla
	 */
	ListaGenerica<T> obtenerValores();

	/**
	 * Función Obtener Claves
	 * @return una lista con todas las claves de la tabla
	 */
	ListaGenerica<K> obtenerClaves();

	/**
	 * Funcion Factor de Carga
	 * @return el factor de carga actual
	 */
	float obtenerFactorCarga();

}