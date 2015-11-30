/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import clasificadores.genetica.reemplazos.ReemplazoTotal;
import clasificadores.genetica.seleccion.SeleccionProporcionalFitness;

import org.junit.Test;
import static org.junit.Assert.*;

public class PoblacionTest {
	
	/**
	 * Test of join method, of class Poblacion.
	 */
	@Test
	public void testJoin() {
		System.out.println("join");
		Poblacion p1 = null;
		Poblacion p2 = new Poblacion(0, 1, 2, 0.5, 0.5, new ReemplazoTotal(), false,false, new SeleccionProporcionalFitness());
		Poblacion expected = p2;
		
		Poblacion result = Poblacion.join(p1, p2);
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getIndividuos(),result.getIndividuos());
		assertEquals(expected.getEstrategiaReemplazo().getClass(), result.getEstrategiaReemplazo().getClass());
		
		result = Poblacion.join(p2,p1);
		assertEquals(expected.getSize(), result.getSize());
		assertEquals(expected.getIndividuos(),result.getIndividuos());
		assertEquals(expected.getEstrategiaReemplazo().getClass(), result.getEstrategiaReemplazo().getClass());
	
	}
	


}
