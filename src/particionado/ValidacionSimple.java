package particionado;

import java.io.BufferedReader;

public class ValidacionCruzada implements EstrategiaParticionado {

	private int numParticiones = 0;

	@Override
	public String getNombreEstrategia() {
		return "Validación simple";
	}

	@Override
	public int getNumeroPArticiones() { 
		return numParticiones;
	}

	@Override
	/* Crea particiones según el método tradicional de división de los datos según el porcentaje deseado. Devuelve una sola partición con un conjunto de train y otro de test.*/

	public ArrayList<Particion> crearParticiones(Datos datos) {
		ArrayList<Particion> particiones=null;

		return particiones;

	}


}