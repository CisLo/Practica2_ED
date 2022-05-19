package TAD_TablaHash_ListaGenerica;

import java.io.Serial;

public class PosicionIncorrectaException extends Exception{

    /**
	 * Version por defecto
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la Excepción
	 * @param posicion - indice que ha dado el error
	 */
	public PosicionIncorrectaException(int posicion){
        super("EXCEPCIÓN PosicionIncorrecta: no existe ningun elemento en esa posicion " + posicion);
    }
}
