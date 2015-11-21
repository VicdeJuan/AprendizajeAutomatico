/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

/**
 *
 * @author victo
 */
public class ReemplazoTotal extends Reemplazo{

	public ReemplazoTotal(double elitis) {
		this.elitismo = elitis;
	}

	
	@Override
	public Poblacion Reemplazar(Poblacion progenitores, Poblacion vastagos) {
		Poblacion p1 = this.getElitistParents(false, progenitores,elitismo);
		Poblacion p2 = this.getElitistParents(false, vastagos,1-elitismo);
		return Poblacion.join(p1,p2);
	}

	public double getElitismo() {
		return this.elitismo;
	}

	
}

