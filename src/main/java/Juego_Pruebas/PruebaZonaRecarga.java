package Juego_Pruebas;

import datos.ZonaRecarga;

import java.io.FileNotFoundException;
import java.util.LinkedList;

public class PruebaZonaRecarga {
	public static void main(String[] args) throws FileNotFoundException{
		// Probamos Lectura Fichero JSON
		LinkedList<ZonaRecarga> listaPruebas  =ZonaRecarga.leerJson("src/main/resources/pruebas.json");
		LinkedList<ZonaRecarga> lista  = ZonaRecarga.leerJson("src/main/resources/icaen.json");

		pruebaJSON(lista, listaPruebas);

		pruebaPotencia(listaPruebas);

		pruebaDistancia(listaPruebas);
	}

	private static void pruebaDistancia(LinkedList<ZonaRecarga> listaPruebas) {
		System.out.println("\n----------PRUEBA DISTANCIA COORDENADAS DE LAS ZONAS--------------");
		String frase = "";
		frase += "\nDistancia zona 0 a si mismo --> " + listaPruebas.get(0).distancia(listaPruebas.get(0));
		frase += "\nDistancia zona 0 a 1 --> " + listaPruebas.get(0).distancia(listaPruebas.get(1));
		frase += "\nDistancia zona 0 a 2 --> " + listaPruebas.get(0).distancia(listaPruebas.get(2));
		frase += "\nDistancia zona 1 a 2 --> " + listaPruebas.get(1).distancia(listaPruebas.get(2));
		System.out.println(frase);
	}

	private static void pruebaPotencia(LinkedList<ZonaRecarga> listaPruebas) {
		System.out.println("\n----------PRUEBA ENCHUFE MAS POTENCIA--------------");
		String frase = "";
		for (ZonaRecarga vertice: listaPruebas) {
			frase += vertice.getId()+ "/ " + vertice.getEnchufeMasPotencia() + " \n";
		}
		System.out.println(frase);
	}

	private static void pruebaJSON(LinkedList<ZonaRecarga> lista,LinkedList<ZonaRecarga> listaPruebas) throws FileNotFoundException {
		System.out.println("\n----------PRUEBA LEER JSON--------------");
		// Listamos los id del fichero
		System.out.println("Lista de Zonas de Prueba leidas");
		listarIdZonas(listaPruebas);
		// No imprimimos la lista completa porque es muy larga
		//System.out.println("Lista Completa de Zonas leidas");
		//listarIdZonas(lista);


		// Comprobamos si hay algun id repetido
		System.out.println("Miramos repetidos en las lista");
		System.out.println("Hay repetidos lista Prueba: "+ repes(listaPruebas));
		System.out.println("Hay repetidos lista Completa: "+ repes(lista));

	}

	private static void listarIdZonas(LinkedList<ZonaRecarga> lista) {
		String frase = "";
		int i = 0;
		for (ZonaRecarga vertice: lista) {
			frase += i+ "/ " + vertice.getId() + " \n";
			i += 1;
		}
		System.out.println(frase);
	}

	private static boolean repes(LinkedList<ZonaRecarga> lista){
		boolean repe = false;
		for (int i = 0; i< lista.size(); i++){
			for(int j = 0; j< lista.size();j++){
				if(i != j) {
					if (lista.get(i).getId() == lista.get(j).getId()) {
						repe = true;
					}
					if (lista.get(i).equalsCoordenadas(lista.get(j).getLatitud(), lista.get(j).getLongitud())) {
						repe = true;
					}
				}
			}
		}
		return repe;
	}
}
