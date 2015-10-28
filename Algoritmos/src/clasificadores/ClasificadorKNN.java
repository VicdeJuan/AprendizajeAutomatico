package clasificadores;

import datos.Datos;
import datos.TiposDeAtributos;
import datos.dataStructure;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import particionado.AAUtils;

public class ClasificadorKNN extends Clasificador {

	HashMap<String, Double> valoresParaNominales;

	private final String STR_DELIM = "_";
	ArrayList<HashMap<String, HashMap<String, dataStructure>>> datosTrain;
	private final int numVecinos;

	public ClasificadorKNN(int numVecinos) {
		this.numVecinos = numVecinos;
	}

	@Override
	public void entrenamiento(Datos datosTrain) {
		this.datosTrain = new ArrayList(datosTrain.getDatos());
	}

	@Override
	public HashMap<String, Double> clasifica(Datos datosTest) {
		TreeMap<String, dataStructure> lineaOrden;
		HashMap<String, Double> intermedio = new HashMap<>();
		HashMap<String, Double> retval = new HashMap<>();
		String max_clase = "";
		double max_val = Double.MIN_VALUE;
		for (HashMap<String, dataStructure> line : datosTest.getDatos()) {
			Comparator comp;
			comp = new ComparatorImpl(line);
			lineaOrden = new TreeMap<>(comp);
			lineaOrden.putAll((Map<? extends String, ? extends dataStructure>) datosTrain);
			for (int i = 0; i < numVecinos; i++) {
				AAUtils.AddOrCreate(intermedio, lineaOrden.firstKey(), 1.0);
				lineaOrden.remove(lineaOrden.firstKey());
			}
			for (String clase : intermedio.keySet()) {
				if (intermedio.get(clase) > max_val) {
					max_clase = clase;
				}
			}

			AAUtils.AddOrCreate(retval, max_clase, 1);
			intermedio.clear();
			max_val = Double.MIN_VALUE;
		}
		return retval;
	}

	/**
	 * Convierte un hashmap con valores discretos a un hashmap de valores
	 * continuos.
	 *
	 * @param line : Vector de atributos
	 * @return
	 */
	private HashMap<String, Double> convertHashFromDiscToCont(HashMap<String, dataStructure> line) {
		HashMap<String, Double> retval = new HashMap<>();
		double val;
		for (Map.Entry<String, dataStructure> entry : line.entrySet()) {
			if (entry.getValue().getTipoAtributo() == TiposDeAtributos.Continuo) {
				val = (Double) entry.getValue().getVal();
			} else {
				/*Si es nominal, convertimos (aunque tal vez este caso no se de nunca)*/
				val = convertCont(entry.getKey(), (String) entry.getValue().getVal());
			}
			retval.put(entry.getKey(), val);
		}

		return retval;
	}

	/**
	 * Convierte un atributo nominal en un atributo continuo utilizando los
	 * pesos construidos en el entrenamiento.
	 *
	 * @param atributo : nombre del atributo que convertir
	 * @param valorNominal : valor nominal para ser convertido a double
	 * @return valor double correspondiente al valorNominal.
	 */
	private double convertCont(String atributo, String valorNominal) {
		double val;

		if ("Class".equals(atributo)) {
			val = 1;
		} else {
			val = valoresParaNominales.get(atributo + STR_DELIM + valorNominal);
		}
		return val;
	}

	private class ComparatorImpl implements Comparator<Entry<String, HashMap<String, dataStructure>>> {

		HashMap<String, Double> point;

		public ComparatorImpl(HashMap<String, dataStructure> point) {
			this.point = convertHashFromDiscToCont(point);
		}

		private double distancia(HashMap<String, dataStructure> p) {
			return AAUtils.restaCuadradoHash(point, convertHashFromDiscToCont(p));
		}

		@Override
		public int compare(Entry<String, HashMap<String, dataStructure>> o1, Entry<String, HashMap<String, dataStructure>> o2) {
			double a = distancia(o1.getValue());
			double b = distancia(o2.getValue());
			return Double.compare(a, b);
		}

	}

}
