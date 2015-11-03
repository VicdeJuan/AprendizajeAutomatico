package particionado;

import java.util.ArrayList;
import java.util.Random;

import Datos.Datos;

public class ValidacionCruzada implements EstrategiaParticionado {

	private int numParticiones=0;
	
	@Override
	//Devuelve el nombre de la estrategia de particionado
	public String getNombreEstrategia(){
		return "Validacion Cruzada";
	}
	
	@Override
	//Devuelve el numero de particiones
	public int getNumeroParticiones(){
		return numParticiones;
	}
	
	protected static Random randomgenerator = new Random(SEED);
	
	public static int randInt(int min, int max) {

	    return randomgenerator.nextInt((max - min) + 1) + min;

	}
	
	@Override
	// Crea particiones segun el metodo de validacion cruzada simple
	// El conjunto de entrenamiento se crea con las numPart-1 particiones
	// y el de test con la particion restante
	
	public ArrayList<Particion> crearParticiones(Datos datos, double k){
		
		ArrayList<Particion> particiones= new ArrayList<Particion>();
		ArrayList<ArrayList<Integer>> partTotal = new ArrayList<ArrayList<Integer>>();
		
		int res = datos.getNumDatos();
		int i=0;
		int j=0;
		
		//Inicializamos las particiones
		for(i=0; i<k; i++){
			partTotal.add(new ArrayList<Integer>());
		}
		
		
		//Creamos array de enteros de res valores
        int[] aux = new int[res];
        int[] aleatPart = new int[res];
        int numAux;
        Random r= randomgenerator;

        //Llenamos el array aux con todos los índices
        for(i=0; i<res; i++){
            aux[i] = i;
        }
        
        for(i=0; i<res; i++){
            numAux = r.nextInt(res-i);
            aleatPart[i]=aux[numAux];
            aux[numAux] = aux[res-i-1];
        }
		
		int num = (int) Math.ceil((double)(res/k));
		
		//Sacamos las primeras k-1 particiones
		i=0;
		for(j=0; j<(k-1); j++){
			while(partTotal.get(j).size()<num){
				partTotal.get(j).add(aleatPart[i]);
				i++;
			}
		}
		
		//La última particion
		for(int n=i; n<res; n++){
			partTotal.get(j).add(aleatPart[n]);
		}
		
			
		for(j=0; j<k; j++){
			ArrayList<Integer> train = new ArrayList<Integer>();
			ArrayList<Integer> test = new ArrayList<Integer>();
			for(i=0; i<(k-1); i++){
				train.addAll(partTotal.get((i+j)%(int)k));
			}
			
			test.addAll(partTotal.get((i+j)%(int)k));
			particiones.add(new Particion(train,test));
		}
		
		
		return particiones;
	}
}
