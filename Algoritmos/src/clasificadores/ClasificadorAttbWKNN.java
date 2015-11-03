package clasificadores;

import java.util.ArrayList;
import java.util.Collections;

import comparadores.ComparadorEuclideo;

import Datos.Datos;
import comparadores.ComparadorPonderado;
import java.util.Random;


public class ClasificadorAttbWKNN extends Clasificador {
	int k;
	protected Random randomgenerator = new Random(SEED);
	public ClasificadorAttbWKNN(int numK) {
		k = numK;
		// TODO Auto-generated constructor stub
	}
	

	Datos datosTrain;
	double[] pesos;

	@Override
	public void entrenamiento(Datos datostrain) {
				
		datosTrain = datostrain;
		pesos = new double[datostrain.getTipoAtributos().size()-1];
		int dimension = datosTrain.getNombreAtrs().size()-1;
		int numDatos = datosTrain.getNumDatos();
		int[] clasesEnteras = new int[numDatos];
		double[] w = new double[dimension];
		double[] x = new double[dimension];
		int predicted;
		int epocas = 36;
		double cteApren = 0.75;
		int numAtr = datosTrain.getNombreAtrs().size();
		
		//Asociamos el 0 a la clase 1 y el 1 a la clase 2:
		for (int i=0; i < numDatos; i++){
			if (datosTrain.getClases().get(0).equals(datosTrain.getDatos().get(i).get(numAtr-1))){
				clasesEnteras[i]=0;
			}else{
				clasesEnteras[i]=1;
			}
		}
		
		/**
		 * Completar la ponderación de los pesos
		 */
		for (int i=0; i<pesos.length ; i++){
			w[i] = randomgenerator.nextDouble();  // Esto da valores entre -1.0 y 1.0 excluido el 1.0
		}
		System.out.println("antes de épocas."+numDatos);
		ArrayList<ArrayList<String>> newd =new ArrayList<ArrayList<String>>(1);
		for (int j=0; j<epocas; j++){
			for (int i=0; i<numDatos;i++){
				System.out.print(i+",");
				
				//Inicializamos el vector x_i:
				x[0] = 1;
				for (int k=0; k<dimension; k++){
					x[k] = Double.parseDouble(datosTrain.getDatos().get(i).get(k));
					//DEBUG
					//System.out.println(x[k]);
				}				
				

				// Clase predicha:
				newd.clear();
				newd.add(datosTrain.getDatos().get(i));
				Datos d = new Datos(newd,datosTrain.getTipoAtributos(),datosTrain.getClases(),datosTrain.getNombreAtrs());
				String pred_str = this.clasifica(d).get(0);
				predicted = datosTrain.getClases().get(0).equals(pred_str) ? 0 : 1;
				
				
						
				//Actualizamos w[i] = w[i]-cteApr*(predicha-real)*x_i
				for (int k=0; k<dimension; k++){
					w[k] = w[k]-cteApren*(predicted-clasesEnteras[i])*x[k];
				}
				
				//DEBUG
				//System.out.println("---------");
				//for (int k=0; k<dimension; k++){
				//	System.out.println(w[k]);
				//}
				pesos = w;
			}
		}
		
		
		//DEBUG
		System.out.println("");
		System.out.println("Despues de épocas.");
	
		System.out.println("Pesos");
		for (double p : pesos){
			System.out.print(p+",");
		}
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
			Collections.sort(datosTrain.getDatos(), new ComparadorPonderado(fila, datosTrain,pesos));
			
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
		//System.out.println("Pesos: " + pesos);
		return prediccion;
		
	}
	


}

