package particionado;

import java.util.ArrayList;
import java.util.Random;

import Datos.Datos;

public class ValidacionSimple implements EstrategiaParticionado{
	private int numParticiones=0;
	
	
	public ValidacionSimple(){
		this.numParticiones = 1;
	}
	
	
	@Override
	
	//Devuelve el nombre de la estrategia de particionado
	public String getNombreEstrategia(){
		return "Validacion Simple";
	}
	
	@Override
	//Devuelve el numero de particiones
	
	public int getNumeroParticiones(){
		return numParticiones;
	}
	
	
	
	protected static Random randomgenerator = new Random(SEED);
	public static int randInt(int min, int max) {

	    Random rand = randomgenerator;
	    return rand.nextInt((max - min) + 1) + min;

	}
	
	@Override
	// Crea particiones segun el metodo tradicional
	// de division de los datos segun el porcentaje
	// deseado. Devuelve una sola partición con un 
	// conjunto de train y otro de test
	
	public ArrayList<Particion> crearParticiones(Datos datos, double porcTrain){
		
		ArrayList<Particion> particiones= new ArrayList<Particion>();
		ArrayList<Integer> train = new ArrayList<Integer>();
		ArrayList<Integer> test = new ArrayList<Integer>();
		
		/////////////////////////para depurar////////////////////////////
		int res = datos.getNumDatos();
		
		
		int numTrain = (int) Math.ceil((double)(res*porcTrain/100));
		
		while(train.size()<numTrain){
			Integer numEleg = randInt(0,res-1);
			if(!train.contains(numEleg))
				train.add(numEleg);
		}
		
		for(Integer i=0; i< res;i++)
			if(!train.contains(i))
				test.add(i);
		
		particiones.add(new Particion(train,test));
		
		
		return particiones;
	}
}
