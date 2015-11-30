package clasificadores.genetica.seleccion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import junit.framework.Assert;
import clasificadores.genetica.Individuo;
import clasificadores.genetica.Poblacion;
import static particionado.EstrategiaParticionado.SEED;


public class SeleccionProporcionalFitness implements Seleccion {

	@Override
	public Poblacion seleccionar(Poblacion P) {

		//Copy & Paste
		Poblacion p1 = P;
		Poblacion toret = new Poblacion(p1.getSize(),p1.getNumReglas(),p1.getNumAtributos(),p1.getProbMutacion(),p1.getProbCruce(),p1.getEstrategiaReemplazo(),true,p1.getNumReglasAleat(),p1.getEstrategiaSeleccion());
		toret.setNumReglasAleat(P.getNumReglasAleat());
		
		ArrayList<Individuo> individuos = P.getIndividuos();
		ArrayList<Individuo> toretList = new ArrayList<>(P.getSize());
		
		int n = (int) Math.round(P.getElitismo()*P.getSize())+1;

		int N = P.getSize()-n;
		double[] fitnesses = new double[N];
		double[] randoms = new double[N];
		
		
		double acum = 0;
		Random r = new Random(SEED);
		// Creamos las variables necesarias;
		for (int i = 0; i < N;i++){
			fitnesses[i] = individuos.get(i).getFitness();
			acum += fitnesses[i];
			randoms[i] = r.nextDouble();
		}
		randoms[N-1] = 1;
		
		if(acum == 0)
			return P;
		
		// Ordenamos por eficiencia de lo siguiente
		Arrays.sort(randoms);
		// Normalizamos, ya que random s�lo devuelve entre 0 y 1.
		for (int i = 0; i < N;i++){
				fitnesses[i] = fitnesses[i] / acum;
				if (i>0)
					fitnesses[i] += fitnesses[i-1];
		}
		fitnesses[N-1] = 1;
		
		// Añadimos los élites:
		for(int i = 0; i<n;i++)
			toretList.add(new Individuo(individuos.get(i)));
		
		// binarySearch busca binariamente y si no lo encuentra, devuelve el �ndice donde deber�a haber estado.
		for(int i1=0;i1<N;i1++){
			int idx = Arrays.binarySearch(fitnesses, randoms[i1]);
			if (idx < 0)
				idx = -(idx+1);
			toretList.add(new Individuo(individuos.get(idx)));
		}
		
		toret.setIndividuos(toretList);
		toret.setSize(toretList.size());
		toret.setElitismo(P.getElitismo());
		toret.OrdenarPorFitness();
		return toret;
	
	}
	
	@Override
	public String toString() {
		return "SeleccionProporcionalAlFitness";
	}
}
