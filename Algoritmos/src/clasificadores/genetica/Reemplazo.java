/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import static particionado.EstrategiaParticionado.SEED;

/**
 *
 * @author vicdejuan
 */
public abstract class Reemplazo {

	
	double elitismo = 0;

	/**
	 *
	 * @param progenitores
	 * @param vastagos
	 * @return
	 */
	public abstract Poblacion Reemplazar (Poblacion progenitores, Poblacion vastagos);
	
	/**
	 * Devuelve un subconjunto de la población p1 de acuerdo al porcentaje de 
	 * 	elitismo.  Si el porcentaje de elitismo el 30%, se devolverán 
	 * 	el 30% de individuos. En caso de estar ordenada la población, serán los 
	 * 	30 primeros. En caso de no estar ordenada, individuo por individuo tiramos
	 * 	un "dado" continuo entre 0 y 1. Si pasa la prueba, se añadirá el 
	 * 	individuo.
	 * 
	 * 	El tamaño de la población nueva será round(tamaño*elitismo)
	 * @param ordered	Si la población está ordenada por fitness
	 * @param p1		Población de la que obtener el subconjunto.
	 * @param elitismo	Porcentaje de progenitores que sobreviven.
	 * @return 	El subconjunto de la población de tamaño n*elitismo (aprox)
	 */
	protected static Poblacion getElitistParents(boolean ordered,Poblacion p1,double elitismo){
		Poblacion toret = new Poblacion(p1.size, p1.getNumReglas(), p1.getNumAtributos(), p1.getProbMutacion(), p1.getProbCruce());
 
		int n = (int) Math.round(p1.size * elitismo);
		ArrayList<Individuo> toadd = new ArrayList<>();
		ArrayList<Individuo> individuos = p1.getIndividuos();
		if (ordered){
			for (int i=0;i<n;i++)
				toadd.add(individuos.get(i));
			toret.setIndividuos(toadd);
			toret.setSize(n);
		}else{
			int j = 1;
			for (Individuo i : individuos){
				if (Math.random() <= elitismo && j < n){
					j++;
					toadd.add(i);
				}
			}
			toret.setIndividuos(toadd);
			toret.setSize(n);
		}
		return p1;
	}

}
