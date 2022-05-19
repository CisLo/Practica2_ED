package TAD_TablaHash_ListaGenerica;

public interface TADListaDoblementeEncadenada<T> {

	/**
	 * Funcion para insertar un elemento al final de la lista
	 * @param data - Objeto que se agregara a la lista
	 */
	void insertar(T data);

	/**
	 * Funcion para insertar un elemento en la lista en una posicion indicada por parametro
	 * @param posicion - Indice de la lista en la que se insertara el objeto
	 * @param data - Objeto que se quiere agregar a la lista
	 * @throws PosicionIncorrectaException - excepcion de que no ha sido posible la insercion del elemento en la lista
	 */
	void insertar(int posicion, T data) throws PosicionIncorrectaException;

	/**
	 * Funcion que busca el elemento que se encuentre en un posicion indicada
	 * @param posicion - indice de la lista del que se quiere obtener el valor
	 * @return el elemento que hay en la posicion pasada por parametro
	 * @throws PosicionIncorrectaException - excepcion de que no se ha podido obtener el elemento buscado
	 */
	T obtener(int posicion) throws PosicionIncorrectaException;


	/**
	 * GETTER del numero de elementos de la lista
	 * @return el número de elementos que tiene la lista
	 */
	int longitud();

	/**
	 * Funcion que elimina un elemento de la lista en una posicion determinada
	 * @param posicio - indice del elemento de la lista del que se quiere eliminar
	 * @throws PosicionIncorrectaException - excepcion producida porque no se ha podido eliminar
	 */
	void eliminar(int posicio) throws PosicionIncorrectaException;


	/**
	 * Funcione que comprueba si un elemento está en la lista
	 * @param data - elemento del que se busca su índice
	 * @return el índice del elemento buscado
	 * @throws ElementoNoEncontrado - excepcion producida porque no existe el elemento en la lista
	 */
	int buscar(T data) throws ElementoNoEncontrado;
}
