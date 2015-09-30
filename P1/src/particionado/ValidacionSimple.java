package particionado;

import datos.Datos;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ValidacionSimple implements EstrategiaParticionado {

	private int numParticiones = 1;

	@Override
	public String getNombreEstrategia() {
		return "Validación simple";
	}

	@Override
	public int getNumeroParticiones() { 
		return numParticiones;
	}

                
	@Override
	/* Crea particiones según el método tradicional de división de los datos según el porcentaje deseado. Devuelve una sola partición con un conjunto de train y otro de test.*/

	public ArrayList<Particion> crearParticiones(Datos datos) {
                double percent = 0.25;
                int div =  (int) (percent * datos.getNumDatos());
		ArrayList<Particion> particiones= new ArrayList<>();
                
                ArrayList<Integer> ints = AAUtils.seq(datos.getNumDatos(), true);
                
                Particion toAdd = null;
                for (int i = 0; i < numParticiones; i++)
                    particiones.add(new Particion( (ArrayList<Integer>) ints.subList(0, div), (ArrayList<Integer>) ints.subList(div, ints.size())));
                
                return particiones;
	}


}