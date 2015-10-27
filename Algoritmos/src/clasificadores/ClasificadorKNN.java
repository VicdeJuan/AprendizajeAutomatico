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


	HashMap<String,HashMap<String,DataStructure>> datosTrain;
	private int numVecinos;

    public ClasificadorKNN(int numVecinos) {
        this.numVecinos = numVecinos;
    }

    @Override
    public void entrenamiento(Datos datosTrain) {
		this.datosTrain = new HashMap<>(datosTrain);
	}

    @Override
    public HashMap<String,Double> clasifica(Datos datosTest) {
		TreeMap<String,DataStructure> lineaOrden;
		HashMap<String,DataStructure> intermedio = new HashMap<>();
		HashMap<String,Double> retval = new HashMap<>();
		String max_clase;
		double max_val = Double.MinInfinity;
		for (HashMap<String,DataStructure> line : datosTest.getDatos()){
			Comparator comp = new Comparator(AAUtils.convertHashFromDiscToCont(line));
			lineaOrden = new(comp);
			lineaOrden.putAll(datosTrain);
			for (int i=0; i<numVecinos; i++){
				AAUtils.addOrCreate(intermedio,lineaOrden.firstKey().get("Class"),1);
				lineaOrden.remove(lineaOrden.firstKey());
			}
			for (String clase : intermedio.keySet()){
				if (intermedio.get(clase) > max_val){
					max_clase = clase;
				}
			}
			AAUtils.addOrCreate(retval,max_clase,1);
			intermedio.clear();
			max_val = Double.MinInfinity;
		}
		return retval;
   	}





    private class ComparatorImpl implements Comparator<Entry<String, Double>> {

		HashMap<String,double> point;

        public ComparatorImpl(HashMap<String,DataStructure> point) {
        	this.point = AAUtils.convertHashFromDiscToCont(point);
		}


		private static double distancia(HashMap<String,DataStructure> p){
			return AAUtils.reduceHash("+",AAUtils.restaCuadradoHash(point,AAUtils.convertHashFromDiscToCont(p)));
		}


        @Override
        public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
            return distancia(o1).compareTo(distancia(o2));
        }
    }


}
