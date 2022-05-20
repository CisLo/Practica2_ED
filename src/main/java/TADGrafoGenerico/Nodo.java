package TADGrafoGenerico;

public class Nodo<K, A> {
	K dato;
	Arista<A> punteroAristaHorizontal;
	Arista<A> punteroAristaVertical;

	public Nodo(K dato){
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
	public boolean hasNextVertical(){
		return punteroAristaVertical != null;
	}

	/**
	 * Metodo next
	 * @return el puntero al siguiente nodo Arista Horizontal
	 */
	public Arista<A> getPunteroAristaHorizontal() {
		return punteroAristaHorizontal;
	}
	/**
	 * Metodo next
	 * @return el puntero al siguiente nodo Arista Vertical
	 */
	public Arista<A> getPunteroAristaVertical() {
		return punteroAristaVertical;
	}

	/**
	 * Setter
	 * @param aristaHorizontal - nuevo puntero al siguiente nodo Arista Horizontal
	 */
	public void setNodoSiguiente(Arista<A> aristaHorizontal){
		this.punteroAristaHorizontal = aristaHorizontal;
	}

	/**
	 * Setter
	 * @param aristaVertical - nuevo puntero al siguiente nodo Arista Vertical
	 */
	public void setNodoAnterior(Arista<A> aristaVertical){
		this.punteroAristaVertical = aristaVertical;
	}

	/**
	 * Metodo para obtener la instancia a los datos
	 * @return la instancia de los datos del nodo
	 */
	public K getDatos() {
		return dato;
	}

	@Override
	public String toString() {
		return "Nodo{" +
				"dato=" + dato +
				", punteroAristaHorizontal=" + punteroAristaHorizontal +
				", punteroAristaVertical=" + punteroAristaVertical +
				'}';
	}
}
