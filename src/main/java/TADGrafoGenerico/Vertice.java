package TADGrafoGenerico;


import TAD_TablaHash_ListaGenerica.Nodo;

public class Vertice<K extends Comparable<K>, V, A> implements Comparable<Vertice<K, V, A>> {

	private K clave;
	private V dato;

	private Arista<K, V, A> punteroAristaHorizontal;
	private Arista<K, V, A> punteroAristaVertical;


	// ************ ↓↓↓ MÉTODOS ↓↓↓ ***********

	public Vertice(K clave, V dato){
		this.clave = clave;
		this.dato = dato;
		punteroAristaHorizontal = null;
		punteroAristaVertical = null;
	}

	/**
	 * Metodo que comprueba si hay un siguiente nodo arista
	 * @return true si hay nodo, false si es null
	 */
	public boolean hasNextHorizontal() {
		return punteroAristaHorizontal != null;
	}

	/**
	 * Metodo next
	 * @return el puntero al siguiente nodo Arista Horizontal
	 */
	public Arista<K, V, A> getPunteroAristaHorizontal() {
		return punteroAristaHorizontal;
	}
	/**
	 * Metodo next
	 * @return el puntero al siguiente nodo Arista Vertical
	 */
	public Arista<K, V, A> getPunteroAristaVertical() {
		return punteroAristaVertical;
	}

	/**
	 * Setter
	 * @param aristaHorizontal - nuevo puntero al primer nodo Arista Horizontal
	 */
	public void setNodoHorizontal(Arista<K, V, A> aristaHorizontal){
		this.punteroAristaHorizontal = aristaHorizontal;
	}

	/**
	 * Setter
	 * @param aristaVertical - nuevo puntero al primer nodo Arista Vertical
	 */
	public void setNodoVertical(Arista<K, V, A> aristaVertical){
		this.punteroAristaVertical = aristaVertical;
	}

	/**
	 * Metodo para obtener la instancia a los datos
	 * @return la instancia de los datos del nodo
	 */
	public V getDatos() {
		return dato;
	}

	@Override
	public int compareTo(Vertice<K, V, A> vertix) {
		return clave.compareTo(vertix.clave);
	}

	//colores para la impresión por consola
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_RESET = "\u001B[0m";

	@Override
	public String toString() {
		return "Vertice{" +
				ANSI_PURPLE + "dato=" + dato +
				ANSI_BLUE + ", punteroAristaHorizontal=" + punteroAristaHorizontal +
				ANSI_GREEN + ", punteroAristaVertical=" + punteroAristaVertical + ANSI_RESET +
				'}';
	}
}
