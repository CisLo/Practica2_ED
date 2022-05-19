package TAD_TablaHash_ListaGenerica;


public class TablaHashGenerica<K extends Comparable<K>, T extends Comparable<T>>
		implements TADTablaHash<K, T>{

	// ATRIBUTOS
	ListaGenerica<ClaveValor<K, T>>[] tabla;
	int numElem;

	// METODOS

	/**
	 * Constructor
	 * @param mida - tamaño de la tabla a generar
	 */
	public TablaHashGenerica(int mida){
		tabla = new ListaGenerica[mida];

		//Inicializamos las listas
		for(int i = 0; i< tabla.length; i++){
			tabla[i] = new ListaGenerica<>();
		}
		numElem = 0;
	}

	@Override
	public void insertar(K key, T valorNuevo) {
		int posicionHash = funcionHash(key);
		ListaGenerica<ClaveValor<K,T>> bloque = tabla[posicionHash]; // Escogemos la lista de esa posicionHash

		ClaveValor<K, T> newData = new ClaveValor<>(key, valorNuevo);


		try {
			int indexLista = bloque.buscarPosicion(newData); // Buscamos si hay un nodo con la misma clave

			// En caso de que la clave exista se ejecuta esto ⇒ Actualizamos el valor
			ClaveValor<K, T> claveValorAntiguo = bloque.obtener(indexLista);
			claveValorAntiguo.setValor(valorNuevo);

		} catch (ElementoNoEncontrado e) { // En caso de que la clave sea nueva ⇒ se inserta el nuevo valor
			bloque.insertar(newData); // Se inserta la nueva informacion al final
			numElem++;
		} catch (PosicionIncorrectaException e) {
			e.printStackTrace(); // ERROR
		}

		//cuando el factor de carga es 0.75 o mayor redimensionar la tabla
		if (obtenerFactorCarga() >= 0.75){ // Redimensionamos
			redimensionarTablaHash();
		}
	}

	@Override
	public T obtener(K key) throws ClaveException {
		int posicion = funcionHash(key);
		T valor;

		try {
			int index = tabla[posicion].buscarPosicion(new ClaveValor<>(key, null)); // Buscamos la clave en la lista
			valor = tabla[posicion].obtener(index).getValor();	// Recogemos el valor

		} catch (ElementoNoEncontrado | PosicionIncorrectaException e) {
			throw new ClaveException();
		}

		return valor;
	}

	@Override
	public int buscar(K key) throws ElementoNoEncontrado {
		int posicion = funcionHash(key);
		ListaGenerica<ClaveValor<K, T>> bloque = tabla[posicion];

		int coste;

		try { // Sumamos 1 al coste por el acceso a la posicion (más el coste de la funcion Hash)
			coste = bloque.buscar(new ClaveValor<>(key, null)) + 1;

		}catch(ElementoNoEncontrado e){ // Sumamos 1 cuando no se encuentra el elemento
			throw new ElementoNoEncontrado(e.getNumElemBuscados() + 1); // Contamos el coste de acceder a la posicion por eso es +1
		}

		return coste;
	}

	@Override
	public int longitud() {
		return numElem;
	}

	@Override
	public void eliminar(K key) throws ClaveException {
		int posicionHash = funcionHash(key);
		ListaGenerica<ClaveValor<K,T>> bloqueHash = tabla[posicionHash]; // Escogemos la lista de esa posicionHash

		try {
			int indexLista = bloqueHash.buscarPosicion(new ClaveValor<>(key, null)); //Buscamos
			bloqueHash.eliminar(indexLista);
			numElem--;
		} catch (ElementoNoEncontrado | PosicionIncorrectaException e) {
			throw new ClaveException();
		}

	}

	@Override
	public ListaGenerica<T> obtenerValores() {
		ListaGenerica<T> listaValores = new ListaGenerica<>();
		for (ListaGenerica<ClaveValor<K, T>> bloque : tabla) {	// usamos el iterator
			for (int indexBloque = 0; indexBloque < bloque.longitud(); indexBloque++) {
				try {
					listaValores.insertar(bloque.obtener(indexBloque).getValor());//Guardamos el valor
				} catch (PosicionIncorrectaException e) {
					e.printStackTrace();    //ERROR, comportamiento atipico
				}
			}
		}
		return listaValores;
	}

	@Override
	public ListaGenerica<K> obtenerClaves() {
		ListaGenerica<K> listaValores = new ListaGenerica<>();

		for (ListaGenerica<ClaveValor<K, T>> bloque : tabla) {	//usamos el iterator
			for (int indexBloque = 0; indexBloque < bloque.longitud(); indexBloque++) {
				try {
					listaValores.insertar(bloque.obtener(indexBloque).getClave());//Guardamos el valor
				} catch (PosicionIncorrectaException e) {
					e.printStackTrace();    //ERROR, comportamiento atipico
				}
			}
		}
		return listaValores;
	}

	@Override
	public float obtenerFactorCarga() {
		return (float)numElem/ tabla.length;
	}

	/**
	 * Funcion Hash dependiente del tamaño de la tabla
	 * @param key - identificador único del objeto
	 * @return la posicion de la tabla Hash correspondiente a la clave
	 */
	public int funcionHash(K key){
		return key.hashCode()% tabla.length;
	}

	private void redimensionarTablaHash( ){
		TablaHashGenerica<K, T> nuevaTablaHash = new TablaHashGenerica<>(tabla.length*2);

		// Recorremos la tabla antigua y vamos pasando los valores a la nueva
		for (ListaGenerica<ClaveValor<K, T>> bloque : tabla) {	//usamos el iterator
			for (int indexBloque = 0; indexBloque < bloque.longitud(); indexBloque++) {
				try {
					ClaveValor<K, T> datosNodo = bloque.obtener(indexBloque);
					nuevaTablaHash.insertar(datosNodo.getClave(), datosNodo.getValor());//Guardamos el valor
				} catch (PosicionIncorrectaException e) {
					e.printStackTrace();    //ERROR, comportamiento atipico
				}
			}
		}
		tabla = nuevaTablaHash.tabla;
		numElem = nuevaTablaHash.numElem;
	}

	@Override
	public String toString() {
		String frase = "Tabla: (" + numElem + " elementos) {\n";
		int index = 0;
		for(ListaGenerica<ClaveValor<K, T>> bloque : tabla){
			frase += "["+index+"]: " + bloque.toString();
			index++;
		}
		return frase + "}";
	}

	/**
	 * Función
	 * @return el número de posiciones de la tabla
	 */
	public int midaTabla(){
		return tabla.length;
	}

	/**
	 * Función toString
	 * @return String con los datos de la tabla
	 */
	public String toStringAdicional(){
		String frase = "Tamaño tabla {"+ midaTabla() +"} " + "Tabla: (" + numElem + " elementos) {\n";
		int index = 0;
		for(ListaGenerica<ClaveValor<K, T>> bloque : tabla){
			if (bloque.longitud() >0 ){
				frase += "["+index+"]: " + bloque.toString();
			}
			index++;
		}
		return frase + "}\n";
	}
}
