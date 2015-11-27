/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Poblacion;

/**
 *
 * @author victo
 */
public class ReemplazoTotal extends ReemplazoAbstract{


		
	@Override
	public Poblacion Reemplazar(Poblacion progenitores, Poblacion vastagos) {
		elitismo = progenitores.getElitismo();
		if (progenitores.getElitismo() != vastagos.getElitismo())
			System.out.print("Error en ReemplazoTotal (1)");
		Poblacion p1 = ReemplazoTotal.getElitistParents(progenitores,elitismo);
		Poblacion p2 = ReemplazoTotal.getElitistParents(vastagos,1-elitismo);
		Poblacion toret = Poblacion.join(p1,p2);
		toret.OrdenarPorFitness();
		return toret;
	}

	public double getElitismo() {
		return this.elitismo;
	}

	
}

