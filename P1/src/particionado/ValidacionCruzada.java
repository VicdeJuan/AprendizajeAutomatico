package particionado;

import datos.Datos;
import java.io.BufferedReader;
import java.util.ArrayList;

public class ValidacionCruzada extends ValidacionSimple {

	private int numParticiones = 10;

	@Override
	public String getNombreEstrategia() {
		return "Validación cruzada";
	}

	@Override
	/* Crea particiones según el método de validación cruzada simple. 
	El conjunto de entrenamiento se crea con las numPart-1 particiones y 
	el de test con la partición restante*/
	public ArrayList<Particion> crearParticiones(Datos datos) {
		ArrayList<Particion> particiones=new ArrayList<>();
                
                for (int i=0; i< numParticiones-1; i++)
                    particiones.add(super.crearParticiones(datos).get(i));
               
                
                
		return particiones;

	}


}