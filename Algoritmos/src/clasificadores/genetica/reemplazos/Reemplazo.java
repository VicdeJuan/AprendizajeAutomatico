/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Poblacion;

public interface Reemplazo {

	

	/**
	 *
	 * @param progenitores
	 * @param vastagos
	 * @return	Una nueva Poblaci�n acorde con lo implementado.
	 */
	public abstract Poblacion Reemplazar (Poblacion progenitores, Poblacion vastagos);

}
