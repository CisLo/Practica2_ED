package TADGrafoGenerico;


import TAD_TablaHash_ListaGenerica.Nodo;

public class Vertice<V extends Comparable<V>, A> {
	V dato;
	// TODO LinkedList<A> listaRelacion1;
	// TODO ListaGenerica<A> listaRelacion2;

	Arista<A> punteroAristaHorizontal;
	Arista<A> punteroAristaVertical;

	public Vertice(V dato){
		this.dato = dato;
		punteroAristaHorizontal = null;
		punteroAristaVertical = null;
	}

	/*
	public void setListaRelacion1(LinkedList<Arista> listaRelacion1) {
		Arista ar = new Arista();
		listaRelacion1.add()
	}*/

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
	 * @param aristaHorizontal - nuevo puntero al primer nodo Arista Horizontal
	 */
	public void setNodoSiguiente(Arista<A> aristaHorizontal){
		this.punteroAristaHorizontal = aristaHorizontal;
	}

	/**
	 * Setter
	 * @param aristaVertical - nuevo puntero al primer nodo Arista Vertical
	 */
	public void setNodoAnterior(Arista<A> aristaVertical){
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
	public String toString() {
		return "Vertice{" +
				"dato=" + dato +
				", punteroAristaHorizontal=" + punteroAristaHorizontal +
				", punteroAristaVertical=" + punteroAristaVertical +
				'}';
	}
}
