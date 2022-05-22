package aplicacion;

import com.google.gson.*;
import datos.Enchufe;
import datos.GrafoEstaciones;
import datos.ZonaRecarga;
import excepciones.NoExiste;

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class AplicacionEstaciones {
	public static void main(String[] args) throws FileNotFoundException { //TODO controlar excepcion
		// Cargamos los datos de JSON al grafo
		LinkedList<ZonaRecarga> listaZonas = ZonaRecarga.leerJson("src/main/resources/icaen.json");
		//System.out.println(listaZonas);

		// Añadimos las estaciones al grafo
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);

		System.out.println(listaZonas.get(0).distancia(listaZonas.get(listaZonas.size()-1)));
	}
}
