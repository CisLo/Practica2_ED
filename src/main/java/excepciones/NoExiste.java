package excepciones;

import java.io.Serial;

public class NoExiste extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public NoExiste (String fraseError){
		super(fraseError);
	}
}
