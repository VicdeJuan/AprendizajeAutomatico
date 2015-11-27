/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetica;

import java.util.ArrayList;
import org.junit.Assert;
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
		assertTrue(instance.regla <= 32-1);
		long val = instance.regla % 2;
		instance.mutar(index);
		// La paridad cambia al mutar 1 bit.
		assertTrue((val +1) %2 == instance.regla%2);
	}

	/**
	 * Test of match method, of class Regla.
	 */
	@Test
	public void testMatch() {
		System.out.println("match");
		Regla rule = new Regla(10,27L);
		Regla instance = new Regla(10,24L);
		
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
		fila.add("b"); //Corresponde a 010 (que será desplazado 3 veces)
		fila.add("x"); //Corresponde a 100 (que será desplazado 2 veces)
		fila.add("o"); //Corresponde a 001 (que será desplazado 1 vez)
		fila.add("negative"); //Corresponde a 0 (que no será desplazado)
		Regla expResult = new Regla(4,(long) (2+Math.pow(2,6)+Math.pow(2, 8)));
		Regla result = Regla.convert(fila);
		assertEquals(expResult.regla, result.regla);
		assertEquals(expResult.size, result.size);
	}
	
	@Test
	public void testCruceNPuntosPar(){
		System.out.println("Cruce en 4 puntos");
		int n = 4;
		Regla[] r1 = new Regla[12];
		Regla[] r2 = new Regla[14];
		for (int i = 0;i<12;i++){
			r1[i] = new Regla(5);
			r2[i] = new Regla(5);
		}
		r2[12] = new Regla(5);
		r2[13] = new Regla(5);
		
		Regla[] expected = new Regla[26];
		int[] idx = Regla.generateIdxAddLast(n, 12,14);
		System.arraycopy(r1, 0, expected, 0, idx[0]);
		System.arraycopy(r2, idx[0], expected, idx[0], idx[1]-idx[0]);
		System.arraycopy(r1, idx[1], expected, idx[1], idx[2]-idx[1]);
		System.arraycopy(r2, idx[2], expected, idx[2], idx[3]-idx[2]);
		System.arraycopy(r1, idx[3], expected, idx[3], 12-idx[3]);
		
		int offset = 12;
		System.arraycopy(r2, 0, expected, offset, idx[0]);
		System.arraycopy(r1, idx[0], expected, offset+idx[0], idx[1]-idx[0]);
		System.arraycopy(r2, idx[1], expected, offset+idx[1], idx[2]-idx[1]);
		System.arraycopy(r1, idx[2], expected, offset+idx[2], idx[3]-idx[2]);
		System.arraycopy(r2, idx[3], expected, offset+idx[3], 14-idx[3]);


		
		Regla[] result = Regla.CruceNPuntos(r1, r2, n);
		
		Assert.assertArrayEquals(expected, result);
		
	}
	
	@Test
	public void testCruceNPuntosImpar(){
		System.out.println("Cruce en 1 punto");
		int n = 1;
		Regla[] r1 = new Regla[12];
		Regla[] r2 = new Regla[14];
		for (int i = 0;i<12;i++){
			r1[i] = new Regla(5);
			r2[i] = new Regla(5);
		}
		Regla[] expected = new Regla[26];
		int[] idx = Regla.generateIdxAddLast(n, 12,14);
		System.arraycopy(r1, 0, expected, 0, idx[0]);
		System.arraycopy(r2, idx[0], expected, idx[0], 14-idx[0]);
		
		
		int offset = 14;
		System.arraycopy(r2, 0, expected, offset, idx[0]);
		
		System.arraycopy(r1, idx[0], expected, offset+idx[0], 12-idx[0]);
		
		
		Regla[] result = Regla.CruceNPuntos(r1, r2, n);
		
		Assert.assertArrayEquals(expected, result);
		
	}
}
