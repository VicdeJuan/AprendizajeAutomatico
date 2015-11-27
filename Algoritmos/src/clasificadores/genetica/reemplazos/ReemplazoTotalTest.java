/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Poblacion;
import clasificadores.genetica.seleccion.SeleccionProporcionalFitness;

import org.junit.Test;
import static org.junit.Assert.*;


public class ReemplazoTotalTest {

	
	/**
	 * Test of Reemplazar method, of class ReemplazoTotal.
	 */
	@Test
	public void testReemplazar() {
		System.out.println("Reemplazar");
		Poblacion progenitores = new Poblacion(2, 1, 1, 0.0, 0.0, new ReemplazoTotal(),false,false,new SeleccionProporcionalFitness());
		Poblacion vastagos = new Poblacion(2, 1, 1, 0.0, 0.0, new ReemplazoTotal(),false,false,new SeleccionProporcionalFitness());
	
			
		/** Si el porcentaje de elitismo es 0, TODOS los vastagos 
		 * 	sustituirán a todos los progenitores.
		 */
		ReemplazoTotal instance = new ReemplazoTotal();
		progenitores.setElitismo(0);
		vastagos.setElitismo(0);
		Poblacion result = instance.Reemplazar(progenitores, vastagos);
		Poblacion expected = vastagos;
		// Sólo comparamos estos 3 campos porque todos los demás se comparan dentro de Poblacion.join
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getIndividuos(),result.getIndividuos());
		assertEquals(expected.getEstrategiaReemplazo().getClass(), result.getEstrategiaReemplazo().getClass());

		/** Si el porcentaje de elitismo es 1, NINGÚN vastago 
		 * 	sustituirán a ningún progenitor.
		 */
		progenitores.setElitismo(1);
		vastagos.setElitismo(1);
		
		result = instance.Reemplazar(progenitores, vastagos);
		expected = progenitores;
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getIndividuos(),result.getIndividuos());
		assertEquals(expected.getEstrategiaReemplazo().getClass(), result.getEstrategiaReemplazo().getClass());

		
	}


		
}
