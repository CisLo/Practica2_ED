package TADGrafoGenerico;

public class Arista2<A> {

	A dato;
	Arista<A> punteroAristaHorizontal;
	Arista<A> punteroAristaVertical;

	public Arista2(A dato){
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
	public A getDatos() {
		return dato;
	}

	@Override
	public String toString() {
		return "Arista2{" +
				"dato=" + dato +
				", punteroAristaHorizontal=" + punteroAristaHorizontal +
				", punteroAristaVertical=" + punteroAristaVertical +
				'}';
	}
}
