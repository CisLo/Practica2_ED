package excepciones;

import java.io.Serial;

public class YaExisteArista extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public YaExisteArista(String fraseError){
		super(fraseError);
	}
}
