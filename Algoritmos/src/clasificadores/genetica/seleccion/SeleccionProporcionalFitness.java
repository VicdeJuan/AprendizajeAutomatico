package clasificadores.genetica.seleccion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import clasificadores.genetica.Individuo;
import clasificadores.genetica.Poblacion;
import static particionado.EstrategiaParticionado.SEED;


public class SeleccionProporcionalFitness implements Seleccion {

	@Override
	public Poblacion seleccionar(Poblacion P) {
		if (!P.isFitnessSetted())
			return P;
		Poblacion toret = new Poblacion(P);

		double[] fitnesses = new double[P.getSize()];
		double[] randoms = new double[P.getSize()];
		ArrayList<Individuo> individuos = P.getIndividuos();
		ArrayList<Individuo> toretList = new ArrayList<>(P.getSize());
		double acum = 0;
		Random r = new Random(SEED);
		// Creamos las variables necesarias;
		for (int i = 0; i < P.getSize();i++){
			fitnesses[i] = individuos.get(i).getFitness();
			acum += fitnesses[i];
			randoms[i] = r.nextDouble();
		}
		randoms[P.getSize()-1] = 1;
		
		if(acum == 0)
			return P;
		
		// Ordenamos por eficiencia de lo siguiente
		Arrays.sort(randoms);
		// Normalizamos, ya que random sólo devuelve entre 0 y 1.
		for (int i = 0; i < P.getSize();i++){
				fitnesses[i] = fitnesses[i] / acum;
				if (i>0)
					fitnesses[i] += fitnesses[i-1];
		}
		fitnesses[P.getSize()-1] = 1;
		
		// binarySearch busca binariamente y si no lo encuentra, devuelve el índice donde debería haber estado.
		for(int i1=0;i1<P.getSize();i1++){
			int idx = Arrays.binarySearch(fitnesses, randoms[i1]);
			if (idx < 0)
				idx = -(idx+1);
			toretList.add(new Individuo(individuos.get(idx)));
		}
		
		toret.setIndividuos(toretList);
		return toret;
	
	}
}
