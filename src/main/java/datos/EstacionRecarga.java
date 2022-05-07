package datos;

public class EstacionRecarga {
	private String id, id_estacio,nom;
	private String data;
	private String consum,carrer,ciutat,estat;
	private String temps, potencia,tipus;
	public String latitud,longitud;

	EstacionRecarga(){

	}

	@Override
	public String toString() {
		return "EstacionRecarga{" +
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
