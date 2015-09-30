package particionado;

import datos.Datos;
import java.util.ArrayList;

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
                
                for (int i = 0; i < numParticiones; i++)
                    particiones.add(new Particion( (ArrayList<Integer>) ints.subList(0, div), (ArrayList<Integer>) ints.subList(div, ints.size())));
                
                return particiones;
	}


}