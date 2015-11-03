package particionado;

import java.util.ArrayList;

import Datos.Datos;

public interface EstrategiaParticionado {
	static final int SEED = 15;	
	public String getNombreEstrategia();
	public int getNumeroParticiones();
	public ArrayList<Particion> crearParticiones(Datos datos, double porc);

}
