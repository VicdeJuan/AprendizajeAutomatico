/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparadores;

import clasificadores.genetica.Individuo;
import java.util.ArrayList;
import java.util.Comparator;


public class ComparadorFitness implements Comparator<Individuo>{

	@Override
	public int compare(Individuo o1, Individuo o2) {
		return Double.compare(o1.getFitness() , o2.getFitness());
	}

	
}
