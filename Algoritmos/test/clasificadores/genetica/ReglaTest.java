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
public class ReglaTest {
	
	public ReglaTest() {
	}
	
	/**
	 * Test of mutar method, of class Regla.
	 */
	@Test
	public void testMutar() {
		System.out.println("mutar");
		int index = 0;
		Regla instance = new Regla(2);
		assertTrue(instance.regla <= 4);
		long val = instance.regla % 2;
		instance.mutar(index);
		// La paridad cambia al mutar 1 bit.
		assertTrue((val +1) %2 == instance.regla%2);
		// TODO review the generated test code and remove the default call to fail.
	}

	/**
	 * Test of match method, of class Regla.
	 */
	@Test
	public void testMatch() {
		System.out.println("match");
		Regla rule = new Regla(10,27L);
		Regla instance = new Regla(10,26L);
		
		String result = instance.match(rule);
		assertNull(result);
		instance = new Regla(10,27L);
		result = instance.match(rule);
		assertEquals(instance.get_class(), result);
	}

	/**
	 * Test of convert method, of class Regla.
	 */
	@Test
	public void testConvert() {
		System.out.println("convert");
		ArrayList<String> fila = new ArrayList<>();
		fila.add("r"); //Corresponde a 10 (que será desplazado 3 veces)
		fila.add("x"); //Corresponde a 00 (que será desplazado 2 veces)
		fila.add("c"); //Corresponde a 01 (que será desplazado 1 vez)
		fila.add("+"); //Corresponde a 0 (que no será desplazado)
		Regla expResult = new Regla(4,2+64);
		Regla result = Regla.convert(fila);
		assertEquals(expResult.regla, result.regla);
		assertEquals(expResult.size, result.size);
	}
	
}
