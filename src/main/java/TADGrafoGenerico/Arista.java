package TADGrafoGenerico;

public class Arista<K extends Comparable<K>, V, A> {

	private A dato;

	// Punteros a las siguientes aristas del Vertice
	private Arista<K, V, A> punteroAristaHorizontal;
	private Arista<K, V, A> punteroAristaVertical;

	// Referencias a los vertices que unen
	private Vertice<K, V, A> referVerticeHorizontal; // Es el vertice menor
	private Vertice<K, V, A> referVerticeVertical; // Es el vertice mayor


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


	/**
	 * Getter
	 * @return el puntero al siguiente nodo Arista Horizontal, la nueva arista tiene el mismo vertice que el menor de esta arista
	 */
	public Arista<K, V, A> getPunteroAristaHorizontal() {
		return punteroAristaHorizontal;
	}
	/**
	 * Getter
	 * @return el puntero al siguiente nodo Arista Vertical, la nueva arista tiene el mismo vertice que el vertice mayor de esta arista
	 */
	public Arista<K, V, A> getPunteroAristaVertical() {
		return punteroAristaVertical;
	}

	/**
	 * Setter
	 * @param aristaHorizontal - nuevo puntero al siguiente nodo Arista Horizontal (ambas están unidas al vertice menor de esta arista)
	 */
	public void setNodoHorizontal(Arista<K, V, A> aristaHorizontal){
		this.punteroAristaHorizontal = aristaHorizontal;
	}

	/**
	 * Setter
	 * @param aristaVertical - nuevo puntero al siguiente nodo Arista Vertical (ambas están unidas al vertice mayor de esta arista)
	 */
	public void setNodoVertical(Arista<K, V, A> aristaVertical){
		this.punteroAristaVertical = aristaVertical;
	}

	/**
	 * Setter
	 * @param referVerticeHorizontal - nueva referencia de la arista a un vertice menor al que está unido
	 */
	public void setReferVerticeHorizontal(Vertice<K, V, A> referVerticeHorizontal) {
		this.referVerticeHorizontal = referVerticeHorizontal;
	}

	/**
	 * Setter
	 * @param referVerticeVertical - nueva referencia de la arista al vertice mayor al que está unido
	 */
	public void setReferVerticeVertical(Vertice<K, V, A> referVerticeVertical) {
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
	 * @return los datos del vertice menor que une la arista
	 */
	public V getValorReferMenor(){return referVerticeHorizontal.getDatos();}

	/**
	 * Getter
	 * @return los datos del vertice mayor que une la arista
	 */
	public V getValorReferMayor(){return referVerticeVertical.getDatos();}

	/**
	 * Getter
	 * @return la referencia a uno de los vertices
	 */
	public Vertice<K, V, A> getReferVerticeHorizontal() {
		return referVerticeHorizontal;
	}

	/**
	 * Getter
	 * @return la referencia a otro de los vertices
	 */
	public Vertice<K, V, A> getReferVerticeVertical() {
		return referVerticeVertical;
	}

	@Override
	public String toString() {
		// Imprimimos el objeto y cuál es la arista siguiente
		V referHorizMenor = punteroAristaHorizontal == null? null: punteroAristaHorizontal.getValorReferMenor();
		V referHorizMayor = punteroAristaHorizontal == null? null: punteroAristaHorizontal.getValorReferMayor();
		V referVertMenor = punteroAristaVertical == null? null: punteroAristaVertical.getValorReferMenor();
		V referVertMayor = punteroAristaVertical == null? null: punteroAristaVertical.getValorReferMayor();

		return "Arista{" +
				"dato=" + dato +
				",\n\t VerticeHorizontal=" + referVerticeHorizontal.getDatos() +
				",\n\t VerticeVertical=" + referVerticeVertical.getDatos() +
				",\n\t AristaHorizontal=(" + referHorizMenor + "<-->" + referHorizMayor +
				"),\n\t AristaVertical=(" + referVertMenor + "<-->" + referVertMayor +
				")\n\t}";
	}
}
