package TADGrafoGenerico;

public class Arista<V extends Comparable<V>, A> {

	private A dato;

	// Punteros a las siguientes aristas del Vertice
	private Arista<V, A> punteroAristaHorizontal;
	private Arista<V, A> punteroAristaVertical;

	// Referencias a los vertices que unen
	private Vertice<V, A> referVerticeHorizontal;
	private Vertice<V, A> referVerticeVertical;


	// ************ ↓↓↓ MÉTODOS ↓↓↓ ***********

	public Arista(A dato){
		this.dato = dato;
		punteroAristaHorizontal = null;
		punteroAristaVertical = null;
		referVerticeHorizontal = null;
		referVerticeVertical = null;
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
	 * Getter
	 * @return el puntero al siguiente nodo Arista Horizontal, la nueva arista tiene el mismo vertice que el menor de esta arista
	 */
	public Arista<V, A> getPunteroAristaHorizontal() {
		return punteroAristaHorizontal;
	}
	/**
	 * Getter
	 * @return el puntero al siguiente nodo Arista Vertical, la nueva arista tiene el mismo vertice que el vertice mayor de esta arista
	 */
	public Arista<V, A> getPunteroAristaVertical() {
		return punteroAristaVertical;
	}

	/**
	 * Setter
	 * @param aristaHorizontal - nuevo puntero al siguiente nodo Arista Horizontal (ambas estan unidas al vertice menor de esta arista)
	 */
	public void setNodoHorizontal(Arista<V, A> aristaHorizontal){
		this.punteroAristaHorizontal = aristaHorizontal;
	}

	/**
	 * Setter
	 * @param aristaVertical - nuevo puntero al siguiente nodo Arista Vertical (ambas estan unidas al vertice mayor de esta arista)
	 */
	public void setNodoVertical(Arista<V, A> aristaVertical){
		this.punteroAristaVertical = aristaVertical;
	}

	/**
	 * Setter
	 * @param referVerticeHorizontal - nueva referencia de la arista a un vertice menor al que esta unido
	 */
	public void setReferVerticeHorizontal(Vertice<V, A> referVerticeHorizontal) {
		this.referVerticeHorizontal = referVerticeHorizontal;
	}

	/**
	 * Setter
	 * @param referVerticeVertical - nueva referencia de la arista al vertice mayor al que está unido
	 */
	public void setReferVerticeVertical(Vertice<V, A> referVerticeVertical) {
		this.referVerticeVertical = referVerticeVertical;
	}

	/**
	 * Metodo para obtener la instancia a los datos
	 * @return la instancia de los datos del nodo
	 */
	public A getDatos() {
		return dato;
	}

	/**
	 * Getter
	 * @return la referencia a uno de los vertices
	 */
	public Vertice<V, A> getReferVerticeHorizontal() {
		return referVerticeHorizontal;
	}

	/**
	 * Getter
	 * @return la referencia a otro de los vertices
	 */
	public Vertice<V, A> getReferVerticeVertical() {
		return referVerticeVertical;
	}

	@Override
	public String toString() {
		return "Arista{" +
				"dato=" + dato +
				",\n\t AristaHorizontal=" + punteroAristaHorizontal +
				",\n\t AristaVertical=" + punteroAristaVertical +
				",\n\t VerticeHorizontal=" + referVerticeHorizontal.getDatos() +
				",\n\t VerticeVertical=" + referVerticeVertical.getDatos() +
				"\n\t}";
	}
}
