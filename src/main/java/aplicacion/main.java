package aplicacion;

import com.google.gson.*;
import datos.Enchufe;
import datos.Estaciones;

import java.io.*;
import java.util.Scanner;

public class main {
	public static void main(String[] args) throws FileNotFoundException {
		leerJson();
	}

	private static void leerJson() throws FileNotFoundException {
		Estaciones listaNodos = new Estaciones();
		Enchufe nodo;
		Scanner reader = new Scanner(new File("src/main/resources/icaen.json"));

		reader.useDelimiter("]");
		String s = reader.next();
		s += "]";


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
			nodo = new Gson().fromJson(objeto, Enchufe.class);
			assert false;
			listaNodos.addEnchufe(nodo);
		}

		System.out.println(listaNodos);
		reader.close();
	}

}
