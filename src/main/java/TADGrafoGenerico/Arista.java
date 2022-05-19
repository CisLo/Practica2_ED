package TADGrafoGenerico;

public class Arista <A> {

	Nodo<A, A> arista;

	public Arista (A dato){
		arista = new Nodo<A, A>(dato);
	}

	Nodo<A,A> getArista(){
		return arista;
	}

}
