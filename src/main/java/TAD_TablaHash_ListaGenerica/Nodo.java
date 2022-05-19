package TAD_TablaHash_ListaGenerica;

public class Nodo<T>{

	private T datos;
	private Nodo<T> nodoAnterior;
	private Nodo<T> nodoSiguiente;


	/**
	 * Constructor de Nodo Generico para una lista doble enlazada
	 * @param datos - objeto con los datos del nodo a introducir
	 */
	public Nodo(T datos){
		this.datos = datos;
		nodoAnterior = null;
		nodoSiguiente = null;
	}

	/**
	 * Metodo que comprueba si hay un siguiente nodo
	 * @return true si hay nodo, false si es null
	 */
	public boolean hasNext() {
		return nodoSiguiente != null;
	}

	/**
	 * Metodo next
	 * @return el puntero al siguiente nodo
	 */
	public Nodo<T> getNodoSiguiente() {
		return nodoSiguiente;
	}

	/**
	 * Metodo previous
	 * @return el puntero al nodo anterior
	 */
	public Nodo<T> getNodoAnterior(){
		return  nodoAnterior;
	}

	/**
	 * Setter
	 * @param nodoSiguiente - nuevo puntero al siguiente nodo
	 */
	public void setNodoSiguiente(Nodo<T> nodoSiguiente){
		this.nodoSiguiente = nodoSiguiente;
	}

	/**
	 * Setter
	 * @param nodoAnterior - nuevo puntero al nodo anterior
	 */
	public void setNodoAnterior(Nodo<T> nodoAnterior){
		this.nodoAnterior = nodoAnterior;
	}

	/**
	 * Metodo para obtener la instancia a los datos
	 * @return la instancia de los datos del nodo
	 */
	public T getDatos() {
		return datos;
	}
}