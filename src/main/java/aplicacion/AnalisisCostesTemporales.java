package aplicacion;

import datos.GrafoEstaciones;
import datos.ZonaRecarga;
import excepciones.NoExiste;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

public class AnalisisCostesTemporales {
	static long inicial;
	static long[] contador = new long[5];

	public static void main(String[] args) throws FileNotFoundException {

		LinkedList<ZonaRecarga> listaZonas = ZonaRecarga.leerJson("src/main/resources/icaen.json");
		GrafoEstaciones grafoEstaciones = new GrafoEstaciones(listaZonas);

		// Inicializamos el vector a 0
		Arrays.fill(contador, 0);

		double[] distancias = new double[5];
		try {
			distancias[0] = grafoEstaciones.caminoOptimoDistancia("9794082", "7562169", 20);
			System.out.println(distancias[0]);
		} catch (NoExiste e) {
			e.printStackTrace();
		}


		for (int i = 0; i < 100; i++){
			medirTiempo(grafoEstaciones);
		}

		for(int i = 0; i < contador.length; i++){
			System.out.println("Ruta " + i + "-> "+ contador[i]/100.0);
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
			grafoEstaciones.caminoOptimo("9794082", "7562169", 20);
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
			grafoEstaciones.caminoOptimo("9794082", "35349720", 20);
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
			grafoEstaciones.caminoOptimo("35349720", "7562243", 20);
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
			grafoEstaciones.caminoOptimo("35349720", "29786231", 20);
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
			grafoEstaciones.caminoOptimo("7562086", "7562247", 20);
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
