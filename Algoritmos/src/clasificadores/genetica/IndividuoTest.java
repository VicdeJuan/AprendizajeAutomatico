/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;


public class IndividuoTest {
	

	ArrayList<String> datos1 = new ArrayList<>();
	ArrayList<String> datos2 = new ArrayList<>();
	ArrayList<String> datos3 = new ArrayList<>();
	ArrayList<String> datos4 = new ArrayList<>();
	ArrayList<String> datos5 = new ArrayList<>();
	ArrayList<ArrayList<String>> bigData = new ArrayList<>();
	
	Regla r1;
	Regla r2;
	Regla r3;
	Regla r4;
	Regla r5;
	
	private void testConstructor(){
		/**
		 * 	
		nomin.put("o", 1L);
		nomin.put("b", 2L);
		nomin.put("x", 0L);

		 */
		datos1.add("o");
		datos1.add("x");
		datos1.add("b");
		datos1.add("positive");
		r1 = Regla.convert(datos1);
		datos2.add("b");
		datos2.add("x");
		datos2.add("o");
		datos2.add("negative");
		
		r2 = Regla.convert(datos2);
		datos3.add("x");
		datos3.add("o");
		datos3.add("b");
		datos3.add("negative");
		
		r3 = Regla.convert(datos3);
		datos4.add("b");
		datos4.add("o");
		datos4.add("b");
		datos5.addAll(datos4);
		datos4.add("negative");
		datos5.add("positive");
		
		r4 = Regla.convert(datos4);
		r5 = Regla.convert(datos5);

		bigData.add(datos1);
		bigData.add(datos2);
		bigData.add(datos3);
		bigData.add(datos4);
		
	}
	
	
	/**
	 * Test of clasifica method, of class Individuo.
	 */
	@Test
	public void testClasifica() {
		testConstructor();
		System.out.println("clasifica");
		ArrayList<String> row = datos4;
		Regla[] rules = new Regla[1];
		
		rules[0] = r4;
		Individuo instance = new Individuo(rules, 4, 1,false);
		instance.ContruirRmasRmenos();
		String result = instance.clasifica(row);
		assertEquals("negative", result);
		
		rules[0] = r5;
		instance = new Individuo(rules, 4, 1,false);
		instance.ContruirRmasRmenos();
		result = instance.clasifica(row);
		assertEquals("positive", result);
		
	}

	/**
	 * Test of fitness method, of class Individuo.
	 */
	@Test
	public void testFitness() {
		testConstructor();
		System.out.println("fitness");
		ArrayList<ArrayList<String>> datos = bigData;
		Regla[] rules = new Regla[4];
		rules[0] = r1;
		rules[1] = r2;
		rules[2] = r3;
		rules[3] = r4;
		Individuo instance = new Individuo(rules, 4, 4,false);
		double result = instance.fitness(datos);
		assertEquals(1, result, 0.0);

	
		rules[3] = r5;
		instance = new Individuo(rules, 4, 4,false);
		result = instance.fitness(datos);
		assertEquals(1, result, 0.0);
	}
	
}
