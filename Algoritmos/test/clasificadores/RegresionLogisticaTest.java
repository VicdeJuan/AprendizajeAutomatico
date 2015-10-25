/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import particionado.EstrategiaParticionado;
import particionado.ValidacionSimple;

/**
 *
 * @author victo
 */
public class RegresionLogisticaTest {
	
	    Datos datos = Datos.cargaDeFichero("data/wdbc.data");
	    RegresionLogistica instance = new RegresionLogistica();
	
	/**
	 * Test of entrenamiento method, of class RegresionLogistica.
	 */
	@Test
	public void testEntrenamiento() {
				HashMap <String,Double>	coeficientes = 	new HashMap<>();
		/**
		 * Valores cogidos a mano de Weka:
		 */
coeficientes.put("Atributo1",	 188.6282);
coeficientes.put("Atributo2",	 10.8339);
coeficientes.put("Atributo3",	-9.1861);
coeficientes.put("Atributo4",	 2.1462);
coeficientes.put("Atributo5",	 1648.9755);
coeficientes.put("Atributo6",	-3054.4526);
coeficientes.put("Atributo7",	 2178.608);
coeficientes.put("Atributo8",	 1414.2789);
coeficientes.put("Atributo9",	-1164.091);
coeficientes.put("Atributo10",	 4982.3167);
coeficientes.put("Atributo11",	 595.7221);
coeficientes.put("Atributo12",	-2.27);
coeficientes.put("Atributo13",	-82.0979);
coeficientes.put("Atributo14",	 2.7601);
coeficientes.put("Atributo15",	-3442.7831);
coeficientes.put("Atributo16",	 4083.8753);
coeficientes.put("Atributo17",	-4388.7917);
coeficientes.put("Atributo18",	 18499.6427);
coeficientes.put("Atributo19",	-5422.451);
coeficientes.put("Atributo20",	-41075.3414);
coeficientes.put("Atributo21",	 15.7906);
coeficientes.put("Atributo22",	 1.9264);
coeficientes.put("Atributo23",	 7.0805);
coeficientes.put("Atributo24",	 0.11);
coeficientes.put("Atributo25",	 290.9135);
coeficientes.put("Atributo26",	-156.3917);
coeficientes.put("Atributo27",	 185.4612);
coeficientes.put("Atributo28",	 610.0745);
coeficientes.put("Atributo29",	 1240.458);
coeficientes.put("Atributo30",	 2677.7547);
coeficientes.put("Class", 	-64.2444);


		System.out.println("entrenamiento");
		RegresionLogistica instance = new RegresionLogistica();
		instance.entrenamiento(datos);
		// TODO review the generated test code and remove the default call to fail.
		for(String a:coeficientes.keySet())
			assertEquals(instance.coef.get(a), coeficientes.get(a));
	}

	/**
	 * Test of clasifica method, of class RegresionLogistica.
	 */
	@Test
	public void testClasifica() {

		HashMap <String,Double>	coeficientes = 	new HashMap<>();
		/**
		 * Valores cogidos a mano de Weka:
		 */
coeficientes.put("Atributo1",	 188.6282);
coeficientes.put("Atributo2",	 10.8339);
coeficientes.put("Atributo3",	-9.1861);
coeficientes.put("Atributo4",	 2.1462);
coeficientes.put("Atributo5",	 1648.9755);
coeficientes.put("Atributo6",	-3054.4526);
coeficientes.put("Atributo7",	 2178.608);
coeficientes.put("Atributo8",	 1414.2789);
coeficientes.put("Atributo9",	-1164.091);
coeficientes.put("Atributo10",	 4982.3167);
coeficientes.put("Atributo11",	 595.7221);
coeficientes.put("Atributo12",	-2.27);
coeficientes.put("Atributo13",	-82.0979);
coeficientes.put("Atributo14",	 2.7601);
coeficientes.put("Atributo15",	-3442.7831);
coeficientes.put("Atributo16",	 4083.8753);
coeficientes.put("Atributo17",	-4388.7917);
coeficientes.put("Atributo18",	 18499.6427);
coeficientes.put("Atributo19",	-5422.451);
coeficientes.put("Atributo20",	-41075.3414);
coeficientes.put("Atributo21",	 15.7906);
coeficientes.put("Atributo22",	 1.9264);
coeficientes.put("Atributo23",	 7.0805);
coeficientes.put("Atributo24",	 0.11);
coeficientes.put("Atributo25",	 290.9135);
coeficientes.put("Atributo26",	-156.3917);
coeficientes.put("Atributo27",	 185.4612);
coeficientes.put("Atributo28",	 610.0745);
coeficientes.put("Atributo29",	 1240.458);
coeficientes.put("Atributo30",	 2677.7547);
coeficientes.put("Class", 	-64.2444);


		System.out.println("clasifica");
		
		instance.setCoef(coeficientes);
		
		
		HashMap<String, Double> result = instance.clasifica(datos);
		double err = instance.error(datos, instance);
		// TODO review the generated test code and remove the default call to fail.
		assertEquals( 0.35, err, 0.001);
	}
	
}
