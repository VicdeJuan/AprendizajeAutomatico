package particionado;

import java.util.ArrayList;

public interface EstrategiaParticionado {

	public String  getNombreEstrategia();
	public int getNumeroParticiones();
	public ArrayList<Particion> crearParticiones(Datos datos);
}