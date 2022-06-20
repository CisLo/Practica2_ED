package aplicacion;

import datos.GrafoEstaciones;
import datos.ZonaRecarga;
import excepciones.NoExiste;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;

public class AnalisisCostesTemporales {
	static long inicial;
	static long[] contador = new long[5];
	static final int AUTONOMIA = 40; // MODIFICAR ESTE VALOR

	public static void main(String[] args) throws FileNotFoundException {

		LinkedList<ZonaRecarga> listaZonas = ZonaRecarga.leerJson("src/main/resources/icaen.json");
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);

		// Inicializamos el vector a 0
		Arrays.fill(contador, 0);

		int[] nodos = new int[5];
		try {
			StringBuilder distancia = new StringBuilder("[");
			nodos[0] = grafoEstaciones.caminoOptimo("9794082", "7562169", AUTONOMIA, distancia).size();
			distancia.append(" , ");
			nodos[1] = grafoEstaciones.caminoOptimo("9794082", "35349720", AUTONOMIA, distancia).size();
			distancia.append(" , ");
			nodos[2] = grafoEstaciones.caminoOptimo("35349720", "7562243", AUTONOMIA, distancia).size();
			distancia.append(" , ");
			nodos[3] = grafoEstaciones.caminoOptimo("35349720", "29786231", AUTONOMIA, distancia).size();
			distancia.append(" , ");
			nodos[4] = grafoEstaciones.caminoOptimo("7562086", "7562247", AUTONOMIA, distancia).size();
			distancia.append("]");
			System.out.println("Las distancias de las rutas son: \n"+distancia);
		} catch (NoExiste e) {
			e.printStackTrace();
		}


		for (int i = 0; i < 100; i++){
			medirTiempo(grafoEstaciones);
		}

		for(int i = 0; i < contador.length; i++){
			System.out.println("Ruta " + (i+1) + ": tiene " + nodos[i] + " estaciones-> "+ contador[i]/100.0);
		}

	}

	private static void medirTiempo(GrafoEstaciones grafoEstaciones) {
    /*
	Ruta 1: 9794082 (Molins de Rei) --> 7562169 (Tortosa)
		* Origen:
		"latitud": "41.412473739646",
		"longitud": "2.014127862566"
		* Destí:
		"latitud": "40.794775",
		"longitud": "0.525542" */

		try {
			iniciar();
			grafoEstaciones.caminoOptimo("9794082", "7562169", AUTONOMIA, null);
			contador[0] += finalizar();
		} catch (NoExiste e) {
			e.printStackTrace();
		}

		/*
		Ruta 2: 9794082 (Molins de Rei) --> 35349720 (Argentona)
			* Origen:
			"latitud": "41.412473739646",
			"longitud": "2.014127862566"

			* Destí:
			"latitud": "41.5555823",
			"longitud": "2.4005556" */

		try {
			iniciar();
			grafoEstaciones.caminoOptimo("9794082", "35349720", AUTONOMIA, null);
			contador[1] += finalizar();
		} catch (NoExiste e) {
			e.printStackTrace();
		}

		/*
		Ruta 3: 35349720 (Argentona) --> 7562243 (Sant Feliu de Guíxols)
			* Origen:
			"latitud": "41.5555823",
			"longitud": "2.4005556"

			* Destí:
			"latitud": "41.780674",
			"longitud": "3.022077"*/

		try {
			iniciar();
			grafoEstaciones.caminoOptimo("35349720", "7562243", AUTONOMIA, null);
			contador[2] += finalizar();
		} catch (NoExiste e) {
			e.printStackTrace();
		}

		/*
		Ruta 4: 35349720 (Argentona) --> 29786231 (Montblanc)
			* Origen:
			"latitud": "41.5555823",
			"longitud": "2.4005556"

			* Destí:
			"latitud": "41.375768",
			"longitud": "1.163327"*/

		try {
			iniciar();
			grafoEstaciones.caminoOptimo("35349720", "29786231", AUTONOMIA, null);
			contador[3] += finalizar();
		} catch (NoExiste e) {
			e.printStackTrace();
		}

		/*
		Ruta 5: 7562086 (Tortosa) --> 7562247 (Figueres)
			* Origen:
			"latitud": "40.814151",
			"longitud": "0.515161"

			* Destí:
			"latitud": "42.268984",
			"longitud": "2.966869"		 */

		try {
			iniciar();
			grafoEstaciones.caminoOptimo("7562086", "7562247", AUTONOMIA, null);
			contador[4] += finalizar();
		} catch (NoExiste e) {
			e.printStackTrace();
		}
	}


	private static void iniciar(){
		inicial = System.currentTimeMillis();
	}

	private static long finalizar(){
		return System.currentTimeMillis() - inicial;
	}
}
