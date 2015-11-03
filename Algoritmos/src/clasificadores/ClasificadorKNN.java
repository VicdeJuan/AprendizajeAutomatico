package clasificadores;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import Datos.Datos;
import comparadores.*;

public class ClasificadorKNN extends Clasificador {
	
	Datos datosTrain;
	int k;

	public ClasificadorKNN(int numK){
		k = numK;
	}

	@Override
	public void entrenamiento(Datos datostrain) {
				
		datosTrain = datostrain;
		//DEBUG
		//System.out.println("Datos entrenamiento: "+datosTrain.getDatos());
	
	}

	@Override
	public ArrayList<String> clasifica(Datos datostest) {
		
		//DEBUG
		//System.out.println("Datos de test: "+datostest.getDatos());
		
		int maximo;
		int indiceMaximo;
		ArrayList<String> prediccion = new ArrayList<String>();
		int dimClase = datosTrain.getClases().size();
		int[] decidirClase =new int[dimClase];
		
		for(int i=0; i<dimClase; i++){
			decidirClase[i] = 0;
		}
		
				
		for(ArrayList<String> fila: datostest.getDatos()){
			Collections.sort(datosTrain.getDatos(), new ComparadorMinkowski(fila, datosTrain));
			
			//DEBUG
			//System.out.println("__________Ordenamos para punto :"+fila);
			//System.out.println("__________Obtenemos: "+datosTrain.getDatos());
			
			//reinicializamos variables para obtener la clase del vecino más próximo:
			maximo=0;
			indiceMaximo=0;
			for(int i=0; i<dimClase; i++){
				decidirClase[i] = 0;
			}
			
			/**
			 * Por cada datos de los k que miramos, sumamos uno a la correspondiente posición de la clase 
			 * en el array decidirClase (que es binario).
			 */
			for(int i=0; i<k; i++){
				for(int j=0; j<dimClase; j++){
					if(datosTrain.getDatos().get(i).get(fila.size()-1).equals(datosTrain.getClases().get(j))){
						decidirClase[j]++;
					}
				}
			}
			
			//DEBUG
			//for(int i=0; i<dimClase; i++){
			//	System.out.println("Clase: "+datosTrain.getClases().get(i)+": "+decidirClase[i]);
			//}
						
			
			for(int i=0; i<dimClase; i++){
				if (decidirClase[i] > maximo){
					maximo = decidirClase[i];
					indiceMaximo = i;
				}
			}
						
			prediccion.add(datosTrain.getClases().get(indiceMaximo));
			//DEBUG
			//System.out.println("Indice maximo es: "+indiceMaximo);
			//System.out.println(prediccion);
		}
		//System.out.println(prediccion);
		return prediccion;
		
	}
	
		
	

}



