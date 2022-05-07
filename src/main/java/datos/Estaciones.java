package datos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Estaciones {
	private final List<EstacionRecarga> estacionesRecarga;

	public Estaciones(){
		estacionesRecarga = new LinkedList<>();
	}

	public void addEstacion(EstacionRecarga newData){
		estacionesRecarga.add(newData);
	}

	@Override
	public String toString() {
		return "Estaciones{" +
				"estacionesRecarga=" + estacionesRecarga +
				'}';
	}
}
