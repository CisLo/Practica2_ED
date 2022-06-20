package datos;

public class ZonaPrioridad implements Comparable<ZonaPrioridad> {
	private final Integer id;
	private final Double coste;

	public ZonaPrioridad (int id, double coste){
		this.id = id;
		this.coste = coste;
	}
	public int getId() {
		return id;
	}

	public double getCoste() {
		return coste;
	}

	@Override
	public int compareTo(ZonaPrioridad zona) {
		return coste.compareTo(zona.coste);
	}
}
