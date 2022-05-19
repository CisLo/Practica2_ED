package TAD_TablaHash_ListaGenerica;

import java.util.Iterator;

/**
 * Clase de la Lista Generica, implementa el TADListaDoblementeEncadenada que permite almacenar una lista del tipo de datos.
 * También implementa la interfaz Iterable para poder usar bucles for each.
 * @param <T> Tipo de datos del que se quiere guardar en la lista
 */
public class ListaGenerica<T extends Comparable<T>>
		implements TADListaDoblementeEncadenada<T>, Iterable<T> {

	private int numElems;
	private final Nodo<T> primerNodo; // El primer nodo es una constante porque siempre será el elemento fantasma
	private Nodo<T> ultimoNodo;

	public ListaGenerica() {
		Nodo<T> fantasma = new Nodo<>(null); // nodo fantasma en la posicion -1
		primerNodo = fantasma;
		ultimoNodo = fantasma;
		numElems = 0; // No contamos el elemento fantasma
	}

	@Override
	public Iterator<T> iterator() {
		return new ListaIterator<>(this);
	}


	@Override
	public void insertar(T data) {
		Nodo<T> nodoInsertar = new Nodo<>(data); // Guardamos el dato en un nodo

		//Reasignamos los punteros para la inserción
		ultimoNodo.setNodoSiguiente(nodoInsertar);
		nodoInsertar.setNodoAnterior(ultimoNodo);

		ultimoNodo = nodoInsertar; // El elemento insertado pasa a ser el último
		numElems++;
	}

	@Override
	public void insertar(int posicion, T data) throws PosicionIncorrectaException {
		// Definimos nuestros nodos en los que cambiaremos los punteros
		Nodo<T> nodoInsertar = new Nodo<>(data);
		Nodo<T> nodoVecinoSiguiente = obtenerNodo(posicion); // No se puede insertar al final
		Nodo<T> nodoVecinoAnterior = nodoVecinoSiguiente.getNodoAnterior(); // Siempre habra un nodo anterior, ya que está el elemento fantasma

		// Modificamos los punteros del nodo insertado
		nodoInsertar.setNodoAnterior(nodoVecinoAnterior);
		nodoInsertar.setNodoSiguiente(nodoVecinoSiguiente);

		// Modificamos los punteros de los nodos anterior y posterior
		nodoVecinoSiguiente.setNodoAnterior(nodoInsertar);
		nodoVecinoAnterior.setNodoSiguiente(nodoInsertar);

		numElems++;
	}

	@Override
	public T obtener(int posicion) throws PosicionIncorrectaException {
		return obtenerNodo(posicion).getDatos();
	}

	/**
	 * Función que busca en cierta posicion de la lista
	 * @param posicion número de la lista en la que se encuentra el nodo
	 * @return nodo buscado
	 * @throws PosicionIncorrectaException error por posicion que no se encuentra en la lista
	 */
	private Nodo<T> obtenerNodo(int posicion) throws PosicionIncorrectaException{
		// Comprobamos que la posicion pedida se encuentre en la tabla, se lanza excepcion si no.
		if (posicion >= numElems || posicion < 0){
			throw new PosicionIncorrectaException(posicion);
		}

		// En caso de que no haya errores con la posicion
		Nodo<T> nodoBuscar = primerNodo.getNodoSiguiente(); // Empezamos desde el primer elemento (posterior al fantasma)
		int index = 0;

		while (index<posicion) {
			nodoBuscar = nodoBuscar.getNodoSiguiente();
			index++;
		}
		return nodoBuscar;
	}

	@Override
	public int longitud() {
		return numElems;
	}

	@Override
	public void eliminar(int posicion) throws PosicionIncorrectaException {
		Nodo<T> nodoEliminar = obtenerNodo(posicion);

		// Nodos vecinos
		Nodo<T> nodoAnterior = nodoEliminar.getNodoAnterior();
		Nodo<T> nodoSiguiente = nodoEliminar.getNodoSiguiente();

		// Modificamos los punteros de los nodos vecinos, así eliminamos las referencias al nodo a eliminar
		//if (nodoEliminar.hasPrevious()) // Evitamos excepcion NullPointerException
			nodoAnterior.setNodoSiguiente(nodoSiguiente);
		if (nodoEliminar.hasNext())
			nodoSiguiente.setNodoAnterior(nodoAnterior);
		if (ultimoNodo == nodoEliminar) // En caso de que el nodo a eliminar es el último hay que actualizar el últimoNodo
			ultimoNodo = primerNodo; // El último nodo pasa a apuntar al fantasma

		numElems--; // Ciudadano eliminado correctamente
	}

	//@Override

	@Override
	public int buscar(T data) throws ElementoNoEncontrado {
		return buscarPosicion(data) + 1;	//sumamos 1 porque se accede a la posición 0
											// en caso de que se encuentre en caso de que no se encuentre se lanza excepcion
	}

	/**
	 * Funcion que retorna el índice de un elemento en la lista
	 * @param data - elemento del que se busca su índice
	 * @return el índice del elemento buscado
	 * @throws ElementoNoEncontrado - excepcion producida porque no existe el elemento en la lista
	 */
	public int buscarPosicion(T data) throws ElementoNoEncontrado {	//Función auxiliar para buscar y para la TablaHash

		Nodo<T> nodoIndex = primerNodo.getNodoSiguiente();
		int index = 0; //Estamos en el nodo siguiente al fantasma (si hay)
		int posicion = -1;

		boolean encontrado = false;

		while (!encontrado && index<numElems){
			if(data.compareTo(nodoIndex.getDatos()) == 0){
				encontrado = true;
				posicion = index;
			}
			nodoIndex = nodoIndex.getNodoSiguiente(); //Pasamos al siguiente nodo
			index ++;
		}

		if (!encontrado){
			throw new ElementoNoEncontrado(index); // Index es el número de elementos buscados (se suma 1 al final de una iteracion)
		}

		return posicion;
	}

	/**
	 * Función toString
	 * @return - String con los datos de la lista
	 */
	public String toString(){
		String frase = "Lista: "+numElems+" elementos\n";
		for (T elemento: this) { // Usamos la clase lista iterator
			frase += "\t->" + elemento + "\n";
		}
		return frase;
	}

	/**
	 * Función Getter
	 * @return el puntero al primer nodo (fantasma) de la lista
	 */
	Nodo<T> getPrimerNodo() {
		return primerNodo;
	} // Acceso solo para dentro del paquete

}
