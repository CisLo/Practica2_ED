package datos;

import com.google.gson.*;
import datos.Enchufe;
import datos.ZonaRecarga;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws FileNotFoundException {
		leerJson("src/main/resources/icaen.json");
	}

	private static void leerJson(String path) throws FileNotFoundException {
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
			if (zona == null || !zona.addEnchufe(enchufe)){ //TODO
				listaZonas.add(zona); //TODO
				zona = new ZonaRecarga(enchufe);
			}

		}

		//TODO System.out.println(listaNodos);
		System.out.println(listaZonas);
		reader.close();
	}


}
