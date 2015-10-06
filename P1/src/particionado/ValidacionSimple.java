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
                double percent = 0.75;
                int div =  (int) (percent * datos.getNumDatos());
		ArrayList<Particion> particiones= new ArrayList<>();
                
                ArrayList<Integer> ints = AAUtils.seq(datos.getNumDatos()-1, true);
                
                particiones.add(new Particion(new ArrayList<>(ints.subList(0, div)) , new ArrayList<>(ints.subList(div, ints.size()))));
                
                    
                
                return particiones;
	}


}