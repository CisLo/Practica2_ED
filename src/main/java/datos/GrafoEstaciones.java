package datos;

import TADGrafoGenerico.GrafoGenerico;

import java.util.LinkedList;

public class GrafoEstaciones { //TODO
	GrafoGenerico <ZonaRecarga, Integer> grafoEstaciones;

	public GrafoEstaciones (int mida){
		grafoEstaciones = new GrafoGenerico<ZonaRecarga, Integer>(mida);
	}

	public GrafoEstaciones (LinkedList<ZonaRecarga> listaZonasRecarga){
		grafoEstaciones = new GrafoGenerico<ZonaRecarga, Integer>(listaZonasRecarga.size()*2);
		for (ZonaRecarga zonaRecarga : listaZonasRecarga) { // añadimos todas las zonas al grafo
			addEstacion(zonaRecarga);
		}
	}

	public void addEstacion (ZonaRecarga zonaRecarga){
		//TODO

	}
}
