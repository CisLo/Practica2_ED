package datos;

import java.util.LinkedList;
import java.util.List;

public class Estaciones {
	private final List<Enchufe> estacionesRecarga;

	public Estaciones(){
		estacionesRecarga = new LinkedList<>();
	}

	public void addEnchufe(Enchufe newData){
		estacionesRecarga.add(newData);
	}

	@Override
	public String toString() {
		return "Estaciones{" +
				"estacionesRecarga=" + estacionesRecarga +
				'}';
	}
}
