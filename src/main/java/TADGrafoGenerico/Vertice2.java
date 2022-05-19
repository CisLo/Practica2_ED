package TADGrafoGenerico;


public class Vertice2<V extends Comparable<V>, A> implements Comparable<Vertice2<V, A>> {

	private Nodo<V, A> vertice;

	public Vertice2(V dato){
		vertice = new Nodo<V, A>(dato);

	}

	Nodo<V,A> getVertice(){
		return vertice;
	}



	@Override
	public int compareTo(Vertice2<V, A> vertix) {
		return this.vertice.dato.compareTo(vertix.vertice.dato);
	}
}
