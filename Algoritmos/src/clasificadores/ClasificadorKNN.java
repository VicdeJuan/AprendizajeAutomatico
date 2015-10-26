package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class ClasificadorKNN extends Clasificador {

    private int numTotalDatos = 0;
    private int posicionClase = 0;
    private Map<String, Map<String, ArrayList<Double>>> tablaFinalContinuo = null;
    private Map<String, Double> clasesAparicion = null;
    private Map<String, Double> mediaTotal = null;
    private Map<String, Double> varianzaTotal = null;
    private int numVecinos = 0;
    private ArrayList<String[]> datosTrainNormalizados = null;

    public ClasificadorKNN(int numVecinos) {
        this.clasesAparicion = new TreeMap();
        this.tablaFinalContinuo = new TreeMap();
        this.mediaTotal = new TreeMap();
        this.varianzaTotal = new TreeMap();
        this.datosTrainNormalizados = new ArrayList<>();
        this.numVecinos = numVecinos;
    }

    @Override
    public void entrenamiento(Datos datosTrain) {/*

        int posicion = 0;
        for (String s : datosTrain.getNombreCampos()) {
            //Metemos el nombre en todas las tablas, excepto en la de nominal o continuo. Lo sacamos con el tipo de atributo. Ponemos posicion para ir moviendonos. Nos ahorramos un absurdo y largo for.
            this.tablaFinalContinuo.put(s, new TreeMap());
            posicion++;
            this.mediaTotal.put(s, new Double(0));
            this.varianzaTotal.put(s, new Double(0));
        }

        for (String s : datosTrain.getNombreClases()) {
            this.clasesAparicion.put(s, 0.0);
        }

        this.numTotalDatos = datosTrain.getNumeroDatos();
        //La usaremos para acceder directamente a ese elemento del arraylist de los datos.
        this.posicionClase = datosTrain.getNumeroAtributos();

        for (String[] s : datosTrain.getDatos()) {
            //enbuclamos para cada atributo de la fila de los datos leida en el for anterior.
            for (int i = 0; i < datosTrain.getNumeroAtributos(); i++) {
                String nombreAtributo = datosTrain.getNombreCampos().get(i);
                if (!this.tablaFinalContinuo.get(nombreAtributo).containsKey(s[posicionClase])) { //no esta la clase
                    this.tablaFinalContinuo.get(nombreAtributo).put(s[posicionClase], new ArrayList<>());
                }
                this.tablaFinalContinuo.get(nombreAtributo).get(s[posicionClase]).add(Double.parseDouble(s[i])); // buying -> unacc  5.1
            }
            //Sumamos 1 a la aparicion de esta clase.
            this.clasesAparicion.put(s[posicionClase], this.clasesAparicion.get(s[posicionClase]) + 1.0);
        }
        
        for (Iterator it = this.tablaFinalContinuo.entrySet().iterator(); it.hasNext();) {
            //Clave nombre de atributo, valor el resto del mapa con el valor del atributo, clases y frecuencias.
            Entry<String, Map<String, Map<String, Double>>> e1 = (Entry<String, Map<String, Map<String, Double>>>) it.next();
            ArrayList<Double> valorArrayList = new ArrayList<>();
            double media = 0.0;
            double varianza = 0.0;
            for (Iterator it3 = this.clasesAparicion.entrySet().iterator(); it3.hasNext();) {
                //Clave nombre de clase, valor frecuencia.
                Entry<String, Double> e3 = (Entry<String, Double>) it3.next();
                valorArrayList.addAll(this.tablaFinalContinuo.get(e1.getKey()).get(e3.getKey()));
            }
            for (Double d : valorArrayList) {
                media += d;
                varianza += Math.pow(d, 2);
            }

            int tamanioArrayListDoubles = valorArrayList.size();
            media /= tamanioArrayListDoubles;
            varianza = (varianza / tamanioArrayListDoubles) - Math.pow(media, 2);

            this.mediaTotal.replace(e1.getKey(), media);
            this.varianzaTotal.replace(e1.getKey(), varianza);
        }

        //Calculo de los valores normalizados.
        //leemos los datos de training
        for (String[] lineaTraining : datosTrain.getDatos()) {
            //leemos cada valor de la linea del train.
            String[] nuevaLineaNormalizada = new String[datosTrain.getNumeroAtributos() + 1];
            for (int i = 0; i < datosTrain.getNumeroAtributos() + 1; i++) {
                //s[i] es el valor
                if (i != this.posicionClase) {
                    String nombreAtributo = datosTrain.getNombreCampos().get(i);
                    double valorNormalizado = (Double.parseDouble(lineaTraining[i]) - this.mediaTotal.get(nombreAtributo)) / Math.sqrt(this.varianzaTotal.get(nombreAtributo));
                    nuevaLineaNormalizada[i] = String.valueOf(valorNormalizado);
                } else {
                    nuevaLineaNormalizada[i] = lineaTraining[i];
                }
            }
            this.datosTrainNormalizados.add(nuevaLineaNormalizada);
        }
        
        //Pasos que se hacen en CLASIFICA
        //2.Calcular Distancias
        //3. Obtenemos los K vecinos mas proximos
        //4. Obtenemos las clases de los K vecinos mas proximos
    */}

    /**
     *
     * @param datosTest
     * @return
     */
    @Override
    public ArrayList<String> clasifica(Datos datosTest) {
/*

        ArrayList<String[]> datosTestNormalizados = new ArrayList<>();
        ArrayList<Map<Integer, Double>> distanciasPares2 = new ArrayList<>();
*/
        ArrayList<String> clasesDevueltas = new ArrayList();
/*
	//Normalizamos el test con los datos de las medias y las varianzas del Training
        for (String[] lineaTest : datosTest.getDatos()) {
            //leemos cada valor de la linea del train.
            String[] nuevaLineaNormalizada = new String[datosTest.getNumeroAtributos()];
            for (int i = 0; i < datosTest.getNumeroAtributos(); i++) {
                String nombreAtributo = datosTest.getNombreCampos().get(i);
                //s[i] es el valor
                double valorNormalizado = (Double.parseDouble(lineaTest[i]) - this.mediaTotal.get(nombreAtributo)) / Math.sqrt(this.varianzaTotal.get(nombreAtributo));
                nuevaLineaNormalizada[i] = String.valueOf(valorNormalizado);
            }
            datosTestNormalizados.add(nuevaLineaNormalizada);
        }

        //Para cada test...     
        int contador = 0;
        for (String[] lineaTest : datosTestNormalizados) {
            int indice = 0;
            distanciasPares2.add(new TreeMap<>());
            for (String[] lineaTrain : this.datosTrainNormalizados) {
                //Se calcula la distancia entre cada fila de test y todas las filas de train y se mete en el arrayList
                double distancia = 0;
                for (int i = 0; i < lineaTest.length; i++) {
                    //Distancia Euclidea
                    distancia += Math.pow((Double.parseDouble(lineaTest[i]) - Double.parseDouble(lineaTrain[i])), 2);
                }
                distancia = Math.sqrt(distancia);
		System.out.println(distancia);
                distanciasPares2.get(contador).put(indice, distancia);
                indice++;
            }
            //Vamos a obtener los K vecinos más cercanos a la linea de test que se está ejecutando.
            ArrayList<Integer> vecinos = new ArrayList<>();
            //Con esto ordenamos en funcion del valor. Obtenemos las distancias más pequeñas y la clave del Hash es el índice! :D
            Map<Integer, Double> MapaOrdenado = sortByComparator(distanciasPares2.get(contador));
            LinkedHashMap<Integer, Double> linkedHashMap = new LinkedHashMap<>();
            linkedHashMap.putAll(MapaOrdenado); //Ya está ordenado
            int z = 0;
            
            //Vamos a meter en el arrayList vecinos los K más cercanos. Cuando z = K dejamos de meter en el arrayList
            for (Iterator it = linkedHashMap.keySet().iterator(); it.hasNext();) {
            	
                if (z == this.numVecinos) {
                    break;
                }
                vecinos.add((Integer) it.next());
                z++;
            }

            //Guardamos las clases de esos vecinos.
            ArrayList<String> clases = new ArrayList();
            for (Integer i : vecinos) {
                String[] linea = this.datosTrainNormalizados.get(i);
                clases.add(linea[this.posicionClase]);
            }
            //Contamos cuantas veces aparece cada clase de esos vecinos. Ejemplo: K=3. Clases obtenidas: B B M. Esto debe devolver la clase B porque aparece más veces.
            int numVecesClase = 0;
            Set<String> apariciones = new HashSet<>(clases);
            String claseConMasApariciones = "";
            for (String clasecilla : apariciones) {
                if (Collections.frequency(clases, clasecilla) > numVecesClase) {
                    claseConMasApariciones = clasecilla;
                    numVecesClase = Collections.frequency(clases, clasecilla);
                }
            }

            clasesDevueltas.add(claseConMasApariciones);

            contador++;
        }
           */
        return clasesDevueltas;
    }

    public void imprimeDatos() {
	return;
    }


	/**
	 * Método que ordena por valor y no por clave.
	 */
    private static Map<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap) {

        // Convert Map to List
        List<Map.Entry<Integer, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sort list with comparator, to compare the Map values
        Collections.sort(list, new ComparatorImpl());

        // Convert sorted map back to a Map
        Map<Integer, Double> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static class ComparatorImpl implements Comparator<Entry<Integer, Double>> {

        public ComparatorImpl() {
        }

        @Override
        public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
            return (o1.getValue()).compareTo(o2.getValue());
        }
    }

}
