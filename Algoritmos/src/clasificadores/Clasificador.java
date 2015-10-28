
package clasificadores;
import datos.Datos;
import java.io.File;
import static java.lang.System.exit;
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

	static final String DEFAULT_CLASIF = "APriori";
	static final Boolean DEFAULT_LAPLACE = false;
	static final int DEFAULT_KNN = 7;
	static final int DEFAULT_EPOC = 7;
        static final int DEFAULT_ETA = 1;
	static final int DEFAULT_VAL = 10;

	static final String NAIVEBAYES = "Bayes";
	static final String VECINOSPROXIMOS = "KNN";
	static final String REGRESIONLOGISTICA = "RegLogistica";
	static final String APRIORI = "APriori";

	private static void printHelp(){
		String usage =	"Ayuda:\n\t ./Clasificador fichero [-c clasificador] [-l laplace] [-num numK] [-eta eta] [-val validacion]\n\n";
		usage += "\t donde:\n";
		usage += "\t\t clasificador : \t "+APRIORI +" | "+NAIVEBAYES+" (para NaiveBayes) | "+VECINOSPROXIMOS+" (para vecinos proximos) | "+REGRESIONLOGISTICA+" (para Regresión Logística). \n";
		usage += "\t\t\t por defecto -> "+DEFAULT_CLASIF+".\n";
		usage += "\t\t laplace :\t 1 | 0 -> indicando si aplicar la corrección de Laplace o no (Sólo para Naive Bayes).\n";
		usage += "\t\t numK :\t natural -> número de épocas (en regresión logística) o de vecinos (en KNN) a utilizar. \n\t\t\tLos valores por defecto son: \n";
		usage += "\t\t\t\teta :\t natural -> valor eta a utilizar en el entrenamiento del clasificador regresión logística.\n";
		usage += "\t\t\t\tepocas -> "+ DEFAULT_EPOC +" .\n";
                usage += "\t\t\t\tvecinos -> "+ DEFAULT_KNN +".\n";
		usage += "\t\tvalidacion : natural -> número de particiones.\n\t\t\t Valor por defecto:\t "+ DEFAULT_VAL +" .\n\t\t\t valor 1 implica la utilización de Validación simple.\n";

		System.out.println(usage);

	}

	private static void printErrores(String clasificador, Boolean laplace, int numK, int eta, int val, ArrayList<Double> errores){
                System.out.println("La tasa de error producida por el clasificador "+clasificador+" ha sido (para cada iteracion): \n");
                int i = 0;
                double acum=0;
                for (double err : errores){
                     System.out.printf("%.2f%%\t",err);
                    acum +=err;
                    i++;
                }
                System.out.println("\n");
                System.out.printf("La tasa de error media ha sido: \n\t %.2f%%\n",acum/i);
		
	}

	public static void main(String []args) {

		/**
		 * Procesamos los argumentos:
		 */

		int numArgs = args.length;
		if (numArgs == 0){
			System.out.println("Debe especificar el fichero a analizar\n");
			printHelp();
                        exit(-1);
		}
		String fichero = args[0];
		File f = new File(fichero);
		if (!f.exists()){
			System.out.printf("El fichero %s no existe\n", fichero);
			exit(-1);
		}
                String clasificador = DEFAULT_CLASIF;
		Boolean laplace = DEFAULT_LAPLACE;
		int numK = DEFAULT_KNN;
		int val = DEFAULT_VAL;
                int eta = DEFAULT_ETA;

		for (int i = 0; i < numArgs-1; i++){
			switch (args[i]) {
				case "-c":
					clasificador = args[i+1];
					break;
				case "-l":
					if (args[i+1].equals("1"))
						laplace = true;
					break;
				case "-num":
					numK = Integer.parseInt(args[i+1]);
					break;
				case "-val":
					val = Integer.parseInt(args[i+1]);
					break;
                                case "-eta":
                                        eta = Integer.parseInt(args[i+1]);
                                        break;
				default:
					break;
			}
		}

		/**
		 * Fin de procesado de argumentos
		 **/

		Datos d = Datos.cargaDeFichero(fichero);
		EstrategiaParticionado part;
		if (val == 1)
			part = new ValidacionSimple();
		else
			part = new ValidacionCruzada(val);

		Clasificador c = null;
		switch (clasificador){
			case APRIORI:
				c = new ClasificadorAPriori();
				break;
			case NAIVEBAYES:
				c = new ClasificadorNaiveBayes(laplace);
				break;
			case VECINOSPROXIMOS:
				c = new ClasificadorKNN(numK);
				break;
			case REGRESIONLOGISTICA:
				c = new RegresionLogistica(eta,numK);
				break;
			default:
				System.out.println("Clasificador NO reconocido. Por favor lea el mensaje de ayuda.");
				printHelp();
				exit(-1);
		}

		ArrayList<Double> errores = Clasificador.validacion(part, d, c);
		printErrores(clasificador,laplace,numK,eta,val,errores);
	}


}
