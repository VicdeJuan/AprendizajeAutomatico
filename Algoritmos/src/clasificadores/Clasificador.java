
package clasificadores;
import datos.Datos;
import java.util.ArrayList;
import java.util.HashMap;
import particionado.*;


abstract public class Clasificador {
	//Métodos abstractos que se implementan en cada clasificador concreto
	abstract public void entrenamiento (Datos datosTrain);
	abstract public HashMap<String,Double> clasifica (Datos datosTest);
        
	// Obtiene el numero de aciertos y errores para calcular la tasa de fallo
	public double error (Datos datos, Clasificador clas) {
		HashMap<String, Double> clasClases = clas.clasifica(datos);
		HashMap<String, Double> realClases = datos.getClases();
                double dif = 0;
                double real,clasificados;
                for (String key : realClases.keySet()){
                    real = clasificados = 0;
                    if (realClases.containsKey(key))
                            real = realClases.get(key);
                    if (clasClases.containsKey(key))
                            clasificados = clasClases.get(key);
                    dif += Math.abs(real - clasificados);
                    
                }
                /* 
                    Dividimos entre 2 para no contabilizar doble los errores. 
                    Si una clase debería ser + y es -, es 1 único error, 
                    aunque dif se incrementa en 2, la del + que no es menos y la del - que no es +
                */
                
		return (dif/2)/datos.getNumDatos();
	}
        
	// Realiza una clasificacion utilizando una estrategia de particionado determinada
	public static ArrayList<Double> validacion(EstrategiaParticionado part, Datos datos,
	Clasificador clas) {
		/*Creamos las particiones siguiendo la estrategia llamando a datos.creaParticiones
		Para validación cruzada: En un bucle hasta nv entrenamos el clasf con la particion 
                de train i (extraerDatosTrain)
		
		y obtenemos el error en la particion test de i (extraerDatosTest)
		Para validación porcentual entrenamos el clasf con la partición de train
		(extraerDatosTrain) y obtenemos el error en la particion test (extraerDatosTest) */
                
                ArrayList<Double> toret = new ArrayList<>();
                ArrayList<Particion> particiones = part.crearParticiones(datos);
                //La última de las particiones es la de test.
                Particion test = particiones.get(part.getNumeroParticiones()-1);
                
                for (int i = 0; i< part.getNumeroParticiones();i++){
                    clas.entrenamiento(datos.extraeDatosTrain(particiones.get(i)));
                    
                    toret.add(clas.error(datos.extraeDatosTest(test), clas));
                }
            
		return toret;
	}
	public static void main(String []args) {
		Datos d = Datos.cargaDeFichero(args[0]);
		EstrategiaParticionado part;
                part = new ValidacionSimple();
		Clasificador c = new ClasificadorAPriori();
		ArrayList<Double> errores = Clasificador.validacion(part, d, c);
		System.out.println(errores);
	}
        

}
