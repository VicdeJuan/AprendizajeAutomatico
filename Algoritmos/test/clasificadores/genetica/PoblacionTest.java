/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import clasificadores.genetica.reemplazos.Reemplazo;
import clasificadores.genetica.reemplazos.ReemplazoTotal;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victo
 */
public class PoblacionTest {
	
	/**
	 * Test of join method, of class Poblacion.
	 */
	@Test
	public void testJoin() {
		System.out.println("join");
		Poblacion p1 = null;
		Poblacion p2 = new Poblacion(0, 1, 2, 0.5, 0.5, new ReemplazoTotal(0.0), false);
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
	
	@Test
	public void testCruceNPuntos(){

		System.out.println("Cruce en 1 punto");
		int n = 1;
		Regla[] r1 = new Regla[12];
		Regla[] r2 = new Regla[14];
		for (int i = 0;i<12;i++){
			r1[i] = new Regla(5);
			r2[i] = new Regla(5);
		}
		Regla[] expected = new Regla[26];
		Regla[] result = Regla.CruceNPuntos(r1, r2, n);
		
		
		/**
		 * TODO: 
		 */	

	}

}
