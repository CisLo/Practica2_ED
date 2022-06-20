package datos;

/**
 * Clase que almacena los datos de cada enchufe de recarga
 */
public class Enchufe {
	private final int id, id_estacio;
	private final String nom;
	private final String data;
	private final String consum,carrer,ciutat,estat;
	private final String temps, potencia;
	private final String tipus;
	private final double latitud,longitud;

	Enchufe( int id, int id_estacio, String nom, String data, String consum, String carrer, String ciutat, String estat, String temps,
			 String potencia, String tipus, double latitud, double longitud){
		this.id = id;
		this.id_estacio = id_estacio;
		this.nom = nom;
		this.data = data;
		this.consum = consum;
		this.carrer = carrer;
		this.ciutat = ciutat;
		this.estat = estat;
		this.temps = temps;
		this.potencia = potencia;
		this.tipus = tipus;
		this.latitud = latitud;
		this.longitud = longitud;

	}

	/**
	 * Getter
	 * @return la latitud (coordenadas) del enchufe
	 */
	public double getLatitud() {
		return latitud;
	}

	/**
	 * Getter
	 * @return la longitud (coordenadas) del enchufe
	 */
	public double getLongitud() {
		return longitud;
	}

	/**
	 * Getter
	 * @return el identificador del enchufe
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter de la potencia del enchufe
	 * @return la potencia del enchufe
	 * @throws NumberFormatException - cuando no se tiene informacion sobre la potencia del enchufe
	 */
	public double getPotencia(){
		return Double.parseDouble(potencia);
	}

	/**
	 * Getter
	 * @return el nombre del enchufe
	 */
	public String getNom(){
		return nom;
	}

	@Override
	public String toString() {
		return "\n\tEnchufe{" +
				"id='" + id + '\'' +
				", id_estacio='" + id_estacio + '\'' +
				", nom='" + nom + '\'' +
				", data='" + data + '\'' +
				", consum='" + consum + '\'' +
				", carrer='" + carrer + '\'' +
				", ciutat='" + ciutat + '\'' +
				", estat='" + estat + '\'' +
				", temps='" + temps + '\'' +
				", potencia='" + potencia + '\'' +
				", tipus='" + tipus + '\'' +
				", latitud='" + latitud + '\'' +
				", longitud='" + longitud + '\'' +
				'}';
	}
}
