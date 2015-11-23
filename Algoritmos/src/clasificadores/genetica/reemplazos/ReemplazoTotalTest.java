/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica.reemplazos;

import clasificadores.genetica.Poblacion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victo
 */
public class ReemplazoTotalTest {

	
	/**
	 * Test of Reemplazar method, of class ReemplazoTotal.
	 */
	@Test
	public void testReemplazar() {
		System.out.println("Reemplazar");
		Poblacion progenitores = new Poblacion(2, 1, 1, 0.0, 0.0, new ReemplazoTotal(0.0),false,false);
		Poblacion vastagos = new Poblacion(2, 1, 1, 0.0, 0.0, new ReemplazoTotal(0.0),false,false);
	
			
		/** Si el porcentaje de elitismo es 0, TODOS los vastagos 
		 * 	sustituirán a todos los progenitores.
		 */
		ReemplazoTotal instance = new ReemplazoTotal(0.0);
		Poblacion result = instance.Reemplazar(progenitores, vastagos);
		Poblacion expected = vastagos;
		// Sólo comparamos estos 3 campos porque todos los demás se comparan dentro de Poblacion.join
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getIndividuos(),result.getIndividuos());
		assertEquals(expected.getEstrategiaReemplazo().getClass(), result.getEstrategiaReemplazo().getClass());

		/** Si el porcentaje de elitismo es 1, NINGÚN vastago 
		 * 	sustituirán a ningún progenitor.
		 */
		instance = new ReemplazoTotal(1.0);
		result = instance.Reemplazar(progenitores, vastagos);
		expected = progenitores;
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getIndividuos(),result.getIndividuos());
		assertEquals(expected.getEstrategiaReemplazo().getClass(), result.getEstrategiaReemplazo().getClass());

		
	}


		
}
