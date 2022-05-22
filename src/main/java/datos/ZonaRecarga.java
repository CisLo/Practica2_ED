package datos;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

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
		if (enchufe.getLongitud() == longitud && enchufe.getLatitud() == latitud){
			listaEnchufes.add(enchufe);
			return true;
		}
		return false; // No se puede añadir porque no pertenece a la misma zona
	}

	public double distancia(ZonaRecarga zonaRecarga) {
		final double  R = 6378.137;

		double latitudA = zonaRecarga.latitud * Math.PI/180;
		double longitudA = zonaRecarga.longitud * Math.PI/180;

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
				", latitud=" + latitud +
				", longitud=" + longitud +
				", listaEnchufes=" + listaEnchufes +
				'}';
	}

	public static LinkedList<ZonaRecarga> leerJson(String path) throws FileNotFoundException {
		LinkedList<ZonaRecarga> listaZonas = new LinkedList<>();
		ZonaRecarga zona = null;
		Enchufe enchufe;
		Scanner reader = new Scanner(new File(path));

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
			assert false;

			// Lo convertimos a una Zona de Recarga
			if (zona == null){
				zona = new ZonaRecarga(enchufe);
			}else{
				if(!zona.addEnchufe(enchufe)){ // Intentamos añadirlo, si no se puede es porque tiene coordenadas distintas
					listaZonas.add(zona); // guardamos la anterior zona de recarga
					zona = new ZonaRecarga(enchufe); // creamos una nueva zona para el enchufe
				}
			}
		}
		listaZonas.add(zona); // Guardamos la última zona de recarga

		reader.close();
		return listaZonas;
	}
}