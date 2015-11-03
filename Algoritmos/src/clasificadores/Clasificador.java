package clasificadores;
import java.util.ArrayList;
import java.util.Scanner;

import Datos.Datos;
import java.util.Random;
import particionado.EstrategiaParticionado;
import particionado.Particion;
import particionado.ValidacionCruzada;
import particionado.ValidacionSimple;

abstract public class Clasificador {

	//Métodos abstractos que se implementan en cada clasificador concreto
	abstract public void entrenamiento (Datos datosTrain);
	abstract public ArrayList<String> clasifica (Datos datosTest);
	
	static final int SEED = 15;	
	// Obtiene el numero de aciertos y errores para calcular la tasa de fallo
	public double error (Datos datostest, ArrayList<String> Resclas) {
			
		    int numA = 0;
		    int numF = 0;
		    int numFila = 0;
		    
		    // pos,neg,pos,pos,neg   --> después de clasificar
			
			
			for(ArrayList<String> fila : datostest.getDatos()){
					String clase = datostest.obtClaseFila(fila);
					if(Resclas.get(numFila).equals(clase))	
						numA++;
					else numF++;
					numFila++;
			}
			return (double) numF/(numF+numA);
	}
	
	// Realiza una clasificacion utilizando una estrategia de particionado determinada
	public static ArrayList<Double> validacion(EstrategiaParticionado part, Datos datos,
			Clasificador clas, double porc, boolean laplace) {
		
		
		//_____________________Tipificación de datos__________________________________
		int nAtrib = datos.getTipoAtributos().size();
		int numDatos = datos.getNumDatos();
		double[] medias = new double[nAtrib];
		double[] varianzas = new double[nAtrib];
				
		//Obtenemos la media y la varianza de cada atributo continuo:
		for (int i=0; i<nAtrib-1; i++){
			if(datos.isContinuos(datos.getNombreAtrs().get(i))){
				medias[i] = datos.media(datos, datos.getNombreAtrs().get(i));
				varianzas[i] = datos.varianza(datos, datos.getNombreAtrs().get(i),medias[i]);
			}
		}
		
		//DEBUG
		//System.out.println(datos.getDatos());
		//System.out.println("Numero de atributos: "+nAtrib);
		//System.out.println("Numero de datos: "+numDatos);
		//for (int i=0; i<nAtrib; i++){
		//	System.out.println("Atributo "+i+": Media "+medias[i]+" - Varianza "+varianzas[i]);
		//}
		//System.out.println("Dimension de la clase: "+datos.getClases().size());
		//for (int i=0; i<datos.getClases().size();i++){
		//	System.out.println("Tipo de clase: "+datos.getClases().get(i)+" - Apariciones: "+datos.getNumClases().get(i));
		//}
		//for(int i=0; i<nAtrib; i++){
		//	if(datos.isContinuos(datos.getNombreAtrs().get(i)))
		//		System.out.println("Tipo atributo "+i+": "+datos.getTipoAtributos().get(i));
		//}
		
		
		//Tipificamos los datos continuos:	
		for (int i=0; i< nAtrib-1; i++){  //Recorremos cada atributo
			if(datos.isContinuos(datos.getNombreAtrs().get(i))){ //Si el dato es continuo...
				for (int j=0; j<numDatos ; j++){ //Recorremos todos los datos tipificando el atributo anterior
					if (varianzas[i]!=0){
						datos.getDatos().get(j).set(i, 
								Double.toString((Double.parseDouble(datos.getDatos().get(j).get(i))-medias[i])/Math.sqrt(varianzas[i])));
					}				
				}
			}
		}
		
		//DEBUG
		//System.out.println("Datos tipificados :"+datos.getDatos());
		
		//____________________________________________________________________________
		
		
		//__________Proceso de particionamiento, entrenamiento y clasificación_______
		ArrayList<Double> erroresClas= new ArrayList<Double>();
		Datos datosTest;
		Datos datosTrain;
		ArrayList <String> resClas;
		ArrayList<Particion> arrayPart = part.crearParticiones(datos,porc);
		
		if(part.getNombreEstrategia()=="Validacion Simple"){
			
			datosTrain = datos.extraeDatosTrain(arrayPart.get(0));
			datosTest = datos.extraeDatosTest(arrayPart.get(0));
			clas.entrenamiento(datosTrain);
			resClas = clas.clasifica(datosTest);
		    erroresClas.add(clas.error(datosTest, resClas));
		    
		}else{
			
			for(Particion p : arrayPart){
				datosTrain = datos.extraeDatosTrain(p);
				datosTest = datos.extraeDatosTest(p);
				clas.entrenamiento(datosTrain);
				resClas = clas.clasifica(datosTest);
			    erroresClas.add(clas.error(datosTest, resClas));

		}
	}
		return erroresClas;
		
		//____________________________________________________________________________
		
}
	


public static void main(String []args) {
		
	 
	 Datos d = Datos.cargaDeFichero("wdbc.data");
	 Clasificador c = new ClasificadorAttbWKNN(7);
	 EstrategiaParticionado part;
	 int porc;
	 boolean laplace;
	 ArrayList<Double> errores;
     
	 Scanner sc = new Scanner(System.in);
     System.out.println("Introduzca el tipo de validacion, S(simple) o C(cruzada)");
	 String val = sc.nextLine();
	 if(val.equals("S")){
		 part =  new ValidacionSimple ();
		 System.out.println("Introduzca el porcentaje de train (entre cero y cien)");
		  porc = sc.nextInt();
		  System.out.println("El porcentaje de error en la única partición es:");
	 }else{
		 part =  new ValidacionCruzada ();
		 System.out.println("Introduzca el numero k de particiones");
		 porc = sc.nextInt();
		 System.out.println("Para WKNN por atributos con 10 particiones y 7 vecinos");
		 System.out.println("El porcentaje de errores por particion es el siguiente:");
	 }
	 errores = Clasificador.validacion(part, d, c,porc,true);

	 sc.close();
	 
	 for(Double err : errores)
		 System.out.println(err);

	 }


}