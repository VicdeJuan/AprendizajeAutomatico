/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author victo
 */
public class IndividuoTest {
	
	/**
	 * Test of clasifica method, of class Individuo.
	 */
	@Test
	public void testClasifica() {
		System.out.println("clasifica");
		ArrayList<String> row = null;
		Individuo instance = null;
		String expResult = "";
		String result = instance.clasifica(row);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of fitness method, of class Individuo.
	 */
	@Test
	public void testFitness() {
		System.out.println("fitness");
		ArrayList<ArrayList<String>> datos = null;
		Individuo instance = null;
		double expResult = 0.0;
		double result = instance.fitness(datos);
		assertEquals(expResult, result, 0.0);
		// TODO review the generated test code and remove the default call to fail.
		fail("The test case is a prototype.");
	}
	
}
