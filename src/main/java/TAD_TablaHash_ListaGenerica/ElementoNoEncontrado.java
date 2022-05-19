package TAD_TablaHash_ListaGenerica;

import java.io.Serial;

public class ElementoNoEncontrado extends Exception {
    /**
	 * Version por defecto
	 */
	@Serial
	private static final long serialVersionUID = 1L;

	// ATRIBUTO DE LA EXCEPCIÓN
	private final int numElemBuscados;

	/**
	 * Constructor de la excepción, almacena el número de elementos accedidos
	 * @param numElementosBuscados - número de elementos accedidos hasta que no se ha encontrado el elemento
	 */
	public ElementoNoEncontrado(int numElementosBuscados){
        super("EXCEPCIÓN ElementoNoEncontrado: No se ha podido encontrar el elemento en la lista, se ha comparado un total de " + numElementosBuscados + " elementos");
		numElemBuscados = numElementosBuscados;
    }

	/**
	 * Getter para obtener cuantos elementos se han buscado antes de retornar la excepcion
	 * @return el número de elementos buscados
	 */
	public int getNumElemBuscados() {
		return numElemBuscados;
	}
}
