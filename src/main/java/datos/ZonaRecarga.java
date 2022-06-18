package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import com.google.gson.Gson;

public class ZonaRecarga implements Comparable<ZonaRecarga> {
	private final int id;
	private LinkedList<Enchufe> listaEnchufes;
	private final double latitud, longitud;

	public ZonaRecarga (Enchufe enchufe){
		this.id = enchufe.getId();
		listaEnchufes = new LinkedList<Enchufe>();
		listaEnchufes.add(enchufe);
		this.latitud = enchufe.getLatitud();
		this.longitud = enchufe.getLongitud();
	}

	/**
	 * Función que añade un enchufe a la zona de recarga, tiene que tener las mismas coordenadas
	 * @param enchufe - enchufe a añadir
	 * @return true - si se ha añdido; false - si no tiene las mismas coordenadas
	 */
	public boolean addEnchufe (Enchufe enchufe)  {
		if (enchufe.getLongitud() == longitud && enchufe.getLatitud() == latitud){
			listaEnchufes.add(enchufe);
			return true;
		}
		return false; // No se puede añadir porque no pertenece a la misma zona
	}

	/**
	 * Función que calcula la distancia euclidiana entre las coordenadas de dos zonas de recarga
	 * @param zonaRecarga - zona de recarga sobre la que se quiere calcular la distancia respecto a la zona de recarga actual
	 * @return la distancia (double) entre dos zonas de recarga
	 * @throws NullPointerException - excepcion si zona de recarga es nulo
	 */
	public double distancia(ZonaRecarga zonaRecarga) {
		if (zonaRecarga == null){
			throw new NullPointerException();
		}

		final double  R = 6378.137; // Constante radio ecuatorial de la tierra

		// Calculamos la latitud y longitud
		double latitudA = zonaRecarga.latitud * Math.PI/180;
		double longitudA = zonaRecarga.longitud * Math.PI/180;
		double latitudB = this.latitud * Math.PI/180;
		double longitudB = this.longitud * Math.PI/180;

		// Calculamos la variación de la latitud y longitud
		double variacionLatitud = latitudB-latitudA;
		double variacionLongitud = longitudB-longitudA;

		variacionLatitud = Math.sin(variacionLatitud/2);
		variacionLongitud = Math.sin(variacionLongitud/2);

		double resultado = variacionLatitud * variacionLatitud + Math.cos(latitudA)*Math.cos(latitudB) * variacionLongitud * variacionLongitud;

		return 2 * R * Math.atan2(Math.sqrt(resultado), Math.sqrt(1-resultado));
	}


	/**
	 * Función que lee un archivo JSON con los enchufes de recarga
	 * @param path - ruta del archivo JSON
	 * @return una lista con las zonas de recarga (conjunto de enchufes con las mismas coordenadas
	 * @throws FileNotFoundException - problemas al no poder encontrar el fichero
	 */
	public static LinkedList<ZonaRecarga> leerJson(String path) throws FileNotFoundException {
		LinkedList<ZonaRecarga> listaZonas = new LinkedList<>();
		ZonaRecarga zona = null;
		Enchufe enchufe;
		Scanner reader = new Scanner(new File(path));
		int index;
		boolean zonaEncontrada;

		// cargamos el texto
		reader.useDelimiter("]");
		String s = reader.next();
		s += "]";


		// configuramos para leer los enchufes
		String objeto;
		s = s.replace("[", "");
		s = s.replace("]", "");
		Scanner leer = new Scanner(s);
		leer.useDelimiter("},");

		while (leer.hasNext()){
			// Ajustamos el string
			objeto = leer.next();
			objeto = objeto.replace("}", "");
			objeto += "}";

			// Pasamos de JSONObject a un Objeto Java
			enchufe = new Gson().fromJson(objeto, Enchufe.class);

			index = 0;
			zonaEncontrada = false;
			while (!zonaEncontrada && index < listaZonas.size()){
				zona = listaZonas.get(index);
				if (zona.equalsCoordenadas(enchufe.getLatitud(), enchufe.getLongitud())){ //Añadimos enchufe a zona existente
					zonaEncontrada = true;
					zona.addEnchufe(enchufe);
				}
				index++;
			}
			if(!zonaEncontrada){ //Añadimos nueva zona zona
				zona = new ZonaRecarga(enchufe);
				listaZonas.add(zona);
			}
		}

		leer.close();
		reader.close();
		return listaZonas;
	}

	public int getId() {
		return id;
	}

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	/**
	 * Compara si la zona tiene esas coordenadas
	 * @param latitud de la zona a comparar
	 * @param longitud de la zona a comparar
	 * @return true si son las mismas coordenadas o false si son diferentes
	 */
	public boolean equalsCoordenadas(double latitud, double longitud){
		return latitud == this.latitud && longitud == this.longitud;
	}

	public Enchufe getEnchufeMasPotencia(){
		Enchufe enchufeMasPotencia = listaEnchufes.get(0);
		for (Enchufe enchufe:listaEnchufes) {
			try {
				if (enchufe.getPotencia() > enchufeMasPotencia.getPotencia()){
					enchufeMasPotencia = enchufe;
				}
			} catch (NumberFormatException e){
				// No hacer nada
			}
		}
		return enchufeMasPotencia;
	}

	@Override
	public int compareTo(ZonaRecarga zona) { //TODO
		return this.id - zona.id; // negativo si este objeto es menor, 0 si son iguales, positivo si es mayor a la zona pasasado por parametro
	}

	@Override
	public String toString() {
		return "\nZonaRecarga{" +
				"id=" + id +
				", latitud=" + latitud +
				", longitud=" + longitud +
				", listaEnchufes=" + listaEnchufes +
				'}';
	}
}