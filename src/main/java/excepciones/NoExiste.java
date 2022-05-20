package excepciones;

public class NoExiste extends Exception {

	public NoExiste (){
		super("Elemento no encontrado en el grafo");
	}

	public NoExiste (String fraseError){
		super(fraseError);
	}
}
