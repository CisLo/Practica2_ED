package TAD_TablaHash_ListaGenerica;

public class ClaveValor<K extends Comparable<K>, T>
		implements Comparable<ClaveValor<K, T>> {

	// Atributos
	private final K clave; // solo se pueden acceder dentro del paquete
	private T valor;

	/**
	 * Constructor de ClaveValor
	 * @param clave - key de la tabla hash
	 * @param valor - value de la tabla hash
	 */
	public ClaveValor(K clave, T valor){
		this.clave = clave;
		this.valor = valor;
	}

	@Override
	public int compareTo(ClaveValor<K, T> datos) {
		return datos.clave.compareTo(clave);
	}

	@Override
	public String toString() {
		return "Clave: " + clave +
				", Valor: " + valor +
				'}';
	}

	/**
	 * Setter
	 * @param valor - sobreescribe el valor de una ClaveValor
	 */
	public void setValor(T valor) {
		this.valor = valor;
	}

	/**
	 * Getter
	 * @return el valor
	 */
	public T getValor() {
		return valor;
	}

	/**
	 * Getter
	 * @return la clave
	 */
	public K getClave() {
		return clave;
	}
}
