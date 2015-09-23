package particionado;

import java.io.BufferedReader;

public class ValidacionCruzada implements EstrategiaParticionado {

	private int numParticiones = 0;

	@Override
	public String getNombreEstrategia() {
		return "Validación cruzada";
	}

	@Override
	public int getNumeroPArticiones() { 
		return numParticiones;
	}

	@Override
	/* Crea particiones según el método de validación cruzada simple. 
	El conjunto de entrenamiento se crea con las numPart-1 particiones y 
	el de test con la partición restante*/
	public ArrayList<Particion> crearParticiones(Datos datos) {
		ArrayList<Particion> particiones=null;

		return particiones;

	}


}