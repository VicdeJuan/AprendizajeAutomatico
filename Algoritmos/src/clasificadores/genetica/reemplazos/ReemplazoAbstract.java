/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Individuo;
import static particionado.EstrategiaParticionado.SEED;
import clasificadores.genetica.Poblacion;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author victo
 */
public abstract class ReemplazoAbstract implements Reemplazo{
	double elitismo = 0;
	
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
	
	protected static Poblacion getElitistParents(Poblacion p1,double elitismo){
		
		Poblacion toret = new Poblacion(p1);
	
		int n = (int) Math.round(p1.getSize() * elitismo);
		ArrayList<Individuo> toadd = new ArrayList<>();
		ArrayList<Individuo> individuos = p1.getIndividuos();
		for (int i=0;i<n;i++)
			toadd.add(new Individuo(individuos.get(i)));
		toret.setIndividuos(toadd);
		toret.setSize(toadd.size());
					
		toret.setIndividuos(toadd);
		toret.setSize(toadd.size());
		
		return toret;
	}


}
