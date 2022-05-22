package aplicacion;

import com.google.gson.*;
import datos.Enchufe;
import datos.GrafoEstaciones;
import datos.ZonaRecarga;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class AplicacionEstaciones {
	public static void main(String[] args) throws FileNotFoundException {
		// Cargamos los datos de JSON al grafo
		LinkedList<ZonaRecarga> listaZonas = leerJson("src/main/resources/icaen.json");
		listaZonas = ZonaRecarga.leerJson("src/main/resources/icaen.json");
		//System.out.println(listaZonas);
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);

		System.out.println(listaZonas.get(0).distancia(listaZonas.get(1)));
	}

	private static LinkedList<ZonaRecarga> leerJson(String path) throws FileNotFoundException {
		LinkedList<Enchufe> listaNodos = new LinkedList<>();
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
			//listaNodos.add(enchufe);
			/*
			if (zona == null || !zona.addEnchufe(enchufe)){ //TODO
				listaZonas.add(zona); //TODO
				zona = new ZonaRecarga(enchufe);
			}*/

			if (zona == null){
				zona = new ZonaRecarga(enchufe);
			}else{
				if(!zona.addEnchufe(enchufe)){ // Intentamos añadirlo, si no se puede es porque tiene coordenadas distintas
					listaZonas.add(zona); // guardamos la anterior zona de recarga
					zona = new ZonaRecarga(enchufe); // creamos una nueva zona para el enchufe
				}
			}
		}
		listaZonas.add(zona);

		reader.close();
		return listaZonas;
	}




}
