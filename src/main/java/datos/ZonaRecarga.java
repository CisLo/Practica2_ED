package datos;

import java.util.LinkedList;

public class ZonaRecarga implements Comparable<ZonaRecarga> {
	private final int id;
	private LinkedList<Enchufe> listaEnchufes;
	private final double latitud, longitud;

	public ZonaRecarga (Enchufe enchufe){
		// this.id = enchufe.getLatitud() + "-" + enchufe.getLongitud(); //TODO
		this.id = enchufe.getId();
		listaEnchufes = new LinkedList<Enchufe>();
		listaEnchufes.add(enchufe);
		this.latitud = enchufe.getLatitud();
		this.longitud = enchufe.getLongitud();
	}

	public boolean addEnchufe (Enchufe enchufe){
		if (enchufe.getLongitud() == longitud && enchufe.getLongitud() == latitud){
			listaEnchufes.add(enchufe);
			return true;
		}
		return false; // No se puede añadir porque no pertenece a la misma zona
	}

	public double distancia(double latitud, double longitud) {
		final double  R = 6378.137;

		double latitudA = latitud * Math.PI/180;
		double longitudA = longitud * Math.PI/180;

		double latitudB = this.latitud * Math.PI/180;
		double longitudB = this.longitud * Math.PI/180;

		double variacionLatitud = latitudB-latitudA;
		double variacionLongitud = longitudB-longitudA;

		variacionLatitud = Math.sin(variacionLatitud/2);
		variacionLongitud = Math.sin(variacionLongitud/2);

		double resultado = variacionLatitud * variacionLatitud + Math.cos(latitudA)*Math.cos(latitudB) * variacionLongitud * variacionLongitud;

		return 2 * R * Math.atan2(Math.sqrt(resultado), Math.sqrt(1-resultado));
	}

	@Override
	public int compareTo(ZonaRecarga zona) { //TODO
		return 0;
	}

	@Override
	public String toString() {
		return "\nZonaRecarga{" +
				"id=" + id +
				", listaEnchufes=" + listaEnchufes +
				", latitud=" + latitud +
				", longitud=" + longitud +
				'}';
	}
}
