package TAD_TablaHash_ListaGenerica;

import java.io.Serial;

public class ClaveException extends Exception {
	/**
	 * Version por defecto
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor de la Excepción
	 */
	public ClaveException() {
		super("EXCEPCIÓN ClaveException: No se ha podido encontrar ningun elemento en la tabla con esa clave");
	}
}
