package excepciones;

public class YaExisteArista extends Exception {

	public YaExisteArista(){
		super("Elemento no encontrado en el grafo");
	}

	public YaExisteArista(String fraseError){
		super(fraseError);
	}
}
