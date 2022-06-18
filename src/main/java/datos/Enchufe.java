package datos;

public class Enchufe {
	private int id, id_estacio;
	private String nom;
	private String data;
	private String consum,carrer,ciutat,estat;
	private String temps, potencia;
	private String tipus;
	private double latitud,longitud;

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

	public double getLatitud() {
		return latitud;
	}

	public double getLongitud() {
		return longitud;
	}

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
