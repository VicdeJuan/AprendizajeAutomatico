package clasificadores;

import java.util.ArrayList;
import Datos.Datos;
import java.util.Random;

public class ClasificadorRegLog extends Clasificador {
	
	Datos datosTrain;
	int epocas;
	double cteApr;
	double[] wFinal;
	
	
	protected Random randomgenerator = new Random(SEED);
	public ClasificadorRegLog(int ep, double ca){
		epocas = ep;
		cteApr = ca;
	}

	@Override
	public void entrenamiento(Datos datostrain) {
				
		datosTrain = datostrain;
		int numAtr = datosTrain.getNombreAtrs().size();
		int dimension = datosTrain.getNombreAtrs().size()+1-1;
		int numDatos = datosTrain.getNumDatos();
		int[] clasesEnteras = new int[numDatos];
		double[] w = new double[dimension];
		double[] x = new double[dimension];
		double aux;
		
		//Asociamos el 0 a la clase 1 y el 1 a la clase 2:
		for (int i=0; i < numDatos; i++){
			if (datosTrain.getClases().get(0).equals(datosTrain.getDatos().get(i).get(numAtr-1))){
				clasesEnteras[i]=0;
			}else{
				clasesEnteras[i]=1;
			}
		}
		
		//Generamos w aleatorio con componentes entre -1 y 1:
		for (int i=0; i<dimension ; i++){
			w[i] = randomgenerator.nextDouble()* 2 -1;  // Esto da valores entre -1.0 y 1.0 excluido el 1.0
		}
		
		//DEBUG
		//System.out.println("---------");
		//for (int k=0; k<dimension; k++){
		//	System.out.println(w[k]);
		//}
		
		for (int j=0; j<epocas; j++){
			for (int i=0; i<numDatos;i++){
				
				//Inicializamos el vector x_i:
				x[0] = 1;
				for (int k=1; k<dimension; k++){
					x[k] = Double.parseDouble(datosTrain.getDatos().get(i).get(k-1));
					//DEBUG
					//System.out.println(x[k]);
				}				
				
				//Calculamos producto escalar w*x_i
				aux = 0;
				for (int k=0; k<dimension; k++){
					aux = aux + w[k]*x[k];
				}
				//DEBUG
				//System.out.println("Valor producto escalar: "+aux);
				
				//Calculamos V_i(w*x_i)
				aux = 1/(1+Math.pow(Math.E, -aux));
				//DEBUG
				//System.out.println("Valor V_i: "+aux);
				
				//Actualizamos w = w-cteApr*(V_i-clase_i)*x_i
				for (int k=0; k<dimension; k++){
					w[k] = w[k]-cteApr*(aux-clasesEnteras[i])*x[k];
				}
				
				//DEBUG
				//System.out.println("---------");
				//for (int k=0; k<dimension; k++){
				//	System.out.println(w[k]);
				//}
				
			}
		}
		
		wFinal = w;
		
		//DEBUG
		//System.out.println("---------");
		//for (int k=0; k<dimension; k++){
		//	System.out.println(wFinal[k]);
		//}
		
		
		//DEBUG
		//System.out.println("Datos entrenamiento: "+datosTrain.getDatos());
	
	}

	@Override
	public ArrayList<String> clasifica(Datos datostest) {
		
		//DEBUG
		//System.out.println("Datos de test: "+datostest.getDatos());
		
		ArrayList<String> prediccion = new ArrayList<String>();
		int dimension = datosTrain.getNombreAtrs().size()+1-1;
		double[] x = new double[dimension];
		double aux;
		
						
		for(ArrayList<String> fila: datostest.getDatos()){
			
			//Inicializamos el vector x_i:
			x[0] = 1;
			for (int k=1; k<dimension; k++){
				x[k] = Double.parseDouble(fila.get(k-1));
			}	
			
			//Calculamos producto escalar w*x_i
			aux = 0;
			for (int k=0; k<dimension; k++){
				aux = aux + wFinal[k]*x[k];
			}
			
			//Calculamos V_i(w*x_i)
			aux = 1/(1+Math.pow(Math.E, -aux));
			
			//Predecimos según el valor de V_i
			if(aux < 0.5){
				prediccion.add(datosTrain.getClases().get(0));
			}else{
				prediccion.add(datosTrain.getClases().get(1));
			}
			
			//DEBUG
			//System.out.println(prediccion);
		}
		//System.out.println(prediccion);
		return prediccion;
		
	}

}